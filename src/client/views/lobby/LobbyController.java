/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.views.lobby;

import client.manager.ClientController;
import gameplay.GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.Timer;

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
     * @param opName 
     */
    public void incomingChallenge(String opName){
        view.challengeFrame(opName);
    }
    
    public void challengeDenied(String opName){
        view.rejectFrame(opName);
    }
    
    public void notOnlineFrame(String opName){
        view.notOnlineFrame(opName);
    }
    
    public void disposeNotOnlineFrame(NotOnlineFrame frame){
        frame.dispose();
    }
    
    public void disposeRejectionFrame(RejectionFrame frame){
        frame.dispose();
    }
    
    public void timedDispose(JInternalFrame frame){
        //source: http://stackoverflow.com/a/16748460
        int delay = 20000; //20000 ms = 20 sec
        
        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
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
    
    public void updateMyRecords(int wins, int gamesPlayed){
        view.updateMyRecords(wins,gamesPlayed);
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
    
    public void leaderboardRequest(){
        controller.leaderboardRequest();
    }
    
    public void leaders(String[] leaders){
        view.updateLeaderboard(leaders);
    }
     
     
}
