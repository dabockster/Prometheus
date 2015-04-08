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
package edu.plu.cs.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Vivo & Timothy
 */
public class ServerController {
    private ServerModel model;
    private ServerView view;
    private ServerConnection connection;
    private InetAddress address;
    
    /**
     * CONSTRUCTOR
     * Obtains IP address of the current machine and creates a ServerView.
     */    
    public ServerController(){
        view = new ServerView(this);
        model = new ServerModel(this);
        connection = null;
        try{
            address = InetAddress.getLocalHost();
            view.setIP(address.getHostAddress());
            view.serverFeedback("Server has been created on this machine.");
        }catch (UnknownHostException ex){
            view.serverFeedback("Failed to identify this machine's IP address.");
        }
        view.setVisible(true);
    }
    
    /**
     * SERVERCONNECTION - SETUP
     * Creates the ServerConnection using the local address and the specified port number.
     * @param port the port to be used by the server
     */
    public void setupConnection(int port){
        sendServerFeedback("Setup ServerConnection.");
        connection = new ServerConnection(this,port);
        new Thread(connection).start();
    }
    
    /**
     * SERVERCONNECTION - TEARDOWN
     * Stops the ServerConnection
     */
    public void teardownConnection(){
        if(connection.isStopped){
            this.sendServerFeedback("How does one stop that which never began?");
        }else            
            connection.stop();
    }
    
    /**
     * MODEL - SETUP
     * Initializes the model data from File fileName
     * @param fileName the name of the file containing the data
     */
    private void setupModel(String fileName){
        if(fileName == null)
            fileName = "ACCOUNT.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String profileString = null;
            while((profileString = br.readLine()) != null){
                UserProfile profile = new UserProfile(profileString);
                model.addProfile(profile);
            }
        } catch (FileNotFoundException ex) {
            sendServerFeedback("Error: Cannot find file" + fileName + " not found.");
        } catch (IOException ex) {
            sendServerFeedback("Error: Cannot read file "+fileName+".");
        }
        sendServerFeedback("Setup ServerModel.");
    }
    
    /**
     * MODEL - TEARDOWN
     * Writes all string representations of registered userProfiles
     * to the designated File with name fileName. If no fileName is specified
     * write to "ACCOUNT.csv"
     * @param fileName the File to be written
     */
    public void teardownModel(String fileName){
        if(fileName == null)
            fileName = "ACCOUNT.csv";
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            while( !model.isEmpty() ){
                out.write(model.pullProfile());
                out.newLine();
            }
            out.close();        
        }
        catch(IOException ex){
            sendServerFeedback("Failed to write UserProfiles to file "+fileName+".");
        }
    }   
    
    
    /**
     * USERCONNECTION - ADD
     * Adds a UserConnection to the model
     * @param ucon the UserConnection to be added
     */
    public void addConnection(UserConnection ucon){
        model.addUserConnection(ucon);
    } 
    
    
    /**
     * UPDATE
     * Broadcasts a server update to all clients connected
     * @param cmndType the type of command to be broadcast
     * @param cmndComp the contents of the command to be broadcast
     */
    public void updateCmnd(String cmndType, String[] cmndComp){
        switch(cmndType){
            case "login": 
                //specify which UserProfile came online
                break;
            case "logout":
                //specify which UserProfile went offline
                break;
        }
    }
    
    /**
     * EXECUTE COMMAND - LOGIN
     * Authenticates username and password and assigns
     * the connection's UserProfile to the corresponding UserProfile.
     * @param ucon
     * @param cmnd the strings used for login
     */
    public void login(UserConnection ucon, String[] cmnd){
        sendClientFeedback("Login attempt made...");
        String username = cmnd[1];
        String password = cmnd[2];
        UserProfile profile;
        if( !model.usernameExists(username) ){ 
            ucon.sendCmnd("loginResponse<&>failure<&>nonexistent");
            sendClientFeedback("... login failed.");
        }else{
            profile = model.getUserProfile(username);
            if( profile.isOnline() ){
                ucon.sendCmnd("loginResponse<&>failure<&>alreadyOnline");
                sendClientFeedback("... login failed.");  
            }else if(profile.hasPassword(password)){
                profile.logon();
                ucon.setUserProfile(profile);
                ucon.sendCmnd("loginResponse<&>success");
                sendClientFeedback("... login successful");
                //update users
                //update list of online
            }else{
                ucon.sendCmnd("loginResponse<&>failure<&>invalidPassword");
                sendClientFeedback("... login failed.");

            }
        }
    }

    /**
     * EXECUTE COMMAND - LOGOUT
     * Safely terminates a user's connection with the server
     * @param ucon
     */
    public void logout(UserConnection ucon){
        model.removeUserConnection(ucon);
        //update view
        //update list of online profiles
    }
    
    /**
     * EXECUTE COMMAND - REGISTER
     * Registers a new UserProfile with the specified username,
     * password, and email.
     * @param ucon the UserConnection that is registering the new UserProfile
     * @param cmnd
     */
    public void register(UserConnection ucon, String[] cmnd){
        sendClientFeedback("Attempting to create new UserProfile...");
        String username = cmnd[1];
        String password = cmnd[2];
        UserProfile newProfile;
        if( model.usernameExists(username) ){ 
            sendClientFeedback("...failed to create a UserProfile.");
            ucon.sendCmnd("registerResponse<&>failure<&>alreadyExists");
        }else{
            sendClientFeedback("...successfully created UserProfile.");            
            newProfile = new UserProfile(username, password);
            model.addProfile(newProfile);
            ucon.sendCmnd("registerResponse<&>success");
            //update list of all profiles
        }
    }
   
            
    /** VIEW - FEEDBACK
     * Relays a message to the view to be displayed
     * @param feedback the message to be relayed
     */
    public void sendServerFeedback(String feedback){
        view.serverFeedback(feedback);
    }
    
    /** VIEW - FEEDBACK
     * Relays a message to the view to be displayed
     * @param feedback the message to be relayed
     */
    public void sendClientFeedback(String feedback){
        view.clientFeedback(feedback);
    }    
    
}
