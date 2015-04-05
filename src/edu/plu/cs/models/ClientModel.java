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
package edu.plu.cs.models;

import java.util.LinkedList;

/**
 * ClientModel Class
 * @author dabockster
 */
public class ClientModel {
    
    private LinkedList<String> opponentNames;
    private int gameNum;
    private String userName;
    private String password;
    
    /**
     * GameModel constructor
     */
    public ClientModel(){
        opponentNames = new LinkedList<>();
        gameNum = 0;
    }

    /**
     * Gets a list of the current opponents
     * @return a list of the current active opponents
     */
    public LinkedList<String> getOpponentNames() {
        return opponentNames;
    }

    /**
     * Adds an opponent to the opponents list
     * @param opponent the opponent to be added
     */
    public void addOpponent(String opponent){
        opponentNames.add(opponent);
    }
    
    /**
     * Removes an opponent from the opponents list
     * @param opponent the opponent to be removed
     * @return true if the opponent was removed successfully
     */
    public boolean removeOpponent(String opponent){
        if (opponentNames.contains(opponent)){
            opponentNames.remove(opponent);
            return true;
        }
        return false;
    }

    /**
     * Gets the current number of active games
     * @return the number of active games
     */
    public int getGameNum() {
        return gameNum;
    }

    /**
     * Sets the current number of active games
     * @param gameNum the number of active games
     */
    public void setGameNum(int gameNum) {
        this.gameNum = gameNum;
    }

    /**
     * Gets the username of the current logged in user
     * @return the username of the logged in user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user logging in
     * @param userName the username of the user logging in
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password that the user entered
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password that the user entered
     * @param password the user's specified password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
