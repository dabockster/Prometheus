/*
 * The MIT License
 *
 * Copyright 2015 dabockster.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package client.manager;

import client.connection.ClientConnectionController;
import client.views.lobby.LobbyController;
import client.views.mainMenu.MainMenuController;
import client.views.offline.OfflineController;
import java.util.Arrays;

/**
 * ClientController class
 * @author dabockster
 */
public class ClientController {
    
    private MainMenuController mainMenu;
    private OfflineController offline;
    private ClientModel model;
    private LobbyController lobby;
    private boolean connectedToServer = false;
    private ClientConnectionController cController;
    
    /**
     * ClientController constructor
     */
    public ClientController(){
        cController = new ClientConnectionController(this);
        mainMenu = new MainMenuController(this);
        model = new ClientModel();
        cController.serverRequest("connect"); //sends a connect request to server
    }
    
    /**
     * Sends a challenge from this ClientConnection to opName
     * @param opName 
     */
    public void challengeRequest(String opName){
        cController.serverRequest("challengeRequest<&>"+opName);
    }
    
    /**
     * Receives challenge from challengerName 
     * @param challengerName 
     */
    public void incomingChallenge(String challengerName){
        lobby.incomingChallenge(challengerName);
    } 
    
    /**
     * Sends this ClientConnection's response to the challenger
     * @param accept 
     */
    public void respondToChallenge(boolean accept){
        if(accept){
            //host the a new game 
            //create a ServerSocket
            //send response to challenge with ip and port
            //wait for client to connect.
        }else{
            //sends a reject response
        }
    }
    
    /**
     * Receives response to the challenge that this ClientConnection sent
     * @param response 
     */
    public void challengeResponse(String[] response){
        //response[1] the username of the person who responsed
        //response[2] their response (accept / reject)
        //if accept:
            //response[3] ip 
            //response[4] port
    }

    /**
     * NEW CONNECTION TO SERVER
     * Connects to a specified IP address and port
     * @param ip
     * @param port 
     */
    public void newConnection(String ip, int port){
        if(cController != null)
            cController.close();
        cController = new ClientConnectionController(this,ip,port);
        connectRequest();
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
    public void connectRequest(){
        cController.serverRequest("connect");
    }    
    
    /**
     * CONNECT RESPONSE
     * Displays a message from the server
     */
    public void connectResponse(boolean connected){
        if(connected){
            connectedToServer = true;
            sendServerFeedback("Connected to Server");
        }else{
            connectedToServer = false;
            sendServerFeedback("NOT Connected to Server");
        }   
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
     * @param error any error that has occurred
     */
    public void loginResponse(boolean success, String error){
        if(success){
            lobby = new LobbyController(this);
            mainMenu.dispose();            
        }else{
            sendServerFeedback(error);
        }        
    }
    
    /**
     * LOGOUT REQUEST
     */
    public void logoutRequest(){
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
     * @param success
     * @param error 
     */
    public void registerResponse(boolean success, String error){
        if(success){
            mainMenu.clearFields();
            sendServerFeedback("Profile Created. Please Login.");
        }else{
            sendServerFeedback(error);
        }
    }
    
    /**
     * UPDATE REQUEST
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
    }

    /**
     * Closes a view
     * @param view The view to be closed (eg "Offline" for the offline mode).
     */
    public void closeView(String view){
        switch(view){
            case "Main Menu":
                mainMenu.dispose();
                break;
            case "Offline":
                offline.disposeView();
                break;
        }
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
        cController = new ClientConnectionController(this);
        model = new ClientModel();
        connectedToServer = false;
        mainMenu = new MainMenuController(this);
        cController.serverRequest("connect");
    }


    
    public void sendServerFeedback(String feedback){
        mainMenu.sendFeedback(feedback);
    }
    
    /**
     * called from dialogue box button
     * sends to ClientConnectionController
     * @param opName
     * @param accept 
     */
    public void challengeResponse(String opName,boolean accept){
        if(accept){
            //cController.interpretResponse("challengeResponse<&>"+opName+"<&>true");
        }
    }

}
