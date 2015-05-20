package client.connection;

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
import java.net.*;
import java.io.*;



public class ClientConnection implements Runnable{
    private final ClientConnectionController controller;
    private final InetSocketAddress serverAddr;
    
    private DataOutputStream streamOut = null;
    private DataInputStream streamIn;
    private final String[] connectionFailure = {"connect","false"};
    
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
     * Opens the  socket.
     * Gets InputStream and OutputStream from socket
     */
    private void  open(){
        String serverName = serverAddr.getHostName();
        int serverPort = serverAddr.getPort();
        try{
            socket = new Socket(serverName, serverPort);
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
            connected = true;
        } catch (IOException ioe){
            controller.interpretResponse(connectionFailure);
        }
    }
    
    public void close(){
        try{
        streamIn.close();
        streamOut.close();
        socket.close();
        connected = false;
        } catch (IOException ex){
            System.out.println("error "+ex);
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
            open();
        }
        serverRequest("connect");
        while(connected){
            try{
                String request = streamIn.readUTF();
                String response[] = request.split("<&>");
                System.out.println("Received Response: "+response[0]);
                if(response[0].equals("disconnect")){
                        connected = false;
                        this.close();
                }
                controller.interpretResponse(response);
            }catch(IOException ioe){
                controller.interpretResponse(connectionFailure);
                System.out.println("Connection Closed "+ioe);
                connected = false; 
            }
        } 
    }

    /**
     * NETWORK COMMUNICATION
     * serverRequest()
     * Write to socket and sends the command to server
     * @param request the command for the server
     */
    public void serverRequest(String request){
        if(!connected) {
            controller.interpretResponse(connectionFailure);
            return;
        }
        try {
            streamOut.writeUTF(request);
            streamOut.flush();
        } catch (IOException ioe) {
            connected = false;
            controller.interpretResponse(connectionFailure);
        }
    }
    
} // enclosing CLASS
