/*
 * The MIT License
 *
 * Copyright 2015 Vivo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
     * SETUP
     * Initializes the ServerConnection socket on port serverPort.
     */
    private void openServerConnection(){
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
     * TEARDOWN
     * Stops the ServerConnection it is no longer accepting new clients.
     */
    public synchronized void stop(){
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
        openServerConnection();
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
