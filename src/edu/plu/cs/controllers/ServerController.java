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

import edu.plu.cs.models.ServerConnection;
import edu.plu.cs.models.ServerModel;
import edu.plu.cs.models.UserConnection;
import edu.plu.cs.views.ServerView;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Vivo
 */
public class ServerController {
    private ServerModel model;
    private ServerView view;
    private ServerConnection connection;
    private InetSocketAddress sock;
    //ServerController Constructor
    public ServerController(InetSocketAddress sock){model = new ServerModel(this); view = new ServerView(this); view.setVisible(true); this.sock = sock; connection = new ServerConnection(sock, this); model.updateViewAccounts();}
    /*
    addConnection
    */
    public void addConnection(UserConnection ucon){
        UserConnection tempConRef = ucon;
        model.addUserConnection(ucon);}
    /*
    recvCmd
    process the command, and enacts the appropriate method
    */
    public void recvCmd(String msg){
        System.out.println("srvCntrl msg: " + msg);
        String[] msgC = msg.split("<&>");
        int conIDIndex = msgC.length -1;
        switch(msgC[0]){
            case "Login":
                if(model.login(msgC[1], msgC[2], msgC[3])){this.sendCmd("Callback Login<&>true", msgC[3]);}else{this.sendCmd("Callback Login<&>false", msgC[conIDIndex]);}
                break;
            case "Register":
                if(model.register(msgC[1], msgC[2], msgC[3])){this.sendCmd("Callback Register<&>true", msgC[4]);}else{this.sendCmd("Callback Register<&>false", msgC[conIDIndex]);} //msgC[4] contains conID
                break;
            case "CONABORT":
                this.sendCmd("CONABORT", msgC[1]);
                this.remove(Integer.parseInt(msgC[1]));
                break;
        }
    }
    /*
    updateConnections
    TODO
    */
    public void updateConnections(String cons){}
    public void updateAccountView(String content){view.setAccountDisplay(content);}
    /*
    remove
    @param ID - the ID of the UserConnection to remove
    */
    public void remove(int ID) {
        model.remove(ID);
    }
    /*
    sendCmd
    */
    public void sendCmd(String msg, String conID){model.handle(msg, conID);}
    
}
