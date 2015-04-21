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

import client.views.offline.OfflineController;
import client.connection.ClientConnectionController;
import client.views.lobby.LobbyController;
import client.views.mainMenu.MainMenuController;
import java.util.Arrays;

/**
 * ClientController class
 * @author dabockster
 */
public class ClientController {
    
    private MainMenuController mainMenu;
    private OfflineController offline;
    private final ClientModel model;
    private LobbyController lobby;
    private final boolean isOnline;
    private ClientConnectionController cController;
    
    /**
     * GameController constructor
     */
    public ClientController(){
        mainMenu = new MainMenuController(this);
        model = new ClientModel();
        isOnline = false;
        cController = new ClientConnectionController(this);
    }
    
    public void connect(){
        if(cController != null)
            cController.close();
        cController.connect(); 
    }
    
    public void connect(String ip, int port){
        if(cController != null)
            cController.close();
        cController.connect(ip,port);
    }
    
    public void acceptChallenge(){
        
    }
    
    public void rejectChallenge(){
        
    }
    
    public void challengeRequest(String opName){
        
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
     */
    public void loginResponse(boolean success, String error){
        if(success){
            mainMenu.openView("Lobby");
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
        mainMenu = new MainMenuController(this);
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
     * @param update
     */
    public void updateResponse(String[] update){
        System.out.println("ClientUpdated");
        String[] players = Arrays.copyOfRange(update, 1, update.length);
        System.out.println(players[0].toString());
        
        model.updateOnlinePlayers(players);
        lobby.updateOnlinePlayers(model.getPlayerNames());
    }
    
    /**
     * Opens a view
     * @param view The view to be opened (eg "Offline" for the offline mode).
     */
    public void openView(String view){
        switch(view){
            case "Main Menu":
                mainMenu = new MainMenuController(this);
                break;
            case "Offline":
                offline = new OfflineController(this);
                break;
            case "Lobby":
                lobby = new LobbyController(this);
                break;
        }
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
                offline.dispose();
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


    
    public void sendServerFeedback(String feedback){
        mainMenu.sendServerFeedback(feedback);
    }
    
}
