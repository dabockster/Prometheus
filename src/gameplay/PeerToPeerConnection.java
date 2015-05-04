package gameplay;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection implements Runnable{
    
    //shared data fields
    private GameController controller;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    private String clientID;
    private boolean isHost; //isHost
    protected Thread worker;
    public boolean connected = false;   
    
    
    private ServerSocket hostSocket;
    private Socket socket;        
    private String ip;
    private int port;    

    /**
     * HOST CLIENT
     * Creates a Host PeerToPeerConnection
     * @param controller the GameController for this game
     */
    public PeerToPeerConnection(GameController controller){
        this.controller = controller;
        clientID = "HostClient";
        isHost = true;
        isHost = true;
        System.out.println("PeerToPeerConnection: Constructor: HostClient");
    }
    
    /**
     * CONNECTING CLIENT
     * Creates a Connecting PeerToPeerConnection
     * @param controller
     * @param ip
     * @param port
     */
    public PeerToPeerConnection(GameController controller, String ip, int port){
        this.controller = controller;
        this.ip = ip;
        this.port = port;
        clientID = "ConnectingClient";
        isHost = false;
        System.out.println("PeerToPeerConnection: Constructor: ConnectingClient");
    }
    
    /**
     * Sets this clientID to include a username
     */
    public void setID(String username){
        this.clientID = username+": "+clientID;
                
    }
    
    /**
     * Opens a socket
     * or Gets InputStream and OutputStream from socket
     * or all the above
     */
    public synchronized void open(){
        if(connected){ //Already connected but needs to get streams
            try{
                streamIn = new DataInputStream(socket.getInputStream());
                streamOut = new DataOutputStream(socket.getOutputStream());
                System.out.println(clientID+": open: socket");
            }catch(IOException e){
                System.out.println(clientID+": open: socket: failed: IOException: "+e);
            }
        }else if(!isHost) { //ConnectingClient
            try{
                socket = new Socket(ip, port);
                streamIn = new DataInputStream(socket.getInputStream());
                streamOut = new DataOutputStream(socket.getOutputStream());
                connected = true;
                System.out.println(clientID+": open: socket: IP:"+ip+" Port:"+port);
            }catch(IOException ex){
                connected = false;
                System.out.println(clientID+": open: socket: failed: IOException: "+ex);
            }
        }else if(isHost){ //HostClient
            try{
                hostSocket = new ServerSocket(0);
                port = hostSocket.getLocalPort();
                ip = InetAddress.getLocalHost().getHostAddress();
                System.out.println(clientID+": open: hostSocket: IP:"+ip+" Port:"+port);
            }catch(IOException e){
                System.out.println(clientID+": open: hostSocket: failed: IOException: "+e);
            }
        }
    }
    
    /**
     * Closes the socket
     * Closes the InputStream and OutputStream
     */
    public synchronized void close(){
        if(connected){
            try{
                socket.close();
                streamIn.close();
                streamOut.close();
                System.out.println(clientID+": close: socket");        
            }catch(IOException ioe){
                System.out.println(clientID+": close: IOException:"+ioe);
            }
        }else if(isHost){
            try{
                hostSocket.close();
                System.out.println(clientID+": close: hostSocket");        
            }catch(IOException ioe){
                System.out.println(clientID+": close: IOException:"+ioe);
            }
        }
    }
    
    public void start() {
        worker = new Thread(this);
        worker.start();
        System.out.println(clientID+": start");
    }
    
    /**
     * if hostWaiting
     *  waits for connectingClient to connect then creates a socket to communicate
     * else this receives communication from a socket
     */
    @Override
    public void run(){
        System.out.println(clientID+" run");
        open();
        if(isHost){ //HostClient listening
            try{
                controller.sendHostAddress(ip, port);
                socket = hostSocket.accept();
                System.out.println(clientID+": run: accept");
                close(); //closes hostSocket
                connected = true;
                open(); //gets streams
                controller.sendConnect(); 
            }catch(IOException e){
                System.out.println(clientID+": run: accept: failed: IOException: "+e);
            }
        }
        while(connected){  //interprets requests from other client
            try{
                String request = streamIn.readUTF();
                System.out.println(clientID+": receive: "+request);
                String response[] = request.split("<&>");
                controller.interpretRequest(response);
            }catch(IOException ioe){
                System.out.println(clientID+": receive: failed: IOException:"+ioe);
                connected = false; 
            }
        }
    }
    
    
    /**
     * NETWORK COMMUNICATION
     * serverRequest()
     * Write to socket and sends the command to server
     * @param request the command for the server
     */
    public void send(String request){
        try {
            streamOut.writeUTF(request);
            streamOut.flush();
            connected = true;
            System.out.println(clientID+": send: "+request);
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.out.println(clientID+": failedToSend: "+request+": IOException: "+ioe);
            //String[] error = {"connect","false"};
            //controller.interpretResponse(error);
        }
    }
}
