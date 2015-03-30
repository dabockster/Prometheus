/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.plu.cs.controllers;

import edu.plu.cs.views.LobbyView;

/**
 *
 * @author PLUCSCE
 */
public class LobbyController {
    
    private LobbyView view;
    private ClientController controller;
    
    public LobbyController(ClientController ctrl){
        view = new LobbyView(this);
        view.setVisible(true);
        controller = ctrl;
    }
    
    //----- send data -----
    
    public boolean challengePlayer(String opponent){
        return true;
    }
    
    public void respondToChallenge(boolean ans){
    }
    
    //retrieve data
    public void getServerData(){
        
    }
    
    //update View
    public void updatePlayers(String[] onlinePlayers){
        view.updatePlayers(onlinePlayers);
    }
    
    public void openView(String view){
        controller.openView(view);
    }
    
    public void dispose(){
        view.dispose();
    }
     
     
}
