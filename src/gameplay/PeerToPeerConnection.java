package gameplay;

import client.manager.ClientController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection implements Runnable{
    
    //Data fields for both connector and host
    private GameController controller;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    private Socket socket;    
    protected Thread worker;
    
    private ServerSocket hostSocket;

    public boolean connected = false;    

    /**
     * HOST CLIENT
     * Creates a PeerToPeerConnection to host a game
     * @param controller the GameController for this game
     */
    public PeerToPeerConnection(GameController controller){
        this.controller = controller;
        try{
            hostSocket = new ServerSocket(0);
            controller.setPort(hostSocket.getLocalPort());
        } catch(IOException e){//Add exception
            
        }
    }
    
    /**
     * CONNECTING CLIENT
     * Creates a PeerToPeerConnection to connect to a game
     * @param controller
     * @param ip
     * @param port
     */
    public PeerToPeerConnection(GameController controller, String ip, int port){
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
            connected = true;
        }catch(IOException ex){
            connected = false;
        }
    }
    
    /**
     * Closes the socket
     * Closes the InputStream and OutputStream
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
        }
        catch(IOException ioe){
        }
    }
    public void start() {
        worker = new Thread(this);
        worker.start();
    }
    /**
     * Listens for commands from the socket
     */
    public void run(){
        open();
        while(connected){
            try {
                socket = hostSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(PeerToPeerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
    
}
