

package gameplay;

import client.manager.ClientController;

/**
 * 
 * @author PLUCSCE
 */
public class GameController {
    
    //data fields
    private ClientController controller;
    private GameView view;
    private PeerToPeerConnection connection;
    private String opName; //opponent's name
    
    /**
     * HOST CONSTRUCTOR
     * Establishes this client as the Host of the match and creates a 
     * PeerToPeerConnection where it will accept the connecting client.
     * @param controller the client controller to which this will communicate
     */
    public GameController(String opName, ClientController controller){
        this.opName = opName;
        this.controller = controller;
        this.connection = new PeerToPeerConnection(this);
        this.view = new GameView(this);
    }
    
    /**
     * CONNECTING CONSTRUCTOR
     * Establishes that this client is NOT the host. It will create a
     * PeerToPeerConnection where it will connect to the host.
     * @param controller the client controller to which this will communicate
     * @param ip the IP address of the host
     * @param port the port number of the host
     */
    public GameController(String opName, ClientController controller, String ip, int port){
        this.opName = opName;
        this.controller = controller;
        this.connection = new PeerToPeerConnection(this, ip, port);
        this.view = new GameView(this);
    }
    
    /**
     * INTERPRET COMMUNICATIONS
     * Receives a string, assesses the string's purpose, then executes an operation.
     * This method takes an array of Strings and interprets the first index 
     * to determine an operation. The method then passes the unused indexes
     * of the string array to the method of the subsequent operation.
     * @param request the string array to be interpreted 
     */
    public synchronized void interpretRequest(String[] request){
        switch(request[0]){
            case "message" :
                this.receiveMsg(request[1]);
                break;
                
        }
    }
    
    /**
     * Sends a message to opponent and updates view
     * @param msg the message to be sent
     */
    public void sendMsg(String msg){
        connection.request(msg);
        //updateView
    }
    
    /**
     * Receives a message from opponent and updates view
     * @param msg the message received
     */
    public void receiveMsg(String msg){
        //updates view
    }
    
    /**
     * Sets this port to the specified value
     */
    public void sendHostAddress(String ip, int port){
        controller.sendHostAddress(opName, ip,port); 
   }
}
