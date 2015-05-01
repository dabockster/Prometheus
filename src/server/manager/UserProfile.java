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

/**
 *
 * @author Vivo
 * UserProfile:
 *  This class serves as the template for managing user information
 */
public class UserProfile {
    private boolean online = false;
    private final String username;
    private final String password;
    private int played;
    private int wins;
    
    //constructor default
    public UserProfile(){
        username = "undefined";
        password = "undefined";
        played = -1;
        wins = -1;
    }
    
    /**
     * CONSTRUCTOR
     * Creates a UserProfile object from the string
     * produced by this.toString()
     * @param profileString the string that has all relevant UserProfile data
     */
    public UserProfile(String profileString){
        String[] userSpecs = profileString.split(",");
        this.username = userSpecs[0];
        this.password = userSpecs[1];
        this.played = Integer.parseInt(userSpecs[2]);
        this.wins = Integer.parseInt(userSpecs[3]);
    }
    
    /**
     * UserProfile(username, password)
     * Constructor for UserProfile
     * @param username - String of the user's name
     * @param password - String of the password
     * @param total
     * @param won
    */
    public UserProfile(String username, String password, int total, int won){
        this.username = username; 
        this.password = password; 
        this.played = total;
        this.wins = won;
    }
    
    /**
     * CONSTRUCTOR - NEW PROFILE
     * Constructor for UserProfile
     * @param username - String of the user's name
     * @param password - String of the password
    */
    public UserProfile(String username, String password){
        this.username = username; 
        this.password = password; 
        this.played = 0;
        this.wins = 0;
    }
    
    /**
     * GETTER
     * returns this UserProfile's username
     * @return this username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * ACTION
     * Sets this UserProfile to signify that it is logged on
     */
    public void logon(){
        online = true;
    }
    
    /**
     * ACTION
     * Sets this UserProfile to signify that it is no longer online 
     */
    public void logout(){
        online = false;
    }
    
    /**
     * ACTION
     * increments the number of games played and number of wins
     */
    public void won(){
        this.wins ++;
    }
    
    /**
     * ACTION
     * increments the number of games played and number of wins
     */
    public void played(){
        this.played ++;
    }
    
    /**
     * ACTION
     * Creates a string representation of this UserProfile
     * @return the string representation of this UserProfile
     */
    @Override
    public String toString(){
        return this.username+","+this.password+","+this.played+","+this.wins;
    }    
    
    /**
     * CHECKER
     * Checks if this UserProfile is online
     * @return true if this UserProfile is already linked to a UserConnection
     */
    public boolean isOnline(){
        return online;
    }
    
    /**
     * CHECKER
     * Checks if this UserProfile has the username username.
     * @param username
     * @return true if this.username username username
     */
    public boolean isNamed(String username){
        return this.username.equals(username);
    }
    
    /**
     * CHECKER
     * Checks if this UserProfile has the password pass.
     * @param pass
     * @return returns true if this.password equals pass
     */
    public boolean hasPassword(String pass){
        return this.password.equals(pass);
    }

}
