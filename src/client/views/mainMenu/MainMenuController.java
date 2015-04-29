
package client.views.mainMenu;

import client.manager.ClientController;
import javax.swing.JFrame;

/**
 * MainMenuController class
 * @author dabockster
 */
public class MainMenuController {
    
    private final MainMenuView view;
    private final ClientController controller;
    
    
    /**
     * MainMenuController constructor
     * @param ctrl ClientController inheritance
     */
    public MainMenuController(ClientController ctrl){
        controller = ctrl;
        view = new MainMenuView(this);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //CHANGE TO EXIT_ON_CLOSE when running Client and Server on dif machines
        view.setVisible(true);
    }
    
    /**
     * Attempts to connect to a specific ip
     * @param ip
     * @param port 
     */
    public void connect(String ip, int port){
        controller.newConnection(ip, port);
    }
    
    /**
     * Closes the view
     */
    public void dispose(){
        view.dispose();
    }
    
    /**
     * QUIT 
     * Exits the entire program
     * @param exitStatus the status of the exit (0 for clean exit, 1 for errors, etc)
     */
    public void quitProgram(int exitStatus){
        controller.quitProgram(exitStatus);
    }

    
    /**
     * LOGIN
     * Logs into the server
     * @param username the specified username
     * @param password the specified password
     */
    public void login(String username, String password){
        controller.loginRequest(username, password);
    }

    /**
     * REGISTER
     * @param username
     * @param password 
     */
    public void register(String username, String password){
        controller.registerRequest(username, password);
    }
    
    /**
     * OFFLINE
     * Creates an offline game
     */
    public void offline(){
        controller.offlineGame();
        view.dispose();
    }
    
    /**
     * VIEW - CLEAR TEXT FIELDS
     */
    public void clearFields(){
        view.clearFields();
    }

    /**
     * VIEW - FEEDBACK
     * @param response 
     */
    public void sendFeedback(String response){
        view.postFeedback(response);
    }

}
