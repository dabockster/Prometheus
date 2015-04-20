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
package server.manager;

import server.storage.ServerModel;
import server.connection.UserConnection;
import server.connection.ServerConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.storage.UserProfile;

/**
 *
 * @author Vivo & Timothy
 */
public class ServerController {
    private String filename;
    private ServerModel model;
    private ServerView view;
    private ServerConnection connection;
    private InetAddress address;
    private File database;
    
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
        setupModel(null);
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
            fileName = "/resources/accounts.txt";
        try {
            File file = new File(getClass().getResource(fileName).toURI());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String profileString;
            while((profileString = bufferedReader.readLine()) != null){
                if(profileString == null)
                    break;
                UserProfile profile = new UserProfile(profileString);
                model.addProfile(profile);
            }
            sendServerFeedback("Successfully retreived profiles.");
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            sendServerFeedback("Error: Cannot find file " + fileName + " not found.");
        } catch (IOException ex) {
            sendServerFeedback("Error: Cannot read file "+fileName+".");
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateViewProfiles();
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
            fileName = "/resources/accounts.txt/";
        try{
            File file = new File(getClass().getResource(fileName).toURI());
            try (PrintWriter out = new PrintWriter(file)) {
                while( model.numberOfProfiles()>0 ){
                    System.out.println(model.registeredUserCount());
                    String nextProfile = model.pullProfileString();
                    sendServerFeedback(nextProfile);
                    out.println(nextProfile);
                }
            }
        }
        catch(IOException ex){
            System.out.println("Could not write to file." + ex);
            sendServerFeedback("Failed to write UserProfiles to file "+fileName+".");
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    
    /**
     * USERCONNECTION - ADD
     * Adds a UserConnection to the model
     * @param ucon the UserConnection to be added
     */
    public void addConnection(UserConnection ucon){
        this.updateAll();
        model.addUserConnection(ucon);
    } 
    
    
    /**
     * UPDATE ALL
     * Broadcasts a server update to all clients connected
     */
    public void updateAll(){
        String cmnd= "updateResponse";
        for(int j = 0; j<model.numberOfConnections(); j++){
            UserConnection thisCon = model.getUserConnection(j);
            cmnd = cmnd + "<&>" + model.getUserConnection(j).toString();                
        }
        for(int i=0; i<model.numberOfConnections(); i++){
            UserConnection ucon = model.getUserConnection(i);
            System.out.println("USER CONNECTIONS :  " + ucon.getUsername() + "    CMND : "+cmnd);
            update(ucon, cmnd);
        }
    }
    
    /**
     * UPDATE VIEW - ONLINE PROFILES
     */
    public void updateViewConnections(){
        view.clearOnlineProfiles();
            for(int j = 0; j<model.numberOfConnections(); j++){
                String username = model.getUserConnection(j).getUsername();
                view.addOnlineProfile(username);
            }      
    }
    
    /**
     * UPDATE VIEW - REGISTERED PROFILE
     * @param username the username to be added to the ServerView
     */
    public void updateViewProfiles(){
        view.clearRegisteredProfiles();
        for(int j = 0; j<model.registeredUserCount(); j++){
            UserProfile user = model.getUserProfile(j);
            view.addRegisteredProfile(user.getUsername());
        }
    }
    
    /**
     * UPDATE
     * Sends a list of all online users in String representation.
     * @param ucon 
     */
    public void update(UserConnection ucon, String cmnd){
        ucon.sendCmnd(cmnd);
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
            sendClientFeedback("Failed login.");
        }else{
            profile = model.getUserProfile(username);
            if( profile.isOnline() ){
                ucon.sendCmnd("loginResponse<&>failure<&>alreadyOnline");
                sendClientFeedback("Tried to login but is already online.");  
            }else if(profile.hasPassword(password)){
                profile.logon();
                ucon.setUserProfile(profile);
                ucon.sendCmnd("loginResponse<&>success");
                sendClientFeedback("I made it!");
                this.updateViewConnections();
                System.out.println("LOGIN");
            }else{
                ucon.sendCmnd("loginResponse<&>failure<&>invalidPassword");
                sendClientFeedback("Tried to login with the incorrect password.");
            }
            updateAll();
        }
    }
    

    /**
     * EXECUTE COMMAND - LOGOUT
     * Safely terminates a user's connection with the server
     * @param ucon
     */
    public void logout(UserConnection ucon){
        model.removeUserConnection(ucon);
        this.updateAll();
        this.updateViewProfiles();
        this.updateViewConnections();
    }
    
    /**
     * EXECUTE COMMAND - REGISTER
     * Registers a new UserProfile with the specified username,
     * password, and email.
     * @param ucon the UserConnection that is registering the new UserProfile
     * @param cmnd
     */
    public void register(UserConnection ucon, String[] cmnd){
        sendClientFeedback("Attempting to register a new UserProfile...");
        String username = cmnd[1];
        String password = cmnd[2];
        UserProfile newProfile; 
        if( model.usernameExists(username) ){ 
            sendClientFeedback("...failed to register a UserProfile.");
            ucon.sendCmnd("registerResponse<&>failure<&>alreadyExists");
        }else{
            sendClientFeedback("...registration complete.");            
            newProfile = new UserProfile(username, password);
            ucon.sendCmnd("registerResponse<&>success");
            model.addProfile(newProfile);
            view.addRegisteredProfile(newProfile.getUsername());
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
