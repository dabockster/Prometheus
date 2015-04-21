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
import server.storage.UserProfile;


public class UserConnection implements Runnable{
    
    private final ServerController controller;    
    private final Socket socket;
    private final int port;
    protected Thread handler;
    
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;    
    public boolean connected = true;
    
    private UserProfile profile;
    private String username = "Anonymous";
    
    
    
    /**
     * CONSTRUCTOR
     * Creates a Thread from the given server and socket
     * @param controller this UserConnection's controller
     * @param socket the socket
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
            this.sendClientFeedback("Unidentified user has connected.");
        }catch(IOException ex){
            this.sendClientFeedback("Failed to retreive socket InputStream and OutputStream.");
            connected = false;
        }
    }
    
    /**
     * SETUP 
     * Sets a UserProfile to this UserConnection ands changes 
     * the UserProfile state to online.
     * @param UserProfile this client's UserProfile
     */
    public void setUserProfile(UserProfile profile){
        this.profile = profile;
        this.username = profile.getUsername();
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
     * NETWORK COMMUNICATIONS
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
                sendClientFeedback("command received "+cmndComp[0]);
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
     * @param cmndComp the string array to be interpreted 
     */
    public synchronized void interpretRequest(String[] cmndComp){
        switch(cmndComp[0]){
            case "connect" :
                this.connectRequest();
                break;
            case "error" :
                this.errorResponse(cmndComp[1]);
                break;
            case "login" :
                this.loginRequest(cmndComp);
                break;
            case "logout" :
                this.logoutRequest();
                break;
            case "msg" :
                msgResponse(cmndComp[1]);
                break;
            case "register" :
                registerRequest(cmndComp);
                break;
            case "update" :
                System.out.println("UserConnection.update");
                updateResponse();
                break;
            }
    }
    
    /**
     * REQUEST
     * Provides a server response to indicate successful connection
     */
    private synchronized void connectRequest(){
        sendClientFeedback("Sending connect response.");
        String response = "connect<&>success";
        this.sendResponse(response);
    }
    
    /**
     * RESPONSE
     * Relays a message to userConnectionFeedback signaling an error.
     * @param errorMsg the error message to be relayed
     */
    private synchronized void errorResponse(String errorMsg){
        this.sendClientFeedback(errorMsg);
    }    
        
    /**
     * REQUEST
     * Commences login procedure for this UserConnection
     * @param cmndComp the login information used for authentication
     */
    private synchronized void loginRequest(String cmndComp[]){
        controller.login(this, cmndComp); //create login
        
    }
        
    /**
     * REQUEST
     * Commences logout procedure for this UserConnection
     */
    private synchronized void logoutRequest(){
        this.sendClientFeedback("has logged off.");
        profile.logout();
        controller.logout(this); //create logout
        this.close();
    }
    
    /**
     * RESPONSE
     * Receives a message from ClientConnection and
     * relays it to userConnectionFeedback.
     * @param msg the message to be relayed
     */
    private synchronized void msgResponse(String msg){
        this.sendClientFeedback(msg);
    }
    
    /**
     * REQUEST
     * Commences procedure to register a new account.
     * @param cmndComp
     */
    private synchronized void registerRequest(String[] cmndComp){
        controller.register(this, cmndComp);
    }
    
    /**
     * RESPONSE
     * @param cmndComp 
     */
    private synchronized void updateResponse(){
        controller.updateAll();
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
     */
    public String getUsername(){
        return this.username;
    }
    
    /**
     * Getter - profile.toString()
     */
    public String toString(){
        
        return profile.toString();
    }
}
