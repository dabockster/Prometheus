package server.connection;


/**
 *
 * @author Ermenildo V. Castro, Jr.
 * This class manages the forked connection from client-to-server
 * References: pirate.shu.edu
 */
/*
TODO: send inputStream of Thread to GUI
*/

import java.net.*;
import java.io.*;
import server.manager.ServerController;
import server.manager.UserProfile;


public class UserConnection implements Runnable{
    
    private final ServerController controller;    
    private final Socket socket;
    private final int port;
    protected Thread handler;
    
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;    
    public boolean connected = true;
    
    private UserProfile profile;
    private String username = "Unidentified";
    private boolean playingAnon; //true if User is playing as anonymous
    public boolean loggedOn = false;
    
    
    
    /**
     * CONSTRUCTOR
     * Creates a Thread from the given server and socket
     * @param controller this UserConnection's controller
     * @param clientSocket the client socket
     */
    public UserConnection(ServerController controller, Socket clientSocket){
        this.controller = controller;
        this.socket = clientSocket;
        this.port = socket.getPort();
        controller.addConnection(this);
    }
    
    /**
     * SETUP
     * Opens the socket
     */
    public synchronized void open(){
        try{
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
            this.sendClientFeedback("Successfully Opened Connection");
        }catch(IOException ex){
            this.sendClientFeedback("Failed to Open Connection");
            connected = false;
        }
    }
    
    /**
     * SETUP 
     * Sets a UserProfile to this UserConnection ands changes 
     * the UserProfile state to online.
     * @param profile this client's UserProfile
     */
    public void setUserProfile(UserProfile profile){
        this.profile = profile;
        this.username = profile.getUsername();
    }
    
    /**
     * SETTER
     * Sets playingAnon to the status boolean.
     * @param status is the new value for playingAnon
     */
    public void setAnon(boolean status){
        this.playingAnon = status;
    }
    
    /**
     * TEARDOWN
     * Closes the socket
     */
    private synchronized void close(){
        try{
            if(socket != null){
                socket.close();
            }if(streamIn != null){
                streamIn.close();
            }if(streamOut != null){
                streamOut.close();
            }
            this.sendClientFeedback("Client connection closed.");
        }
        catch(IOException ioe){
            this.sendClientFeedback("Failed to close client connection");
        }
    }    
    
    /**
     * RUN
     * Listens for incoming client commands then 
     * forwards it to the interpretations method.
     */
    @Override
    public void run(){
        synchronized(this){
            this.handler = Thread.currentThread();
        }
        this.open();
        while(connected){
            try{
                String cmnd = streamIn.readUTF();
                String cmndComp[] = cmnd.split("<&>");
                sendClientFeedback("Made Request : "+cmndComp[0]);
                this.interpretRequest(cmndComp);
            }catch(IOException ioe){
                connected = false;
            }
        } 
    }

    /**
     * NETWORK COMMUNICATIONS
     * Writes command to socket for client to read
     * @param cmnd the command to be sent
     */
    public void sendResponse(String cmnd){
        try {
            streamOut.writeUTF(cmnd);
            streamOut.flush();
        } catch(IOException e){
            this.sendClientFeedback("Failed to send command to client.");
        }
    }
    
    /**
     * INTERPRET COMMUNICATIONS
     * Receives a string, assesses the string's purpose, then executes an operation.
     * This method takes an array of Strings and interprets the first index 
     * to determine an operation. The method then passes the unused indexes
     * of the string array to the method of the subsequent operation.
     * @param request the string array to be interpreted 
     */
    public synchronized void interpretRequest(String[] request){
        switch(request[0]){
            case "challengeResponse":
                this.challengeResponse(request);
                break;
            case "challengeRequest" :
                this.challengeRequest(request);
                break;
            case "connect" :
                this.connectRequest();
                break;
            case "login" :
                this.loginRequest(request);
                break;
            case "logout" :
                this.logoutRequest();
                break;
            case "register" :
                registerRequest(request);
                break;
            case "update" :
                updateRequest();
                break;
            default :
                sendClientFeedback("Unrecognized request "+request[0]);
                break;
            }
    }
    
