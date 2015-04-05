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
    private boolean loginStatus;
    
    /**
     * GameController constructor
     */
    public ClientController(){
        mainMenu = new MainMenuController(this);
        model = new ClientModel();
        loginStatus = false;
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
        return loginStatus;
    }
    
}
