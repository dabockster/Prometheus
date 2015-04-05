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

/**
 *
 * @author dabockster
 */
public class LoginController {
    
    private String username;
    private String password;
    private String toTransmit;
    private MainMenuController menuController;
    
    public LoginController(String username, String password, MainMenuController controller){
        this.username = username;
        this.password = password;
        this.menuController = controller;
        constructTransmission();
    }
    
    private void constructTransmission(){
        //Build what needs to be transmitted to server
        StringBuilder sb = new StringBuilder();
        sb.append(username);
        sb.append("<&>");
        
        //TODO: Use hashing library here to hash password before transmit
        //SHA-256 is preferred
        
        sb.append(password); //we will hash the password in a later deliverable      
        
        //set the transmit variable to what we've built
        toTransmit = sb.toString();
    }
    
    public void execute(){
        //HELP!!!
        
        menuController.setLoginStatus(true);
    }
}
