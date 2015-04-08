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
package edu.plu.cs.controllers;

import edu.plu.cs.models.ClientModel;

/**
 * ClientController class
 * @author dabockster
 */
public class ClientController {
    
    private MainMenuController mainMenu;
    private OfflineController offline;
    private ClientModel model;
    private LobbyController lobby;
    private boolean isOnline;
    private ClientConnectionController cController;
    
    /**
     * GameController constructor
     */
    public ClientController(){
        cController = new ClientConnectionController(this);
        mainMenu = new MainMenuController(this);
        model = new ClientModel();
        isOnline = false;
    }
    
    public void connect(){
        cController.connect();
    }
    
    /**
     * LOGIN REQUEST
     * Uses username and password provided to loginRequest
     * @param username the username
     * @param password the password
     */
    public void loginRequest(String username, String password){
        String data = username+"<&>"+password;
        cController.sendCmnd("login", data);
    }
    
    /**
     * LOGIN RESPONSE
     */
    public void loginResponse(boolean success, String error){
        if(success){
            mainMenu.dispose();
            lobby = new LobbyController(this);
        }else{
            sendServerFeedback(error);
        }        
    }
    
    /**
     * LOGOUT REQUEST
     */
    public void logoutRequest(){
        cController.sendCmnd("logout",null);
        lobby.dispose();
        mainMenu = new MainMenuController(this);
    }
    
    /**
     * REGISTER REQUEST
     * Uses the username, password, and email provided to attempt to registerRequest
     * a new UserProfile.
     * @param username requested username
     * @param password requested password
     * @param email requested email
     */
    public void registerRequest(String username, String password){
        String credentials = username+"<&>"+password;
        cController.sendCmnd("register", credentials);
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
    
    public boolean getLoginStatus(){
        return isOnline;
    }

    public void setLoginStatus(boolean status) {
        this.isOnline = status;
    }
    
    public void sendServerFeedback(String feedback){
        mainMenu.sendServerFeedback(feedback);
    }
    
}
