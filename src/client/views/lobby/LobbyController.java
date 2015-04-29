/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.views.lobby;

import client.manager.ClientController;
import gameplay.GameView;

/**
 *
 * @author PLUCSCE
 */
public class LobbyController {
    
    private final LobbyView view;
    private final ClientController controller;
    
    
    public LobbyController(String username, ClientController ctrl){
        view = new LobbyView(username, this);
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
        RequestDialog challenger = new RequestDialog(view, this, challengerName);
    }
    
    public void challengeDenied(String challengerName){
        view.rejectFrame(challengerName);
    }
    
    public void disposeRejectionFrame(noticeFrame frame){
        frame.dispose();
    }
    
    /**
     * Sends an accept Challenge response to the challenger
     * @param opName
     */
    public void acceptChallenge(String opName){
        controller.respondToChallenge(opName, true);
    }
    
    /**
     * Sends a reject Challenge response to the challenger
     * @param opName
     */
    public void rejectChallenge(String opName){
        controller.respondToChallenge(opName, false);
    }
    
    public void addGameView(String opName, GameView newGame){
        view.addGameView(opName,newGame);
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
