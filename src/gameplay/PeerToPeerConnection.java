package gameplay;

import client.manager.ClientController;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection {
    
    /*
        Dear Tim and Sean,
    
            AYY LMAO
    
            Sincerely,
             Steven
    */  
    
    /**
     * HOST CLIENT
     * Creates a PeerToPeerConnection to host a game
     * @param controller the GameController for this game
     */
    public PeerToPeerConnection(ClientController controller){
        
    }
    
    /**
     * CONNECTING CLIENT
     * Creates a PeerToPeerConnection to connect to a game
     * @param controller
     * @param ip
     * @param port
     */
    public PeerToPeerConnection(ClientController controller, String ip, int port){
        
    }
    
    /**
     * Opens the socket.
     * Gets InputStream and OutputStream from socket
     */
    public synchronized void open(){
        //do it.
    }
    
    /**
     * Closes the socket
     * Closes the InputStream and OutputStream
     */
    private synchronized void close(){
        //do it, man. do it!
    }
    
    /**
     * Listens for commands from the socket
     */
    public void run(){
        //Cmon man! just do it already!
    }
    
    
    
    
}
