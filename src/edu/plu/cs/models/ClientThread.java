package edu.plu.cs.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ermenildo V. Castro, Jr.
 * References:
 * pirate.shu.edu
 */
import java.net.*;
import java.io.*;
public class ClientThread extends Thread{
    private Socket socket = null;
    private Client client = null;
    private DataInputStream streamIn = null;
    private boolean contRun = true;
    private BufferedReader bReader = null;
    private char[] charBuf = new char[200];
    private int msgLen;
    private String msg;
    /**
     * ClientThread()
     * Constructor
     * @param client - client connection reference
     * @param socket - socket to read from
     */
    public ClientThread(Client client, Socket socket){
        this.client = client;
        this.socket = socket;
        this.open();
        this.start(); 
    }
    
    /**
     * open()
     * Open the input stream to read from socket
     */
    public void open(){
        try{streamIn = new DataInputStream(socket.getInputStream());
        bReader = new BufferedReader(new InputStreamReader(streamIn));
        }catch(IOException ioe){
            System.out.println("Failed to Open, Motherfucker: "+ ioe);
        }
    }
    
    /**
     * close()
     * End inputStream
     */
    public void close(){
        try{
            if(streamIn != null){
                streamIn.close();
            }
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
    
    /**
     * run()
     * RUN instance invoked by Thread
     */
    public void run(){
        while(contRun){
            try{System.out.println("clientThread AM RUNNING");
            msgLen = bReader.read(charBuf); 
                if(msgLen > 0){msg = new String(charBuf, 0, msgLen);
                client.handle(msg);
                }
                else{
                    System.out.println("Connection failure");
                    contRun = false;
                }
            }catch(IOException ioe){
                System.out.println("Smack a hoe" + ioe);
                contRun = false; client.stop();
            }
        }
    }
    
    //streamIn.readUTF()
}
