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
package edu.plu.cs.models;
import edu.plu.cs.controllers.ServerController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vivo
 * stores all server data
 * 
 */
/*
Data Fields:
controller : ServerController
	the ServerController that controls this ServerModel
cons : ArrayList<UserConnection>
	This is an array list of all UserConnection currently connected to the server.
accounts : ArrayList<UserProfile>
	This is an array list of all the registered UserProfiles that exist.
Methods:
isOnline(String username) : boolean
	returns true if the specified user is online
usernameTaken(String username) : boolean
	returns true if the specified username is already taken
getUserConnection(String username) : UserConnection
	returns the UserConnection with the specified username
getUserProfile(String username) : UserProfile
	returns the UserProfile with the specified username
addRegisteredProfile(UserProfile newProfile) : void
	adds newProfile to the ArrayList of allRegisteredPlayers

*/
public class ServerModel {
    private ServerController scntrl;
    final private int MAXCONS = 50;
    private ArrayList<UserProfile> accounts;
    private int clientCount = 0;
    private UserConnection[] clientSet = new UserConnection[MAXCONS];
    private String ACCOUNTDB = "ACCOUNT.csv"; // C:\Users\Vivo\Documents\GitHub\Prometheus\ACCOUNT.csv
    private String USERSTATDB; //TODO
    private String MATCHDB; //TODO
    //constructor
    public ServerModel(ServerController controller){
        this.scntrl = controller; 
        accounts = new ArrayList<UserProfile>();
        this.initializeDB(); 
    }
    
