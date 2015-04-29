package gameplay;

import client.manager.ClientController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PLUCSCE
 */
public class PeerToPeerConnection implements Runnable{
    
    //shared data fields
    private GameController controller;
    private DataOutputStream streamOut;
    private DataInputStream streamIn;
    private String clientType;
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
        clientType = "HostClient";
        isHost = true;
        isHost = true;
        System.out.println("PeerToPeerConnection: /Constructor:HostClient");
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
        clientType = "ConnectingClient";
        isHost = false;
        System.out.println("PeerToPeerConnection: /Constructor:ConnectingClient");
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
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /getStreams:Success");
            }catch(IOException e){
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /getStreams:Failed");
            }
        }else if(!isHost) { //ConnectingClient
            try{
                socket = new Socket(ip, port);
                streamIn = new DataInputStream(socket.getInputStream());
                streamOut = new DataOutputStream(socket.getOutputStream());
                connected = true;
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /IP:"+ip+" /Port:"+port);
            }catch(IOException ex){
                connected = false;
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /IP:"+ip+" /Port:"+port+" /IOException:"+ex);
            }
        }else if(isHost){ //HostClient
            try{
                hostSocket = new ServerSocket(0);
                port = hostSocket.getLocalPort();
                ip = hostSocket.getInetAddress().getHostAddress();
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /IP:"+ip+" /Port:"+port);
            }catch(IOException e){
                System.out.println("PeerToPeerConnection: /open:"+clientType+" /IP:"+ip+" /Port:"+port+" /IOException:"+e);
            }
        }
    }
    
    /**
     * Closes the socket
     * Closes the InputStream and OutputStream
     */
    private synchronized void close(){
        if(connected){
            try{
                socket.close();
                streamIn.close();
                streamOut.close();
                System.out.println("PeerToPeerConnection: /close:"+clientType);        
            }catch(IOException ioe){
                System.out.println("PeerToPeerConnection: /close:"+clientType+" /IOException:"+ioe);
            }
        }else if(isHost){
            try{
                hostSocket.close();
                System.out.println("PeerToPeerConnection: /close:"+clientType);        
            }catch(IOException ioe){
                System.out.println("PeerToPeerConnection: /close:"+clientType+" /IOException:"+ioe);
            }
        }
    }
    
    public void start() {
        worker = new Thread(this);
        worker.start();
        System.out.println("PeerToPeerConnection: /start:"+clientType);
    }
    
    /**
     * if hostWaiting
     *  waits for connectingClient to connect then creates a socket to communicate
     * else this receives communication from a socket
     */
    @Override
    public void run(){
        System.out.println("PeerToPeerConnection: /run:"+clientType);
        open();
        if(isHost){ //HostClient listening
            try{
                controller.sendHostAddress(ip, port);
                socket = hostSocket.accept();
                close(); //closes hostSocket
                connected = true;
                open(); //gets streams
                System.out.println("PeerToPeerConnection: /run:"+clientType+" /accept:Success");
            }catch(IOException e){
                System.out.println("PeerToPeerConnection: /run:"+clientType+" /accept:Failed /stack:"+e);
            }
        }
        while(connected){  //interprets requests from other client
            try{
                String request = streamIn.readUTF();
                String response[] = request.split("<&>");
                System.out.println("Received Response: "+response[0]);
                if(response[0].equals("disconnect")){
                        connected = false;
                        this.close();
                }
                controller.interpretRequest(response);
            }catch(IOException ioe){
                System.out.println("Failed to receive response." + ioe);
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
    public void request(String request){
        try {
            streamOut.writeUTF(request);
            streamOut.flush();
            connected = true;
            System.out.println("PeerToPeerConnection: /request:"+clientType+" /sucessfullySent:"+request);
        } catch (IOException ioe) {
            System.out.println(ioe);
            System.out.println("PeerToPeerConnection: /request:"+clientType+" /failedToSend:"+request+" /IOException:"+ioe);
            //String[] error = {"connect","false"};
            //controller.interpretResponse(error);
        }
    }
}
