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

/**
 *
 * @author Vivo
 * UserProfile:
 *  This class serves as the template for managing user information
 */
public class UserProfile {
    private String username;
    private String password;
    private String email;
    private UserStatistic statistic;
    
    //constructor default
    public UserProfile(){
        username = "undefined";
        password = "undefined";
        email = "undefined";
        statistic = new UserStatistic(this);
    }
    
    /*
    UserProfile(username, password, email)
    Constructor for Userprofile
    @param username - String of the user's name
    @param password - String of the password
    @param email - String of the user's email
    TODO: ENCRYPTION
    */
    public UserProfile(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        statistic = new UserStatistic(this);
    }
    
    //Implementation of constructor using String array {username, password, email}
    public UserProfile(String[] tuple){
        this.username = tuple[0]; 
        this.password = tuple[1]; 
        this.email = tuple[2]; 
        statistic = new UserStatistic(this);
    }
    
    /*
    UserProfile(username, password)
    Constructor for Userprofile
    @param username - String of the user's name
    @param password - String of the password
    TODO: ENCRYPTION
    */
    public UserProfile(String username, String password){
        this.username = username; 
        this.password = password; 
        this.email = "undefined"; 
        statistic = new UserStatistic(this);
    }
    /*
    getUserProfile()
    @return - a String array containing all the user information [username][password][email]
    */
    public String[] getUserProfile(){
        String[] content = {username, password, email}; 
        return content;
    }
   
}
