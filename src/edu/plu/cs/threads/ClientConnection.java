package edu.plu.cs.threads;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ermenildo V. Castro, Jr.
 * References:
 * pirate.shu.edu - client/server java example
 */
/*
*TODO: Set received input to GUI OUT; Ref. HANDLE()
    Implement conditional to 
*/
import edu.plu.cs.controllers.ClientConnectionController;
import java.net.*;
import java.io.*;



public class ClientConnection implements Runnable{
    private final ClientConnectionController controller;
    private InetSocketAddress serverAddr;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    
    private Socket socket;
    protected Thread listeningToServer;
    
    public boolean connected = false;

    
    
    /**
     * CONSTRUCTOR 
     * @param controller
     * @param serverAddr
     */
    public ClientConnection(ClientConnectionController controller, InetSocketAddress address){
        this.serverAddr = address;
        this.controller = controller;        
    }
    
    /**
     * SETUP
     * Opens the client socket
     */
    private void open(){
        String serverName = serverAddr.getHostName();
        int serverPort = serverAddr.getPort();
        try{
            socket = new Socket(serverName, serverPort);
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
            connected = true;
            System.out.println("Connection medium created.");
        } catch (IOException ioe){
            System.out.println("Error building ClientConnection "+ ioe);
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
            this.listeningToServer = Thread.currentThread();
        }
        open();
        while(connected){
            System.out.println("Connected on clientConnection ");
            try{
                System.out.println("Client is listening...");
                String cmnd = streamIn.readUTF();
                cmnd = "server<&>"+cmnd;
                String cmndComp[] = cmnd.split("<&>");
                controller.interpretCmnd(cmndComp);
                System.out.println(cmndComp[0]);
            }catch(IOException ioe){
                System.out.println("Failed to receive command from server.");
                connected = false; 
            }
        } 
    }

    /**
     * NETWORK COMMUNICATIONS
     * sendCmnd()
     * Write to socket and sends the command to server
     * @param cmnd the command for the server
     */
    public void sendCmnd(String cmnd){
        try {
            streamOut.writeUTF(cmnd);;
            streamOut.flush();
            connected = true;
        } catch (IOException ioe) {
            System.out.println(ioe);
            controller.sendServerFeedback("Failed to send command.");
        }
    }
    
} // enclosing CLASS
