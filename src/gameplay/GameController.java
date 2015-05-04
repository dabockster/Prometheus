

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
    private String username;
    private GameModel model;
    
    private boolean myTurn;
    private boolean wentFirstLast;
    private boolean rematch = false;
            
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
        model = new GameModel(this);
        connection.start();
        setUsername();
        myTurn(false);
        wentFirstLast = false;
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
        model = new GameModel(this);
        connection.start();
        setUsername();
        myTurn(true);
        wentFirstLast = true;
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
            case "connect" :
                this.receiveConnect();
                break;
            case "connectConfimation" :
                this.receiveConnectConfirmation();
                break;
            case "disconnect":
                this.receiveDisconnect();
                break;
            case "message" :
                this.receiveMsg(request[1]);
                break;
            case "play"  :
                this.receivePlay(request);
                break;            
            case "rematch" :
                this.playAgain(true);
                break;
            case "surrender" :
                this.receiveSurrender();
                break;
        }
    }
    
    /**
     * Sends a connect send to opponent
     */
    public void sendConnect(){
        connection.send("connect");
    }
    
    /**
     * Receives a connect send from opponent, adds view to LobbyView
     * and sends a connectConfirmation send to opponent
     */
    private void receiveConnect(){
        controller.addGameViewToLobby(opName, view);
        view.setVisible(true);
        sendConnectConfirmation();
    }
    
    /**
     * Sends a connectConfirmation to the opponent
     */
    private void sendConnectConfirmation(){
        connection.send("connectConfimation");
    }
    
    /**
     * Receives a connectConfirmation send from opponent and
     * adds view to LobbyView.
     */
    private void receiveConnectConfirmation(){
        controller.addGameViewToLobby(opName, view);
        view.setVisible(true);
    }
    
    /**
     * Closes the connection
     */
    private void receiveDisconnect(){
        //add method for removing a gameController in clientController
        // you opponent has fled the battlefield you are victorious
        connection.close();
    }
    
    /**
     * Sends a message to opponent and updates view
     * @param msg the message to be sent
     */
    public void sendMsg(String msg){
        msg = username+": "+msg;
        connection.send("message<&>"+msg);
        view.postMsg(msg);
    }
    
    /**
     * Receives a message from opponent and updates view
     * @param msg the message received
     */
    public void receiveMsg(String msg){
        view.postMsg(msg);
    }
    
    /**
     * Sends a play move to opponent
     * @param x the row that was played in
     * @param y the column that was played in
     */
    public void sendPlay(int x, int y){
        connection.send("play<&>"+x+"<&>"+y);
        model.playMove(x, y, 1);
        myTurn(false);
    }
    
    /**
     * Receives a play from an opponent
     * @param coordinates 
     */
    public void receivePlay(String[] coordinates){
        int x = Integer.parseInt(coordinates[1]);
        int y = Integer.parseInt(coordinates[2]);
        view.playMove(x,y);
        model.playMove(x, y, -1);
        myTurn(true);
    }
    
    public void sendSurrender(){
        connection.send("surrender");
        view.close();
        controller.sendGameResults("defeat");
    }
    
    public void receiveSurrender(){
        //display that opponent left
        controller.sendGameResults("victory");
        connection.send("disconnect");
        connection.close();
        
    }
    
    /**
     * sets myTurn equal to false
     * @param iJustPlayed 
     */
    private void myTurn(boolean turn){
        if(turn){
            myTurn = true;
            view.turnover(true);
        }else{
            myTurn = false;   
            view.turnover(false);
        }
    }
    
    
    /**
     * Sets this port to the specified value
     */
    public void sendHostAddress(String ip, int port){
        controller.sendHostAddress(opName, ip,port); 
   }
    
    /**
     * Sets the clientType to include this client's username in p2pConnection
     */
    private void setUsername(){
        username = controller.getUsername();
        connection.setID(username);
    }
    
    /**
     * Sends these game results to the server
     * @param results
     */
    public void gameOver(int results){
        if(results == -1){  
            view.loseDisplay();         //loss
            controller.sendGameResults("defeat");
        }else if(results == 0){
            view.tieDisplay();          //tie
            controller.sendGameResults("defeat");
        }else if(results == 1){
            view.winDisplay();          //win
            controller.sendGameResults("victory");
        }
    }   
    
    /**
     * Starts a new game
     * @param gameOn 
     */
    public void playAgain(boolean gameOn){
        if(gameOn){
            if(rematch == true){
                //do new game
                //model.buildNewGrid(30,30);
            }else{
                rematch = true;
            }
        }else{
            //send notice that opponent has left
        }
    }
}
