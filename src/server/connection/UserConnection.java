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
                this.interpretCmnd(cmndComp);
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
    public void sendCmnd(String cmnd){
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
    public synchronized void interpretCmnd(String[] cmndComp){
        switch(cmndComp[0]){
            case "connect" :
                this.connectCmnd();
                break;
            case "error" :
                this.errorCmnd(cmndComp[1]);
                break;
            case "login" :
                this.loginCmnd(cmndComp);
                break;
            case "logout" :
                this.logoutCmnd();
                break;
            case "msg" :
                msgCmnd(cmndComp[1]);
                break;
            case "register" :
                registerCmnd(cmndComp);
                break;
            case "update" :
                System.out.println("UserConnection.update");
                updateCmnd();
                break;
            }
    }
    
    /**
     * EXECUTE COMMAND
     * Provides a server response to indicate successful connection
     */
    private synchronized void connectCmnd(){
        sendClientFeedback("Sending connect response.");
        String response = "connect<&>success";
        this.sendCmnd(response);
    }
    
    /**
     * EXECUTE COMMAND
     * Relays a message to userConnectionFeedback signaling an error.
     * @param errorMsg the error message to be relayed
     */
    private synchronized void errorCmnd(String errorMsg){
        this.sendClientFeedback(errorMsg);
    }    
        
    /**
     * EXECUTE COMMAND
     * Commences login procedure for this UserConnection
     * @param cmndComp the login information used for authentication
     */
    private synchronized void loginCmnd(String cmndComp[]){
        controller.login(this, cmndComp); //create login
        
    }
        
    /**
     * EXECUTE COMMAND
     * Commences logout procedure for this UserConnection
     */
    private synchronized void logoutCmnd(){
        this.sendClientFeedback("has logged off.");
        profile.logout();
        controller.logout(this); //create logout
        this.close();
    }
    
    /**
     * EXECUTE COMMAND
     * Receives a message from ClientConnection and
     * relays it to userConnectionFeedback.
     * @param msg the message to be relayed
     */
    private synchronized void msgCmnd(String msg){
        this.sendClientFeedback(msg);
    }
    
    /**
     * EXECUTE COMMAND
     * Commences procedure to register a new account.
     * @param cmndComp
     */
    private synchronized void registerCmnd(String[] cmndComp){
        controller.register(this, cmndComp);
    }
    
    /**
     * EXECUTE COMMAND
     * @param cmndComp 
     */
    private synchronized void updateCmnd(){
        controller.updateAll();
    }
    
    /**
     * RELAY MESSAGE
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
