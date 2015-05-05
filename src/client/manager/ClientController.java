
package client.manager;

import client.connection.ClientConnectionController;
import client.views.lobby.LobbyController;
import client.views.mainMenu.MainMenuController;
import client.views.offline.OfflineController;
import gameplay.GameController;
import gameplay.GameView;
import java.util.Arrays;

/**
 * ClientController class
 * @author dabockster
 */
public final class ClientController {
    
    String username;
    
    private MainMenuController mainMenu = null;
    private OfflineController offline;
    private ClientModel model;
    private LobbyController lobby;
    private boolean connectedToServer = false;
    private ClientConnectionController cController;
    
    int attemptsToConnect = 0;
    
    /**
     * ClientController constructor
     */
    public ClientController(){
        model = new ClientModel(this);
        mainMenu = new MainMenuController(this);
        newConnection(null,0);
    }
  
    /**
     * Displays a message on the view
     * @param feedback 
     */
    public void displayLoginMessage(String feedback){
        mainMenu.sendFeedback(feedback);
    }
     
    /**
     * Exits the entire program
     * @param exitStatus the status of the exit (0 for clean exit, 1 for errors, etc)
     */
    public void quitProgram(int exitStatus){
        System.exit(exitStatus);
    }
    
    /**
     * Wipes this ClientController and creates a new clientController
     */
    public void refreshClient(){      
        model = new ClientModel(this);
        mainMenu = new MainMenuController(this);
        newConnection(null,0);
        username = null;
    }

    /**
     * NEW CONNECTION TO SERVER
     * Connects to a specified IP address and port
     * @param ip
     * @param port 
     */
    public void newConnection(String ip, int port){
        if(ip == null){
            ip = "localhost";
            port = 8080;
        }
        if(cController == null){
            cController = new ClientConnectionController(this,ip,port);
        }else{
            if(cController.connectedToServer())
                cController.close();
            cController.newConnection(ip,port);
        }
        System.out.print("Connecting to server");
    }
    
    /**
     * Creates an offline game 
     */
    public void offlineGame(){
        offline = new OfflineController(this);
    }

    /**
     * CONNECT REQUEST
     * Sends a request to server to ensure a connection
     * has been established
     */
    public synchronized void connectRequest(){
        cController.serverRequest("connect");
    }    
    
    /**
     * CONNECT RESPONSE
     * Displays a message from the server
     */
    public void connectResponse(boolean connected){
        if(connected){
            System.out.println("Successfully Connected!");
            displayLoginMessage("Connected to Server");
            connectedToServer = true;
        }else{
            attemptsToConnect++;
            if(attemptsToConnect == 5){
                System.out.println("Cannot Connect");
            }else if(attemptsToConnect < 5){
                System.out.print("."); 
                try {
                    Thread.sleep(1000 * attemptsToConnect);
                    cController.serverRequest("connect");
                } catch (InterruptedException ex) {
                   System.out.println("ClientController: connectResponse: InterruptedException: "+ex);
                }
            }
        displayLoginMessage("NOT Connected to Server");
        }   
    }    
    
    /**
     * UPDATE REQUEST
     * Sends a request to the server to receive a list of all online UserProfiles
     */
    public void updateRequest(){
        cController.serverRequest("update");
    }
    
    /**
     * UPDATE RESPONSE
     * This method receives all online players from server and adds them to 
     * the OnlinePlayers in the model. It then updates the lobby view.
     * @param update
     */
    public void updateResponse(String[] update){
       String[] players = Arrays.copyOfRange(update, 1, update.length);        
        model.updateOnlinePlayers(players);
        lobby.updateOnlinePlayers(model.getPlayerNames());
        lobby.updateMyRecords(model.getMyWins(), model.getMyPlayed());
    }
    
    /**
     * LOGIN REQUEST
     * Uses username and password provided to loginRequest
     * @param username the username
     * @param password the password
     */
    public void loginRequest(String username, String password){
        cController.serverRequest("login<&>"+username+"<&>"+password);
    }
    
