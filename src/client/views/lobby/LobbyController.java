/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.views.lobby;

import client.manager.ClientController;
import client.views.dialogues.RequestDialog;

/**
 *
 * @author PLUCSCE
 */
public class LobbyController {
    
    private final LobbyView view;
    private RequestDialog challenger;
    private final ClientController controller;
    
    public LobbyController(ClientController ctrl){
        view = new LobbyView(this);
        view.setVisible(true);
        controller = ctrl;
    }
    
    /**
     * Sends a challenge to the server to the specified opponent.
     * @param opName 
     */
    public void challenge(String opName){
        controller.challengeRequest(opName);
    }
    
    public void incomingChallenge(String opName){
        challenger = new RequestDialog(view, this, opName);
    }
    
    public void acceptChallenge(){
        controller.respondToChallenge(true);
    }
    
    public void rejectChallenge(){
        controller.respondToChallenge(false);
    }
    
    //update View
    public void updateOnlinePlayers(String[] onlinePlayers){
        view.updatePlayers(onlinePlayers);
    }
    
    public int logout(){
        controller.logoutRequest();
        return 0;
    }
    
    public void dispose(){
        view.dispose();
    }
     
     
}
