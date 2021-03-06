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
import server.connection.UserConnection;
import java.util.*;

/**
 * STORES ALL SERVER DATA
 * @author Timothy Ernst
 */
public class ServerModel {
    private final ServerController controller;        
    private final ArrayList<UserProfile> accounts;
    private final ArrayList<UserConnection> online;
    private int numberOfAnon = 0;
    

    /**
     * CONSTRUCTOR
     * Creates a new ServerModel object
     * @param controller the controller to manage this ServerModel
     */
    public ServerModel(ServerController controller){
        this.controller = controller; 
        accounts = new ArrayList<>();
        online = new ArrayList<>();
    }
    
   /**
     * USERCONNECTION - ADD
     * Adds a UserConnection to the ArrayList of UserConnections
     * @param ucon the UserConnection pass-by-reference
     */
    public void addUserConnection(UserConnection ucon){
        online.add(ucon);
    }    

    /**
     * USERCONNECTION - REMOVE
     * Removes connection from the connection set
     * @param ucon - the UserConnection to be removed
     */
    public synchronized void removeUserConnection(UserConnection ucon){
        online.remove(ucon);
    }
    
    /**
     * USERCONNECTION - GET
     * getUserConnection(String username) : UserConnection
     * @return the UserConnection with the specified username
     */ 
    public UserConnection getUserConnection(int i){
        return online.get(i);   
    }
    
    /**
     * Gets the desired UserConnection with the specified username
     * @param username
     * @return 
     */
    public UserConnection getUserConnection(String username){
        UserConnection ucon=null;
        for(UserConnection i:online){
            if(i.getUsername().equals(username)){
                ucon=i;
            }
        }
        return ucon;
    }
    
    /**
     * USERCONNECTION - GET ANON NAME
     * Appends the current number of anonymous players to the end of "anonymous"
     * so there are no duplicate usernames.
     * @return the anonymous name
     */
    public String getAnonName(){
        numberOfAnon++;
        return "Anonymous"+numberOfAnon;
    }
    
    /**
     * USERCONNECTION - CHECKER
     * @return the number of UserConnections currently connected
     */
    public int numberOfConnections(){
        return online.size();
    }
    
    /**
     * USERPROFILE - ADD
     * addProfile(UserProfile newProfile) : void
     * adds newProfile to the ArrayList of allRegisteredPlayers
     * @param profile
     */
    public void addProfile(UserProfile profile){
            sendServerFeedback("Created UserProfile '"+profile.getUsername()+"'");
            accounts.add(profile);
    }
    
    /**
     * USERPROFILE - PULL
     * Removes the UserProfile from ArrayList accounts at index 0.
     * @return a string representation of the UserProfile removed
     */
    public UserProfile pullProfile(){
        if( accounts.isEmpty() ){
            sendServerFeedback("Model Is Empty");
            return null;
        }else{
            return accounts.remove(0);
        }
    }
    
    /**
     * USERPROFILE - GET
     * Retrieves and returns the UserProfile with the username username.
     * If no UserProfiles exist with the specified username
     * this returns a return UserProfile with "undefined"
     */
    public UserProfile getUserProfile(String username){
        try{    
            for(int i = 0; i < accounts.size(); i++){
                if(accounts.get(i).isNamed(username)){
                    return accounts.get(i);  
                }
            }
        } catch( NullPointerException ex){
            sendServerFeedback("Failed to Find UserProfile - '"+username+"' does not exist");
        }
        return null;
    }
    
    /**
     * USERPROFILE - GET
     * Retrieves and returns the UserProfile with the username username.
     * If no UserProfiles exist with the specified username
     * this returns a return UserProfile with "undefined"
     */
    public UserProfile getUserProfile(int i){
        return accounts.get(i);
    }
    
    /**
     * USERPROFILE - REMOVE ANON
     * Removes an anonymous profile from the list of registered profiles.
     * 
     * @param username 
     */
    public void removeAnon(String username){
        for(int i = 0; i < accounts.size(); i++){
                if(accounts.get(i).isNamed(username)){
                    sendServerFeedback("Deleted UserProfile '" + username+"'");
                    accounts.remove(i);  
                }
            }
    }
    
    /**
     * USERPROFILE - SIZE
     */
    public int registeredUserCount(){
        return accounts.size();
    }
    
    /**
     * USERPROFILE - CHECKER
     * @param username the username being searched
     * @return true if the specified username is NOT already taken
     */
    public boolean usernameExists(String username){
        for (UserProfile account : accounts) {
            if (account.isNamed(username)) {
                return true;
            }
        }
        return false;
    } 
    
    /**
     * USERPROFILE - CHECKER 
     * Checks if the specified UserProfile is already online
     * @return true if the specified user is online
    */
    public boolean userIsOnline(String username){
        UserProfile profile = getUserProfile(username);
        if(profile == null)
            return false;
        return getUserProfile(username).isOnline();
    } 
    
    /**
     * USERPROFILE - CHECKER
     * @return the number of registered UserProfiles
     */
    public int numberOfProfiles(){
        return accounts.size();
    }
    
   /**
     * USERPROFILE - CHECKER
     * @return true if there are no UserConnections connected
     */
    public boolean isEmpty(){
        return online.isEmpty();
    }    
       
    
    /**
     * VIEW - FEEDBACK
     * Sends a message to the controller to relay to the view.
     * @param feedback 
     */
    public void sendServerFeedback(String feedback){
        controller.sendServerFeedback("Model: "+ feedback);
    }  
    
    public void incrementGames(UserProfile profile){
        for(UserProfile i:accounts){
            if(i.getUsername().equals(profile.getUsername()))
                i.played();
        }
    }
    
    public void incrementWins(UserProfile profile){
        for(UserProfile i:accounts){
            if(i.getUsername().equals(profile.getUsername()))
                i.won();
        }
    }
    
    public ArrayList<UserProfile> sortedAccounts(){
        
        
        
        return accounts;
    }
}
