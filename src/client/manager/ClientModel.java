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
package client.manager;

import gameplay.GameController;
import server.manager.UserProfile;
import java.util.ArrayList;

/**
 * ClientModel Class
 * @author dabockster
 */
public class ClientModel {
    
    private ClientController controller;
    private ArrayList<UserProfile> onlinePlayers;
    private ArrayList<GameController> currentGames;
    private String username;
    private UserProfile profile;
    /**
     * GameModel constructor
     */
    public ClientModel(ClientController controller){
        this.controller = controller;
        onlinePlayers = new ArrayList<UserProfile>();
        currentGames = new ArrayList<>();
    }
    
    /**
     * sets this username equal to newName
     * @param newName 
     */
    public void setUsername(String newName){
        this.username = newName;
    }
    
    /**
     * UPDATE ONLINE PLAYERS
     * @param newProfiles 
     */
    public void updateOnlinePlayers(String[] newProfiles){
        onlinePlayers.clear();
        for(int i=0; i<newProfiles.length; i++){
            this.addPlayer(newProfiles[i]);
        }
    }
    
    /**
     * UPDATE ONLINE PLAYERS
     * @param ucon 
     */
    private void addPlayer(String profileString){
        UserProfile newProfile = new UserProfile(profileString);
        if(newProfile.getUsername().equals(username)){
            this.profile = newProfile;
        }else{
            onlinePlayers.add(newProfile);
        }
     }
    
    /**
     * Returns an array of Strings of all online players
     * @return 
     */
    public String[] getPlayerNames(){
        if(onlinePlayers == null)
            return null;
        String[] playerNames = new String[onlinePlayers.size()];
        
        for(int i=0; i<playerNames.length; i++){
           playerNames[i] = onlinePlayers.get(i).getUsername();
        }
        return playerNames;
    }
    
    public int getMyPlayed(){
        if(profile == null)
            return 0;
        return profile.getPlayed();
    }
    
    public int getMyWins(){
        if(profile == null)
            return 0;
        return profile.getWins();
    }
    
    /**
     * Adds a GameController to the list of CurrentGames
     * @param newGame the GameController to be added
     */
    public void addGame(GameController newGame){
        currentGames.add(newGame);
    }
    
    /**
     * Removes a GameController from the list of CurrentGames
     * @param oldGame 
     */
    public void removeGame(GameController oldGame){
        currentGames.remove(oldGame);
        //ensure game results are sent to server,
        //somehow account for crashes
    }
    
    /**
     * Returns the number of current games
     * @return the number of games
     */
    public int numberOfGames(){
        return currentGames.size();
    }
    
    
    
}
