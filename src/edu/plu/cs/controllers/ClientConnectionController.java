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
package edu.plu.cs.controllers;

import edu.plu.cs.models.Client;
import java.net.InetSocketAddress;

/**
 *
 * @author Vivo
 */
public class ClientConnectionController {
    private Client client;
    private ClientController clientCtrl;
    InetSocketAddress conAddr;
    private String ip;
    private int port;
    //constructor default
    public ClientConnectionController(){ip = "localhost"; port = 8080; conAddr = new InetSocketAddress(ip, port); client = new Client(conAddr);};
    /*
    constructor
    @param String ip to connect to;
    @param int port of server;
    */
    public ClientConnectionController(String ip, int port){this.ip = ip; this.port = port; conAddr = new InetSocketAddress(ip, port); client = new Client(conAddr);}
    /**
     * setClientController
     * @param clientCtrl 
     */
    public void setClientController(ClientController clientCtrl){this.clientCtrl = clientCtrl;}
    /**
     * conStatus
     * @return TRUE if connection is closed
     */
    public boolean conStatus(){return client.conStatus();}
    
    public void sendCmd(String cmd, String data){client.send(cmd +"<&>"+data);}
    public void recvCmd(String msg) {
        
        String[] msgC = msg.split("<&>");
        switch(msgC[0]){
            case "Callback Login":
                System.out.println(msgC[0] +" : "+ msgC[1]);
                if(msgC[1].equals("true")){clientCtrl.setLoginStatus(true);}else{clientCtrl.setLoginStatus(false);}
                break;
            case "Callback Register":
                if(msgC[1].equals("true")){System.out.println("Successful Registration!");}else{System.out.println("Unsuccessful Registration.");}
                break;
            case "CONABORT": //Server has requested connection termination
                client.stop();
                break;
            case "CONCLOSED":
                System.out.println("Client connection to server was terminated.");
                break;
        }
    }
}
