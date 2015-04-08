package edu.plu.cs.models;

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
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client implements Runnable{
    private Socket socket = null;
    private BufferedReader console = null;
    private DataOutputStream streamOut = null;
    private Thread thread = null;
    private ClientThread client = null;
    private ClientConnectionController cntrl;
    /**
     * client constructor
     * @param serverName - string, IP designation of server
     * @param serverPort - int, port designation of server
     * 
     */
    public Client(InetSocketAddress conaddr){
        String serverName = conaddr.getHostName();
        int serverPort = conaddr.getPort();
        System.out.println("Connecting to: " + serverName + " : " + serverPort);
        try{
            // creates TCP socket
            // use DatagramSocket for UDP
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            this.start();
           
        }catch(IOException ioe){System.out.println(ioe); this.stop();}
    } // closing constructor
    /**
     * conStatus
     * @return TRUE if socket isClosed
     */
    public boolean conStatus(){return socket.isClosed();}
    @Override
    /**
     * run()
     * Invoked by THREAD
     * Writes USER input to socket
     */
    public void run() {
        while(thread != null){
            //TODO: change to byteRead, !ReadLine
            //ByteArray in buffer
            //--DEPRECATED--
            //try{streamOut.writeUTF(console.readLine()); streamOut.flush();}catch(IOException ioe){System.out.println(ioe); this.stop();}
        }
    }
    /**
     * send()
     * Write to socket, send msg to server
     * @param msg - string, message
     */
    public void send(String msg){try {
        streamOut.writeUTF(msg);
        } catch (IOException ioe) {
            System.out.println("Client: Unable to Write >>CON-CLOSED<<");
            this.stop();
        }
}
    /**
     * start()
     * Create thread of THIS client
     * Invoke ClientThread
     * Set I/O streams for client socket 
     */
    public void start() throws IOException{
        console = new BufferedReader(new InputStreamReader(System.in));
    
        streamOut = new DataOutputStream(socket.getOutputStream());
        
        //if thread has yet to be created
        //create only single thread instance of THIS
        if(thread == null){client = new ClientThread(this, socket); thread = new Thread(this); thread.start();}
    }
    /**
     * stop()
     * End Thread execution
     */
    public void stop(){
        try{if(console != null){console.close();}if(streamOut != null){streamOut.close();}if(socket != null){socket.close();}if(thread != null){thread = null;}}catch(IOException ioe){System.out.println("Exception at Client.java stop(): "+ ioe);}
        //client.close();
        //cntrl.recvCmd("CONCLOSED");
        if(thread != null){thread = null;}
    }
    /**
     * handle()
     * Retrieve input from socket; forward msg to ClientConnectionController
     * @param msg - the message to display
     * TODO: Handle connection close request
     */
    public void handle(String msg){
        try{
        cntrl.recvCmd(msg);
        } catch(NullPointerException n){System.out.println("Client.java: handle(null)>>CON-CLOSE<<"); this.stop();}
    }
    /**
     * setController()
     * Allow client to notify controller of changes
     * @param cntrl - controller to reference
     */
    public void setController(ClientConnectionController cntrl){this.cntrl = cntrl;}
    /**
     * removeController()
     * Sets controller to null
     */
    public void removeController(){cntrl = null;}
} // enclosing CLASS
