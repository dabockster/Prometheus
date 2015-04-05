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

import edu.plu.cs.views.MainMenuView;

/**
 * MainMenuController class
 * @author dabockster
 */
public class MainMenuController {
    
    private MainMenuView view;
    private ClientController controller;
    
    
    /**
     * MainMenuController constructor
     * @param ctrl ClientController inheritance
     */
    public MainMenuController(ClientController ctrl){
        view = new MainMenuView(this);
        view.setVisible(true);
        controller = ctrl;
    }
    
    /**
     * Closes the view
     */
    public void dispose(){
        view.dispose();
    }
    
    /**
     * Opens a new view outside of the current view
     * @param view The view to be opened (eg "Offline" for the offline mode).
     */
    public void openView(String view){
        controller.openView(view);
    }
    
    /**
     * Exits the entire program
     * @param exitStatus the status of the exit (0 for clean exit, 1 for errors, etc)
     */
    public void quitProgram(int exitStatus){
        controller.quitProgram(exitStatus);
    }
    
    /**
     * Logs into the server
     * @param username the specified username
     * @param password the specified password
     */
    public void login(String username, String password){
        
        //Housekeeping - may delete later from ClientModel
        controller.setUsername(username);
        controller.setPassword(password);
        
        //Login object for ease and simplicity
        LoginController login = new LoginController(username, password, this);
        
        //execute login
        login.execute();
    }

    public void setLoginStatus(boolean status) {
        controller.setLoginStatus(status);
    }
}
