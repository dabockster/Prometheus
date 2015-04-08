/*
 * The MIT License
 *
 * Copyright 2015 Vivo.
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

import edu.plu.cs.threads.ClientConnection;
import java.net.InetSocketAddress;

/**
 *
 * @author Vivo
 */
public class ClientConnectionController {
    private final ClientController controller;

    private ClientConnection connection;
    private InetSocketAddress conAddr;
    
    private String ip;
    private int port;
    
    /**
     * CONSTRUCTOR - DEFAULT
     * Creates a new ClientConnectionController
     */
    public ClientConnectionController(ClientController controller){
        this.controller = controller;
        ip = "localhost"; 
        port = 8080;
        conAddr = new InetSocketAddress(ip, port);
    }
    
    public void connect(){
        connection = new ClientConnection(this, conAddr);
        new Thread(connection).start();
    }
    
    /**
     * CONSTRUCTOR - MODIFIED
     * @param ip to confirmConnection;
     * @param port of server;
     */
    public ClientConnectionController(ClientController controller, String ip, int port){
        this.controller = controller;
        this.ip = ip;
        this.port = port;
        conAddr = new InetSocketAddress(ip, port);
    }
    
    /**
     * TEARDOWN - CLIENTCONNECTION
     */
    public void teardownConnection(){
        //send disconnection command
    }
    

    
    /**
     * NETWORK COMMUNICATIONS - SEND CMND
     * @param cmndType
     * @param cmnd 
     */
    public void sendCmnd(String cmndType, String cmnd){
        System.out.println("Sent command " + cmndType);
        connection.sendCmnd(cmndType+"<&>"+cmnd);
    }
    
    /**
     * INTERPRET COMMUNICATIONS
     * Receives a string, assesses the string's purpose, then executes an operation.
     * This method takes an array of Strings and interprets the first index 
     * to determine an operation. The method then passes the unused indexes
     * of the string array to the method of the subsequent operation.
     * @param cmndComp the string array to be interpreted 
     */    
    public void interpretCmnd(String[] cmndComp) {
        if(cmndComp[0].equals("server")){ //this is added on the client side for all incoming transmission from the server
            switch(cmndComp[1]){
                case "connect":
                    sendServerFeedback("You are connected to the server.");
                    break;
                case "loginResponse":
                    this.loginResponse(cmndComp);
                    break;
                case "registerResponse":
                    this.registrationResponse(cmndComp);
                    break;
            }
        }
    }
    
    /**
     * EXECUTE RESPONSE - LOGIN 
     */
    private void loginResponse(String[] response){
        if(response[2].equals("success")){
            controller.loginResponse(true, null);
        }else if(response[2].equals("failure")){
            switch(response[3]){
                case "nonexistent":
                    controller.loginResponse(false, "Invalid username.");
                    break;
                case "alreadyOnline":
                    controller.loginResponse(false, "The account you entered is already online.");
                    break;
                case "invalidPassword":
                    controller.loginResponse(false, "Invalid password.");
                    break;
            }
        }else{
            controller.loginResponse(false, "Unknown login error.");
        }
    }
    
    /**
     * EXECUTE RESPONSE - REGISTRATION 
     */
    private void registrationResponse(String[] response){
        if(response[2].equals("success")){
            controller.registerResponse(true, null);
        }else if(response[2].equals("failure")){
            switch(response[3]){
                case "alreadyExists":
                    controller.registerResponse(false, "That username already exists.");
                    break;
            }
        }
    }
    
    /**
     * CHECKER
     * Checks to see if this client is connected the server
     * @return true if a connection has been established with the server
     */
    public boolean isConnectedToServer(){
        return connection.connected;
    }
    
    /**
     * SERVER FEEDBACK
     */
    public void sendServerFeedback(String response){
        controller.sendServerFeedback(response);
    }    

}
