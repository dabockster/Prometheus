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
package client.views.mainMenu;

import client.manager.ClientController;
import javax.swing.JFrame;

/**
 * MainMenuController class
 * @author dabockster
 */
public class MainMenuController {
    
    private final MainMenuView view;
    private final ClientController controller;
    
    
    /**
     * MainMenuController constructor
     * @param ctrl ClientController inheritance
     */
    public MainMenuController(ClientController ctrl){
        view = new MainMenuView(this);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //CHANGE TO EXIT_ON_CLOSE when running Client and Server on dif machines
        view.setVisible(true);
        controller = ctrl;
    }
    
    /**
     * Attempts to connect to a specific ip
     * @param ip
     * @param port 
     */
    public void connect(String ip, int port){
        controller.newConnection(ip, port);
    }
    
    /**
     * Closes the view
     */
    public void dispose(){
        view.dispose();
    }
    
    /**
     * QUIT 
     * Exits the entire program
     * @param exitStatus the status of the exit (0 for clean exit, 1 for errors, etc)
     */
    public void quitProgram(int exitStatus){
        controller.quitProgram(exitStatus);
    }

    
    /**
     * LOGIN
     * Logs into the server
     * @param username the specified username
     * @param password the specified password
     */
    public void login(String username, String password){
        controller.loginRequest(username, password);
    }

    /**
     * REGISTER
     * @param username
     * @param password 
     */
    public void register(String username, String password){
        controller.registerRequest(username, password);
    }
    
    /**
     * OFFLINE
     * Creates an offline game
     */
    public void offline(){
        controller.offlineGame();
        view.dispose();
    }
    
    /**
     * VIEW - CLEAR TEXT FIELDS
     */
    public void clearFields(){
        view.clearFields();
    }

    /**
     * VIEW - FEEDBACK
     * @param response 
     */
    public void sendFeedback(String response){
        view.postFeedback(response);
    }

}
