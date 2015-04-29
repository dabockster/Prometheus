/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.views.lobby;

import client.manager.ClientController;

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
    
    /**
     * Receives challenge from challengerName and displays a RequestDialogue 
     * @param challengerName 
     */
    public void incomingChallenge(String challengerName){
        System.out.println("Challenger: "+ challengerName);
        challenger = new RequestDialog(view, this, challengerName);
    }
    
    /**
     * Sends an accept Challenge response to the challenger
     */
    public void acceptChallenge(String opName){
        controller.respondToChallenge(opName, true);
    }
    
    /**
     * Sends a reject Challenge response to the challenger
     */
    public void rejectChallenge(String opName){
        controller.respondToChallenge(opName, false);
    }
    
    //update View
    public void updateOnlinePlayers(String[] onlinePlayers){
        view.updatePlayers(onlinePlayers);
    }
    
    /**
     * Logs the user out
     * @return 
     */
    public int logout(){
        controller.logoutRequest();
        return 0;
    }
    
    /**
     * Dispose the view
     */
    public void dispose(){
        view.dispose();
    }
     
     
}