    /**
     * REQUEST - CONNECT
     * Provides a server response to indicate successful connection
     */
    private synchronized void connectRequest(){
        connectResponse(true);
    }
    
    /**
     * REQUEST - CHALLENGE
     * Forwards a challenge from this userConnection to another.
     * @param request username of the player to be challenged
     */
    private synchronized void challengeRequest(String request[]){
        String opponentUsername = request[1];
        controller.relayChallengeRequest(this.username, opponentUsername);
        sendClientFeedback("is Challenging "+ opponentUsername);
    }
    
    /**
     * RESPONSE - RELAY CHALLENGE
     * Receives  challenge from the server to send a challenge message to 
     * this UserConnection.
     * @param challengeUsername the username of the challenger
     */
    public void relayChallengeRequest(String challengeUsername){
        sendResponse("incomingChallenge<&>"+challengeUsername);
        sendClientFeedback("received challenge from "+challengeUsername);
    }
    
    /**
     * REQUEST - CHALLENGE RESPONSE
     * Receives challenge response from a ClientConnection and forwards it
     * to the server.
     * @param request challengerUsername, a response to challenge, and ip/port for Client Connection
     */
    private synchronized void challengeResponse(String request[]){
        String challengerUsername = request[1];
        if(request[2].equals("reject")){
            controller.relayChallengeResponse(false, challengerUsername,null,0);
            sendClientFeedback("challenge denied from "+challengerUsername);
        }else{
            controller.relayChallengeResponse(true, challengerUsername, request[3], Integer.parseInt(request[4]));
            sendClientFeedback("challenge accepted from "+challengerUsername);

        }
    }
    
    /**
     * RESPONSE - RELAY CHALLENGE RESPONSE
     * Receives a challenge response from the server on behalf of another client.
     * @param details if they accepted this will contain the address to connect
     * @param accept true if they accepted challenge
     */
    public void relayChallengeResponse(boolean accepted, String response){
        if(accepted){
            sendResponse("challengeAccepted<&>"+response);
        }else{
            sendResponse("challengeRejected<&>"+response);
        }
    }
    
    /**
     * REQUEST - LOGIN
     * Commences login procedure for this UserConnection
     * @param request the login information used for authentication
     */
    private synchronized void loginRequest(String request[]){
        controller.login(this, request); //create login
    }
        
    /**
     * REQUEST - LOGOUT
     * Commences logout procedure for this UserConnection
     */
    private synchronized void logoutRequest(){
        this.sendClientFeedback("Logoff Successful");
        profile.logout();
        controller.logout(this, playingAnon);
        this.sendResponse("disconnect");
        loggedOn = false;
    }
    
    /**
     * REQUEST - REGISTER
     * Commences procedure to register a new account.
     * @param request
     */
    private synchronized void registerRequest(String[] request){
        controller.register(this, request);
    }      
    
    /**
     * REQUEST - UPDATE
     * Updates all UserConnections connected to the server
     */
    private synchronized void updateRequest(){
        controller.updateAll();
    }
    
    /**
     * RESPONSE - CONNECT
     * Sends a connection status update to the client.
     * @param connected true if this is connected to client
     */
    public void connectResponse(boolean connected){
        String response = "connect<&>";
        if(connected){
            this.sendResponse(response + "true");
        }else
            this.sendResponse(response+"false");
    }
    
    /**
     * RESPONSE
     * Sends a message to server controller to relay to view.
     * @param feedback 
     */
    private synchronized void sendClientFeedback(String feedback){
        controller.sendClientFeedback(username+": "+ feedback);
    }
    
    
    /**
     * GETTER - USERNAME
     * @return this UserConnection's username
     */
    public String getUsername(){
        return this.username;
    }
    /**
     * Getter - profile.toString()
     * @return a string representation of this UserConnection's profile
     */
    @Override
    public String toString(){
        return profile.toString();
    }
}
