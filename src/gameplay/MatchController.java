/*
 * The MIT License
 *
 * Copyright 2015 PLUCSCE.
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

package gameplay;

import client.manager.ClientController;

/**
 * 
 * @author PLUCSCE
 */
public class MatchController {
    
    //data fields
    ClientController controller;
    MatchView view;
    PeerToPeerConnection connection;
    
    /**
     * HOST CONSTRUCTOR
     * Establishes this client as the Host of the match and creates a 
     * PeerToPeerConnection where it will accept the connecting client.
     * @param controller the client controller to which this will communicate
     */
    public MatchController(ClientController controller){
        this.controller = controller;
        //creates view
        //creates PeerToPeerConnection to host
    }
    
    /**
     * CONNECTING CONSTRUCTOR
     * Establishes that this client is NOT the host. It will create a
     * PeerToPeerConnection where it will connect to the host.
     * @param controller the client controller to which this will communicate
     * @param ip the IP address of the host
     * @param port the port number of the host
     */
    public MatchController(ClientController controller, String ip, int port){
        this.controller = controller;
        //creates view
        //creates PeerToPeerConnection to connect
    }
}
