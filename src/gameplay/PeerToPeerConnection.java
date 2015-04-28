package gameplay;

import client.manager.ClientController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection {
    
    private ClientController controller;
    
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    
    private Socket socket;
    private ServerSocket hostSocket;
    
    protected Thread listeningToServer;
    
    public boolean connected = false;    

    /**
     * HOST CLIENT
     * Creates a PeerToPeerConnection to host a game
     * @param controller the GameController for this game
     */
    public PeerToPeerConnection(ClientController controller){
        this.controller = controller;
    }
    
    /**
     * CONNECTING CLIENT
     * Creates a PeerToPeerConnection to connect to a game
     * @param controller
     * @param ip
     * @param port
     */
    public PeerToPeerConnection(ClientController controller, String ip, int port){
        this.controller = controller;
        
    }
    
    /**
     * Opens the socket.
     * Gets InputStream and OutputStream from socket
     */
    public synchronized void open(){
        try{
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
        }catch(IOException ex){
            connected = false;
        }
    }
    
    /**
     * Closes the socket
     * Closes the InputStream and OutputStream
     */
    private synchronized void close(){

    }
    
    /**
     * Listens for commands from the socket
     */
    public void run(){

    }
    
    
    
    
}