    /**
     * LOGIN RESPONSE
     * Relays whether this client has successfully logged onto the server
     * @param success the state of the login request
     * @param details contains errors or, if successful, contains username
     */
    public void loginResponse(boolean success, String details){
        if(success){
            this.username = details;
            model.setUsername(username);
            lobby = new LobbyController(username, this);
            mainMenu.dispose();            
        }else{
            displayLoginMessage(details);
        }        
    }
    
    /**
     * LOGOUT REQUEST
     * Sends a logout request from this to the server.
     * Disposes the game lobby, closes ClientConnection, and opens the MainMenuView
     */
    public void logoutRequest(){
        while(model.hasGame()){
            model.popGame().leave();
        }
        cController.serverRequest("logout");
        lobby.dispose();
        refreshClient();
    }
    
    /**
     * REGISTER REQUEST
     * Uses the username, password, and email provided to attempt to registerRequest
     * a new UserProfile.
     * @param username requested username
     * @param password requested password
     */
    public void registerRequest(String username, String password){
        cController.serverRequest("register<&>"+username+"<&>"+password);
    }
    
    /**
     * REGISTER RESPONSE
     * Receives a response to a register request
     * @param success true if successfully registered
     * @param error an error to display to the client
     */
    public void registerResponse(boolean success, String error){
        if(success){
            mainMenu.clearFields();
            displayLoginMessage("Profile Created. Please Login.");
        }else{
            displayLoginMessage(error);
        }
    }
        
    /**
     * SEND CHALLENGE
     * Sends a challenge from this ClientConnection to opName
     * @param opName 
     */
    public void challengeRequest(String opName){
        cController.serverRequest("challengeRequest<&>"+opName);
    }
    
    public void notOnlineResponse(String opName){
        System.out.println("not online controller "+opName);
        lobby.notOnlineFrame(opName);
    }
    
    /**
     * RECEIVE CHALLENGE
     * Receives challenge from challengerName 
     * @param challengerName 
     */
    public void incomingChallenge(String challengerName){
        lobby.incomingChallenge(challengerName);
    } 
    
    /**
     * SEND RESPONSE TO CHALLENGE
     *      if accept :
     * Hosts a new Game and sends a response to challenger with IP and port
     *      else :
     * Sends a response to challenger saying reject
     * @param accept 
     */
    public void respondToChallenge(String opName, boolean accept){
        if(accept){
            model.addGame(new GameController(opName, this));
        }else{//reject
            cController.serverRequest("challengeResponse<&>"+opName+"<&>reject");
        }
    }
    
    /**
     * Sends the hosting IP and port to the challenging client.
     * @param opName
     * @param ip
     * @param port 
     */
    public void sendHostAddress(String opName, String ip, int port){
        cController.serverRequest("challengeResponse<&>"+opName+"<&>accept<&>"+ip+"<&>"+port);
    }
    
    /**
     * RECEIVE RESPONSE TO CHALLENGE
     * Receives the username and response (true/false) of the person who responded
     * if accept it will call connectToHost
     * @param response contains responseToChallenge, username, ip, port
     */
    public void challengeResponse(String[] response){
        String opName = response[2];
        if(response[1].equals("reject")){
            lobby.challengeDenied(opName);
        }else{
            String ip = response[3];
            int port = Integer.parseInt(response[4]);
            model.addGame( new GameController( opName,this,ip,port));
        }
    }
    
    /**
     * Adds a GameView to the lobbyView
     * @param opName
     * @param newGame 
     */
    public void addGameViewToLobby(String opName, GameView newGame){
        lobby.addGameView(opName, newGame);
    }
    
    public void leaveGame(GameController oldGame){
        oldGame.leave();
        model.removeGame(oldGame);
    }
    
    /**
     * Returns this username
     */
    public String getUsername(){
        return this.username;
    }
    
    public void sendGameResults(String result){
        cController.serverRequest("endGame<&>"+result);
    }
    
    public void leaderboardRequest(){
        cController.serverRequest("leaders");
    }
    
    public void leaders(String[] leaders){
        String[] topFive=new String[5];
        for(int i=1;i<leaders.length;i++){
            topFive[i]=leaders[i];
        }
        
        lobby.leaders(leaders);
    }

}
