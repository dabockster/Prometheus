package gameplay;

import client.manager.ClientController;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection {
    
    //Dear Sean,
    //
    //     I have stubbed out some of the methods I know you will need
    //because I am really on a role with stubbing. You might think its annoying
    //or you might find that they are very helpful. If the prior is true
    //feel free to delete anything except the ones I wrote for myself:)
    //On a seperate note, I do not have my phone today (Jessicah does because
    //she wants to play Hearthstone). I cannot blame her. It is a fun game
    //afterall! Anyways I'm just sitting in the compuer lab with Steven. Just 
    //me and him having the time of our lives! Wish you were here buddy,
    //
    //Good luck writing the rest of this thread! Let me know if you need any
    //help or want to grab a beer.
    //
    //      Sincerely,
    //         Mr. Timothy Ernst
    
    
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
