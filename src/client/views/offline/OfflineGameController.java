

package client.views.offline;

import gameplay.*;
import client.manager.ClientController;
import static java.lang.Math.abs;
import AISimulationEnvironment.*;
import java.util.concurrent.TimeUnit;
import BotSunTzu.SunTzu;
/**
 * 
 * @author PLUCSCE
 */
public class OfflineGameController {
    
    //data fields
    private ClientController controller;
    private OfflineGameView view;
    private PeerToPeerConnection connection;
    private String opName; //opponent's name
    private String username;
    private OfflineGameModel model;
    
    private boolean myTurn;
    private boolean wentFirstLast;
    private boolean rematch = false;
    private boolean playing;
    
    //START AI DECLARATIONS-----------------------------------------------------
    private boolean botPlaying = false;
    
    //0 = Player 1; 1 = Player 2
    //denotes if Bot is Player1 or Player2
    private int botPlayerDesignation; 
    private boolean botPlayerDesignationBOOL;
    
    private boolean botMoved;
    private int[] botMove; 
    
    //BOT time delay
    final private int BOTDELAY = 145;
    
    //BOT SEED
    public BotInterface bot; 
    //END AI DECLARATIONS-------------------------------------------------------      
    /**
     * HOST CONSTRUCTOR
     * !!!!!!!!!!!!!DEPRECATED!!!!!!!!!!!!!!!!!!
     * Establishes this client as the Host of the match and creates a 
     * PeerToPeerConnection where it will accept the connecting client.
     * @param controller the client controller to which this will communicate
     */
    public OfflineGameController(String opName, ClientController controller){
        this.opName = opName;
        this.controller = controller;
        model = new OfflineGameModel(this);
        connection.start();
        setUsername();
        playing = true;
        myTurn(false);
        wentFirstLast = false;
    }
    /**
     * OFFLINE CONSTRUCTOR
     */
    public OfflineGameController(int player1Type, int player2Type, int bSizeX, int bSizeY, ClientController control){
        this.controller = control;
        this.opName = "Generic Player";
        
        view = new OfflineGameView(this, bSizeX, bSizeY);
        model = new OfflineGameModel(this, bSizeX, bSizeY);
        
        //check if player1 is AI
        if(player1Type != 0){this.setBot(2, player1Type);}
        //check if player2 is AI
        else if(player2Type != 0){this.setBot(3, player2Type);}
        
        playing = true;
        view.setVisible(true);
    }
    
    /**
     * setBot()
     * Initializes
     * @param player - determines if bot is Player1 or Player2: 0 = Player 1; 1 = Player 2
     * @param difficulty - determines the difficulty level of the bot [1-3]
     */
    public void setBot(int player, int difficulty){
        botPlaying = true;
        botMove = new int[2];
        botMoved = false;
        botPlayerDesignation = abs(player % 2);
        
        
        
        if(botPlayerDesignation == 1){
            botMoved = true; // Player2 is bot, botMoved true == player1 moves first
            botPlayerDesignationBOOL = true;
            myTurn(true);} //PLAYER1 is HUMAN
        else{
            botPlayerDesignationBOOL = false;
            this.verifyMove(); //Player 1 is bot, retrieve botMove
            myTurn(true);}
        //TODO: difficulty modifier for bot
        bot = new SunTzu(botPlayerDesignationBOOL, model.getBoard(), 2);
        //bot = new RandBot();
    }
    
