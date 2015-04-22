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

import server.manager.UserProfile;
import java.util.ArrayList;

/**
 * ClientModel Class
 * @author dabockster
 */
public class ClientModel {
    
    private ArrayList<UserProfile> onlinePlayers;
    
    
    /**
     * GameModel constructor
     */
    public ClientModel(){
        onlinePlayers = new ArrayList<UserProfile>();
    }
    
    /**
     * UPDATE ONLINE PLAYERS
     * @param profiles 
     */
    public void updateOnlinePlayers(String[] profiles){
        onlinePlayers.clear();
        System.out.println("ClientModel.updateOnlinePlayers()");
        for(int i=0; i<profiles.length; i++){
            this.addPlayer(profiles[i]);
            System.out.println(profiles[i].toString());
        }
    }
    
    /**
     * UPDATE ONLINE PLAYERS
     * @param ucon 
     */
    private void addPlayer(String profileString){
        System.out.println("ClientModel.addPlayer()");
         onlinePlayers.add(new UserProfile(profileString));
     }
    
    public String[] getPlayerNames(){
        if(onlinePlayers == null)
            return null;
        String[] playerNames = new String[onlinePlayers.size()];
        
        for(int i=0; i<playerNames.length; i++){
           playerNames[i] = onlinePlayers.get(i).getUsername();
        }
        
        return playerNames;
    }
    
}