    /*
    initializeDB()
    Initializes the dataStructures by reading the csv files
    */
    private void initializeDB(){
    try {
            //INITIALIZE ACCOUNT ARRAY
            //Reads CSV file
            //CSV FILE FORMAT PER LINE: "username","password","email"
            BufferedReader br = new BufferedReader(new FileReader(ACCOUNTDB));
            String tuples = "";
            int rowCount = 0;
            while((tuples = br.readLine()) != null){
                String[] entries = tuples.split(",");
                System.out.println("entires: "+entries[0] +":"+ entries[1] +":" + entries[2]);
                UserProfile profile = new UserProfile(entries);
                accounts.add(profile);
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("File 404: " + ACCOUNTDB);
        } catch (IOException ex) {
            System.out.println("tuple read error");
        }
    }
    
    /*
    isOnline(String username) : boolean
	@return true if the specified user is online
    */
    public boolean isOnline(String uname){
        System.out.println("isOnline UNIMPLEMENTED");
        return false;
    }
    
    /*
    usernameTaken(String username) : boolean
            @return true if the specified username is already taken
    */
    public boolean usernameTaken(String uname){
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUserProfile()[0].equals(uname)){
                return true;
            }
        }
        return false;
    }
    /*
    getUserConnection(String username) : UserConnection
            @return the UserConnection with the specified username
    */
    /*
    
    getUserProfile(String username) : UserProfile
            returns the UserProfile with the specified username
            If profile is non-existent, will return UserProfile with "undefined"
    */
    public UserProfile getUserProfile(String uname){
        UserProfile profile = new UserProfile();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUserProfile()[0].equals(uname)){
                profile = accounts.get(i);
            }
        }
        return profile;
    }
    /*
    addRegisteredProfile(UserProfile newProfile) : void
            adds newProfile to the ArrayList of allRegisteredPlayers
    */
    public void addRegisteredProfile(UserProfile profile){
        if(!usernameTaken(profile.getUserProfile()[0])){
            accounts.add(profile);
        }
    }
    /*
    flushACCOUNTDB()
    Writes the accounts data into the ACCOUNT Database
    */
    public void flushDB(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(ACCOUNTDB));
            for(int i = 0; i < accounts.size(); i++){
                String[] profileInfo = accounts.get(i).getUserProfile();
                String content = profileInfo[0]+","+profileInfo[1]+","+profileInfo[2];
                out.write(content);
                out.newLine();
            }
            out.close();        
        }
        catch(IOException ex){System.out.println("ACCOUNTDB WRITE FAIL");}
        
    }
    /*
    addUserConnection
    updates the server model UserConnection arraylist
    @param ucon - the UserConnection pass-by-reference
    */
    public void addUserConnection(UserConnection ucon){
        clientSet[clientCount] = ucon;
        //update Server View
        //this.updateViewConnections();
    }
    /**
     * handle()
     * Will be called from ServerThread instances
     * Forwards message to list of connections on THIS Server Thread
     * @param msg - the message to broadcast to clientSet
     * @param conID - the connection ID
     */
    public synchronized void handle(String msg, String conID){
        try{
            int clientIndex = findClient(Integer.parseInt(conID));
            if(clientIndex != -1){
                if(clientSet[clientIndex].getID() == Integer.parseInt(conID)){
                }
                    clientSet[clientIndex].sendMessage(msg);
            }
        }catch(NullPointerException n){
            System.out.println("Fucked at Handle.");
        }
    }
    /**
     * remove()
     * Remove connection from the connection set
     * @param ID - integer of the connection ID to remove
     */
    public synchronized void remove(int ID){
        int i = findClient(ID);
        System.out.println("iVal, Server.remove(): " + i);
        if(i >= 0){
            clientSet[i].close(); //clientSet[i].stop();
            System.out.println("Remove(): clientSet[i]: closed.");
        }
        if(i >= 0){ //there exists
            if(i < clientCount -1){ // there exists contents at +1 of index
                for(int k = i+1; k < clientCount; k++){
                    clientSet[k-1] = clientSet[k];
                } //shift values
            }
            clientCount--;
        }
        //update Server View
        this.updateViewAccounts();
    }
    
    /**
     * updateViewConnections
     * Requests the controller to update the view to the current set of connections
     */
    public void updateViewAccounts(){String tempAggregate = "";
        for(int p = 0; p < accounts.size(); p++){
            String[] entries = accounts.get(p).getUserProfile();
            tempAggregate +=  entries[0]+" " +entries[1] + " " +entries[2] + "\n";}
        scntrl.updateAccountView(tempAggregate);
    }
    
    /**
     * findClient()
     * --DEPRECATED--
     * @param ID - the ID to search for
     * @return index of the client within the array
     */
    private int findClient(int ID){
        for(int i = 0; i <= clientCount; i++){
            if(clientSet[i].getID() == ID){
                return i;
            }
        }return -1;
    }
    
    /**
     * login()
     * @param username - string
     * @param password - string TODO: ENCRYPT
     * @return FALSE for invalid credentials, or account is active
     */
    public boolean login(String username, String password, String conID){
        
        if(!acctActive(username) && validCredentials(username, password)){
                int clientIndex = findClient(Integer.parseInt(conID));
                clientSet[clientIndex].setAssociatedAcctName(username); 
                return true;
        }
        return false;
    }
    /**
     * 
     * @param username
     * @param password
     * @param email
     * @return 
     */
    public boolean register(String username, String password, String email){
        if(email.equals("") || email == null){
            email = "undefined";
        }if(!validCredentials(username)){
            String[] entries = {username, password, email}; 
            UserProfile tempProfile = new UserProfile(entries);
            accounts.add(tempProfile); 
            this.updateViewAccounts(); 
            return true;
        } 
        return false;
    }
    
    /**
     * 
     * @param username
     * @return TRUE if Account w/ username is active (online)
     */
    private boolean acctActive(String username){
        for(int i = 0; i < clientCount; i++){
            if(clientSet[i].getAssociatedAcctName().equals(username)){
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * @param username
     * @param password
     * @return TRUE if credentials exist in ACCOUNTDB
     */
    private boolean validCredentials(String username, String password){
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUserProfile()[0].equals(username) && accounts.get(i).getUserProfile()[1].equals(password)){
                return true;
            }
        }
        return false;
    }
    
    private boolean validCredentials(String username){
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUserProfile()[0].equals(username)){
                return true;
            }
        }
        return false;
    }
}