    /**
     * CONNECTING CONSTRUCTOR
     * Establishes that this client is NOT the host. It will create a
     * PeerToPeerConnection where it will connect to the host.
     * @param controller the client controller to which this will communicate
     * @param ip the IP address of the host
     * @param port the port number of the host
     */
    public OfflineGameController(String opName, ClientController controller, String ip, int port){
        this.opName = opName;
        this.controller = controller;
        //this.view = new OfflineGameView(this);
        model = new OfflineGameModel(this);
        connection.start();
        playing = true;
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
            case "leave" :
                this.opponentLeft();
                break;
            case "play" :
                this.receivePlay(request);
                break;            
            case "playAgain" :
                this.opponentChoice();
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
     * Sends a connectConfirmation to the opponent
     */
    private void sendConnectConfirmation(){
        connection.send("connectConfimation");
    }
    
    
    private void sendDisconnect(){
        connection.send("disconnect");
        connection.close();
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
     * Sends a play move to opponent
     * @param x the row that was played in
     * @param y the column that was played in
     */
    public void sendPlay(int x, int y, boolean player){
        if(player){model.playMove(x, y, 1);}
        else{model.playMove(x, y, -1);}
        
        //connection.send("play<&>"+x+"<&>"+y);
        //myTurn(false);
    }
    /**
     * Determine precedence of move acceptance based on Player1 or Player2 status.
     * Player1 moves first; Player2 moves second.
     */
    public void verifyMove(){
        //IF AI PLAYING, DETERMINE PRECEDENCE
        if(botPlaying){
            //IF AI FIRST (!aiMoved)
            if(!botMoved){
                //AI fetchMove()
                botMove = bot.fetchMove(model.getBoard());
                view.playMoveAI(botMove, botPlayerDesignation);
                botMoved = true;
                //TURNOVER TO HUMAN
                try{TimeUnit.MILLISECONDS.sleep(BOTDELAY);}catch(InterruptedException e){System.out.println("Sleep Interrupted");}
                myTurn(true);
            }
            else{
            //ELSE REGISTER HUMAN MOVE
            view.playerDesignatedMove(!botPlayerDesignationBOOL);
                 //TURNOVER TO AI
            botMoved = false;
            myTurn(false);
            this.verifyMove();
            }
        }else{
        //ELSE USE wentFirstLast rotation to determine player designation
            
            //PLAYER2 wentFirstLast = true; PLAYER1 must wait
            if(wentFirstLast){view.playerDesignatedMove(false);} //player2 players move
            
            //PLAYER1 wentFirstLast = false; PLAYER2 must wait
            else{view.playerDesignatedMove(true);}//player1 plays move
            //view.playerDesignatedMove(wentFirstLast);
            wentFirstLast = !wentFirstLast;
        }
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
    
    /**
     * Forwards a surrender to opponent client.
     * Notifies the server of a defeat.
     * Displays the loseDisplay.
     */
    public void sendSurrender(){
        connection.send("surrender");
        gameOver(-1);
        view.playAgainDisplay();
    }
    
    /**
     * Receives a surrender from the opposing client and displays a winDisplay
     */
    public void receiveSurrender(){
        gameOver(1);
//        view.receiveSurrender();
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
        playing = false;
        if(results == -1){  
            view.loseDisplay();         //loss
            controller.sendGameResults("defeat");
            view.playAgainDisplay();
        }else if(results == 0){
            view.tieDisplay();          //tie
            controller.sendGameResults("defeat");
            view.playAgainDisplay();
        }else if(results == 1){
            view.winDisplay();          //win
            controller.sendGameResults("victory");
            view.playAgainDisplay();

        }
    }
    
    /**
     * If this player has to leave in the middle of a game
     * This method is called from the ClientController
     */
    public void leave(){
        if(connection.connected)
            connection.send("leave");
        view.close();
        if(playing)
            gameOver(-1);
    }
    
    private void opponentLeft(){
        if(playing){
            gameOver(1);
        }
//        view.opponentLeftDisplay();
        sendDisconnect();
    }
    
    /**
     * Receives the opponents decision on playing another game
     */
    public void opponentChoice(){
        playAgain();
    }
    
    public void myChoice(boolean choice){
        /*
        if(choice){
            playAgain();
            connection.send("playAgain<&>true");
        }else{
            controller.leaveGame(this);
        }
        */
    }
    
    /**
     * Starts a new game if both players want to play again
     */
    public void playAgain(){            
        if(rematch == true)
            newGame();
        else
            rematch = true;
    }
    
    /**
     * Builds a new GameModel and scrubs the GameView to have a new game
     */
    private void newGame(){
        playing = true;
        rematch = false;
//        view.newGame();
//        view.toggleGameButtons(true);
        model.buildNewGrid(30,30);
        if(wentFirstLast){
            wentFirstLast = false;
            myTurn(false);
        }else{
            wentFirstLast = true;
            myTurn(true);
        }                   
    }
}
