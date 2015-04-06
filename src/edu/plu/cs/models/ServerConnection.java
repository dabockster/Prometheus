/*
 * The MIT License
 *
 * Copyright 2015 Vivo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.plu.cs.models;

import edu.plu.cs.controllers.ServerController;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Vivo
 * Ref. CSCE 386: ClientServerCommunications, Ermenildo V. Castro, Jr.
 */
//TODO: Abstract server processes to the ServerController and ServerModel
public class ServerConnection implements Runnable{
    final int MAXCONS = 50;
    private ServerSocket server = null;
    private Thread thread = null;
    private int clientCount = 0;
    private UserConnection[] clientSet = new UserConnection[MAXCONS];
    private ServerController scntrl;
    /**
     * server constructor
     * @param conAddr - socket address for setup: IP & port
     */
    public ServerConnection(InetSocketAddress conAddr, ServerController srvCtrl){
        try{
            this.setController(srvCtrl);
            System.out.print("Binding Port:  ...");
            server = new ServerSocket();
            server.bind(conAddr);
            System.out.println("Server Started.");
            System.out.println("Listening on: " + server.getInetAddress() + " : " + server.getLocalPort());
            //FORK connection into THREAD
            this.start();
        }catch(IOException ioe){System.out.println(ioe);}
        
    }//close server
    /**
     * start()
     * Fork received socket into new thread
     */
    public void start(){if(thread == null){thread = new Thread(this);}thread.start();}
    @Override
    /**
     * run()
     * Process requests for connection until THIS THREAD is ended (false): this.stop()
     */
    public void run(){while(thread != null){
        while(thread != null){try{
            //server waits at ACCEPT until connection received;
            //callback followed by addThread executes
            this.addThread(server.accept());
        }catch(IOException ioe){System.out.println(ioe);}}}}
    /*
    getServerController()
    @return the Controller
    */
    public ServerController getController(){return this.scntrl;}
    /**
     * addThread();
     * Creates new Thread using the received socket
     */
    public void addThread(Socket socket){
        UserConnection ucon = new UserConnection(this, socket);
        scntrl.addConnection(ucon);
       // ServerThread tempST = new ServerThread(this, socket);
        try{ucon.open(); ucon.start();}catch(IOException ioe){System.out.println("ServerConnection: addThread(), UserConnection Error: "+ ioe);}
        //clientSet.add(tempST);
        
        
        
    }
    /**
     * stop()
     * end THREAD
     * TODO: Clear all threads, or argument overload to end particular thread
     */
    public void stop(){if(thread != null){thread.stop(); thread = null;}}

    /**
     * setController()
     * Links server to controller
     * @param cntrl - the controller to reference
     */
    public void setController(ServerController cntrl){scntrl = cntrl;}
    
}
