
package client.connection;

import client.manager.ClientController;
import java.net.InetSocketAddress;

/**
 *
 * @author Vivo
 */
public class ClientConnectionController {
    private final ClientController controller;

    private ClientConnection connection;
    private InetSocketAddress conAddr;
    
    
    private String opIp;
    private int opPort;
    

    /**
     * CONSTRUCTOR - MODIFIED
     * @param ip to confirmConnection;
     * @param port of server;
     */
    public ClientConnectionController(ClientController controller, String ip, int port){
        this.controller = controller;
        conAddr = new InetSocketAddress(ip, port);
        this.newConnection(ip,port);
    }

    
    public void newConnection(String iP, int portNum){
        if(connectedToServer()){
            connection.close();
        }
        conAddr = new InetSocketAddress(iP,portNum);
        connection = new ClientConnection(this, conAddr);
        new Thread(connection).start();
    }
    
    /**
     * Terminates ClientConnection
     */
    public void close(){
        connection.close();
    }

    
    /**
     * NETWORK COMMUNICATIONS - SEND CMND
     * @param request
     * @param cmnd 
     */
    public void serverRequest(String request){
        if(!connection.connected)
            connectionResponse("false");
        connection.serverRequest(request);
    }
    
    /**
     * INTERPRET COMMUNICATIONS
     * Receives a string, assesses the string's purpose, then executes an operation.
     * This method takes an array of Strings and interprets the first index 
     * to determine an operation. The method then passes the unused indexes
     * of the string array to the method of the subsequent operation.
     * @param response the string array to be interpreted 
     */    
    public void interpretResponse(String[] response) {
        switch(response[0]){
            case "challengeResponse":
                controller.challengeResponse(response);
                break;
            case "connect":
                this.connectionResponse(response[1]);
                break;
            case "incomingChallenge":
                controller.incomingChallenge(response[1]);
                break;
            case "loginResponse":
                this.loginResponse(response);
                break;
            case "registerResponse":
                this.registrationResponse(response);
                break;
            case "updateResponse":
                this.updateResponse(response);
                break;
            case "notOnline" :
                controller.notOnlineResponse(response[1]);
                break;
            case "leader":
                controller.leaders(response);
                break;
        }
    }
    
    /**
     * CONNECT RESPONSE
     */
    private void connectionResponse(String response){
        if(response.equals("true")){
            
            controller.connectResponse(true);
        }else if(response.equals("false")){
            controller.connectResponse(false);
        }else{
            System.out.println("FAILED");
        }
    }
    
    /**
     * LOGIN RESPONSE
     */
    private void loginResponse(String[] response){
        if(response[1].equals("success")){
            controller.loginResponse(true, response[2]);
        }else if(response[1].equals("failure")){
            switch(response[2]){
                case "nonexistent":
                    controller.loginResponse(false, "Invalid username.");
                    break;
                case "alreadyOnline":
                    controller.loginResponse(false, "The account you entered is already online.");
                    break;
                case "invalidPassword":
                    controller.loginResponse(false, "Invalid password.");
                    break;
            }
        }else{
            controller.loginResponse(false, "Unknown login error.");
        }
    }
    
    
    /**
     * REGISTER RESPONSE
     */
    private void registrationResponse(String[] response){
        if(response[1].equals("success")){
            controller.registerResponse(true, null);
        }else if(response[1].equals("failure")){
            switch(response[2]){
                case "alreadyExists":
                    controller.registerResponse(false, "That username already exists.");
                    break;
            }
        }
    }
    
    /**
     * EXECUTE RESPONSE - ONLINE PLAYERS
     * @param request 
     */
    private void updateResponse(String[] request){
        controller.updateResponse(request);
    }
    
    /**
     * CHECKER
     * Checks to see if this client is connected the server
     * @return true if a connection has been established with the server
     */
    public boolean connectedToServer(){
        if(connection == null)
            return false;
        return connection.connected;
    }
    
    /**
     * SERVER FEEDBACK
     */
    public void sendServerFeedback(String response){
        controller.displayLoginMessage(response);
    }
    
    public void interpretDetails(String[] ipPort){
        opIp=ipPort[0];
        opPort=Integer.parseInt(ipPort[1]);
    }
    
    

}
