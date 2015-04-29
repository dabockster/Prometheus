
package server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.manager.ServerController;

/**
 *
 * @author Vivo
 * Ref. CSCE 386: ClientServerCommunications, Ermenildo V. Castro, Jr.
 */
//TODO: Abstract server processes to the ServerController and ServerModel
public class ServerConnection implements Runnable{
    private final ServerController controller;
    private final int port;
    
    private ServerSocket socket;    
    protected Thread acceptingNewClients; 
    public boolean isStopped = false;

    
    /**
     * CONSTRUCTOR
     * This creates a new ServerConnection object to accept incoming 
     * ClientConnections.
     * @param controller the ServerController for this ServerConnection
     * @param port the port through which the server is running
     */
    public ServerConnection(ServerController controller, int port){
        this.controller = controller;
        this.port = port;
    }    
    
    /**
     * Opens the socket
     * Initializes the ServerConnection socket on port serverPort.
     */
    private void open(){
        if(this.socket != null){
            sendServerFeedback("Failed to Open - Socket Already In Use");
            return;
        }
        try{
            this.socket = new ServerSocket(this.port);
            sendServerFeedback("Socket Open");
        } catch (IOException e) {
            this.sendServerFeedback("Faiiled to Open - Port "+port+" Unaccessisble");
        } 
    }
    
    /**
     * Closes socket
     * Stops the ServerConnection it is no longer accepting new clients.
     */
    public synchronized void close(){
        this.isStopped = true;
        try{
            this.socket.close();
        } catch ( IOException e ) {
            this.sendServerFeedback("Failed to Close");
            throw new RuntimeException("Error closing server.", e);
        }
    }
    
    /**
     * NETWORK COMMUNICATIONS
     * Runs the ServerConnection and accepts then handles incoming clients.
     */
    @Override
    public void run() {
        synchronized(this){
            this.acceptingNewClients = Thread.currentThread();
        }
        open();
        while(! isStopped()){
            Socket client = null;
                try{
                    client = this.socket.accept();
                    this.sendServerFeedback("Accepted New Client");
                } catch (IOException e){
                    if(isStopped()){
                        this.sendServerFeedback("Stopped");
                        return;
                    }
                    throw new RuntimeException("Error accepting ClientConnection.",e);
                }
                new Thread( new UserConnection(controller, client)).start();
        }
    }
    
    /**
     * CHECKER
     * Returns true if the ServerConnection is not accepting clients
     * and returns false if the server is accepting clients.
     * @return true if server is isStopped
     */
    private synchronized boolean isStopped(){
        return this.isStopped;
    }
    
    /**
     * VIEW - FEEDBACK
     * Sends a message to the controller to relay to the view.
     * @param feedback 
     */
    public void sendServerFeedback(String feedback){
        controller.sendServerFeedback("Connection: " + feedback);
    }    
}
