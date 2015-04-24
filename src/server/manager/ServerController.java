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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import server.connection.ServerConnection;
import server.connection.UserConnection;

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
    
    private boolean serverUp = false;
    
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
        }catch (UnknownHostException ex){
            view.serverFeedback("Failed to identify this machine's IP address.");
        }
        view.setVisible(true);
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
    
    /**
     * GETTER
     * Returns the current state of the server
     */
    public boolean serverRunning(){
        return serverUp;
    }
    
    /**
     * SERVERCONNECTION - SETUP
     * Creates the ServerConnection using the local address and the specified port number.
     * @param port the port to be used by the server
     */
    public void setupConnection(int port){
        connection = new ServerConnection(this,port);
        new Thread(connection).start();
        setupModel(null);
        serverUp = true;
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
        serverUp = false;
    }
    
    /**
     * MODEL - SETUP
     * Initializes the model data from File fileName
     * @param fileName the name of the file containing the data
     */
    private void setupModel(String fileName){
        try {
            File file = new File("accounts.txt");
                    if(!file.exists())
                        file.createNewFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String profileString;
            while((profileString = bufferedReader.readLine()) != null){
                if(profileString == null)
                    break;
                UserProfile profile = new UserProfile(profileString);
                model.addProfile(profile);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            sendServerFeedback("Error: Cannot find file.");
        } catch (IOException ex) {
            sendServerFeedback("Error: Cannot read file.");
        }
        updateViewProfiles();
    }
    
    /**
     * MODEL - TEARDOWN
     * Writes all string representations of registered userProfiles
     * to the designated File with name fileName. If no fileName is specified
     * write to "accounts.txt"
     * @param fileName the File to be written
     */
    public void teardownModel(){
        try{
            File file = new File("accounts.txt");
            try (PrintWriter out = new PrintWriter(file)) {
                while( model.numberOfProfiles()>0 ){
                    System.out.println(model.registeredUserCount());
                    String nextProfile = model.pullProfileString();
                    sendServerFeedback(nextProfile);
                    out.println(nextProfile);
                }
                out.close();
            }
        }
        catch(IOException ex){
            System.out.println("Could not write to file." + ex);
            sendServerFeedback("Failed to write UserProfiles to file.");
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
        String response= "updateResponse";
        for(int j = 0; j<model.numberOfConnections(); j++){
            UserConnection ucon = model.getUserConnection(j);
            if(ucon.loggedOn)
                response = response + "<&>" + model.getUserConnection(j).toString();                
        }
        for(int i=0; i<model.numberOfConnections(); i++){
            UserConnection ucon = model.getUserConnection(i);
            System.out.println("USER CONNECTIONS :  " + ucon.getUsername() + "    CMND : "+response);
            if(ucon.loggedOn)
                update(ucon, response);
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
        ucon.sendResponse(cmnd);
    }
    
    /**
     * LOGIN REQUEST
     * Authenticates username and password and assigns
     * the connection's UserProfile to the corresponding UserProfile.
     * @param ucon
     * @param request the strings used for login
     */
    public void login(UserConnection ucon, String[] request){
        String username = request[1];
        String password = request[2];
        UserProfile profile;
        if(username.equals("anonymous")){    //For anonymous gameplay
            profile = new UserProfile(model.getAnonName(), password);
            model.addProfile(profile);
            view.addRegisteredProfile(profile.getUsername());
            profile.logon();
            ucon.setUserProfile(profile);
            ucon.setAnon(true);
            ucon.loggedOn = true;
            ucon.sendResponse("loginResponse<&>success");
            sendClientFeedback(ucon.getUsername()+": Anonymous Login Succssful");
            this.updateViewConnections();
            updateAll();
        }else if( !model.usernameExists(username) ){  //username does not exist
            ucon.sendResponse("loginResponse<&>failure<&>nonexistent");
            sendClientFeedback(ucon.getUsername()+": Login Failed - Invalid Username");
        }else{  
            profile = model.getUserProfile(username);
            if( profile.isOnline() ){   //is already online
                ucon.sendResponse("loginResponse<&>failure<&>alreadyOnline");
                sendClientFeedback(ucon.getUsername()+": Login Failed - Profile Already Logged In");
            }else if(profile.hasPassword(password)){ //succesfful login
                profile.logon();
                ucon.setUserProfile(profile);
                ucon.loggedOn = true;
                ucon.sendResponse("loginResponse<&>success");
                sendClientFeedback(ucon.getUsername()+": Login Succssful");
                this.updateViewConnections();
                updateAll();
            }else{ //incorrect password
                ucon.sendResponse("loginResponse<&>failure<&>invalidPassword");
                sendClientFeedback(ucon.getUsername()+": Login Failed - Invalid Password");
            }
        }
    }
    

    /**
     * LOGOUT REQUEST
     * Safely terminates a user's connection with the server
     * @param ucon
     */
    public void logout(UserConnection ucon, boolean isAnon){
        if(isAnon){
            model.removeAnon(ucon.getUsername());
        }
        model.removeUserConnection(ucon);
        this.updateAll();
        this.updateViewProfiles();
        this.updateViewConnections();
    }
    
    /**
     * REGISTER REQUEST
     * Registers a new UserProfile with the specified username,
     * password, and email.
     * @param ucon the UserConnection that is registering the new UserProfile
     * @param cmnd
     */
    public void register(UserConnection ucon, String[] cmnd){
        String username = cmnd[1];
        String password = cmnd[2];
        UserProfile newProfile; 
        if( model.usernameExists(username) ){ 
                sendClientFeedback(ucon.getUsername()+": Registration Failed - Username Taken");
            ucon.sendResponse("registerResponse<&>failure<&>alreadyExists");
        }else{
                sendClientFeedback(ucon.getUsername()+": Registration Successful");
            newProfile = new UserProfile(username, password);
            model.addProfile(newProfile);
            view.addRegisteredProfile(newProfile.getUsername());
            ucon.sendResponse("registerResponse<&>success");
        }
    }
    
    /**
     * RELAY CHALLENGE RESPONSE
     * relays response to UserConnection of the challenger
     * sends details if accept, no details if reject
     * @param challengerName
     * @param details
     * @param accept 
     */
    public void relayChallengeResponse(String challengerName,String[] details,boolean accept){
        UserConnection uCon=model.getUserConnection(challengerName);
        uCon.relayChallengeResponse(details,accept);
    }
    
}
