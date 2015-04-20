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
    
    private LobbyView view;
    private ClientController controller;
    
    public LobbyController(ClientController ctrl){
        view = new LobbyView(this);
        view.setVisible(true);
        controller = ctrl;
    }
    
    //----- send data -----
    public void challenge(String opName){
        controller.challengeRequest(opName);
    }

    //update View
    public void updateOnlinePlayers(String[] onlinePlayers){
        view.updatePlayers(onlinePlayers);
    }
    
    public void openView(String view){
        controller.openView(view);
    }
    
    public void logout(){
        controller.logoutRequest();
    }
    
    public void dispose(){
        view.dispose();
    }
     
     
}
