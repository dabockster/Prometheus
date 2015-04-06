/*
 * The MIT License
 *
 * Copyright 2015 dabockster.
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
package edu.plu.cs;

import edu.plu.cs.controllers.ClientController;
import edu.plu.cs.controllers.ServerController;
import java.net.InetSocketAddress;

/**
 * Prometheus main class
 * @author dabockster
 */
public class Prometheus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //boolean variable determines if MAIN should initialize a server controller
        boolean initServerController = true;
        
        
        // TODO code application logic here
        ClientController controller = new ClientController();
        if(initServerController){
            String ip = "localhost";
            int port = 8080;
            InetSocketAddress sock = new InetSocketAddress(ip, port);
            ServerController srvCntrl = new ServerController(sock);}
    }
    
}
