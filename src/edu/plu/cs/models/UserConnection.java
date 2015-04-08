package edu.plu.cs.models;


/**
 *
 * @author Ermenildo V. Castro, Jr.
 * This class manages the forked connection from client-to-server
 * References: pirate.shu.edu
 */
/*
TODO: send inputStream of Thread to GUI
*/
import edu.plu.cs.controllers.ServerController;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConnection extends Thread{
    private Socket socket = null;
    private ServerConnection server = null;
    private ServerController scntrl;
    private int ID = -1;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;
    private String associatedAcctName = "undef";
    private boolean boolThreadCont = true;
    /**
     * ServerThread constructor
     * Creates a Thread from the given server and socket
     * @param _server - the received server, reference
     * @param _socket - the socket
     */
    public UserConnection(ServerConnection server, Socket socket){this.server = server; this.socket = socket; ID = socket.getPort(); this.scntrl = server.getController(); scntrl.addConnection(this);}
    /**
     * run()
     * From Thread extension, executes THIS thread
     */
    @Override
    public void run(){
        String content = "";
        //listen for input
        //reads from socket - receives client input
        boolThreadCont = true;
        while(boolThreadCont){
            //print input
            //TODO - send input to GUI
            try{
                content = streamIn.readUTF();
                scntrl.recvCmd(content + "<&>" + ID); //TODO should call ServerController
                //System.out.println("FROM CLIENT: " + streamIn.readUTF()); //this code prints any data received to console
                
            }catch(IOException ioe){System.out.println("UserConnection.java: Socket closed! " + ioe); boolThreadCont = false; }
        }//close while
    }
    /**
     * setAssociatedAcctName
     * @param username 
     */
    public void setAssociatedAcctName(String username){associatedAcctName = username;}
    /**
     * getAssociatedAcctName
     * @param username 
     */
    public String getAssociatedAcctName(){return associatedAcctName;}
    /**
     * sendMessage()
     * Writes to the socket for the client to read
     * @param msg - the message to write to client
     */
    public void sendMessage(String msg){
        //System.out.println("SThread, sendMessage is fucked." );
        try{streamOut.writeUTF(msg); streamOut.flush();}catch(IOException ioe){System.out.println(ioe);}
    }
    /**
     * getID()
     * @return the integer port for the current socket
     */
    public int getID(){return ID;}
    /**
     * open()
     * Opens the socket
     */
    public void open() throws IOException{streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream())); streamOut = new DataOutputStream(socket.getOutputStream());}
    /**
     * close()
     * Closes the socket
     */
    public void close(){
        try{if(socket!= null){socket.close();}if(streamIn != null){streamIn.close();}if(streamOut != null){streamOut.close();}boolThreadCont = false;}
        catch(IOException ioe){System.out.println("UserConnection, close() error" +ioe);
        }}
}
