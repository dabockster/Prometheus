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

/**
 *
 * @author PLUCSCE
 */
public class GameThread implements Runnable {
    
    private GameController controller;
    
    public boolean connectedToOpponent;
    
    private boolean gameOver; //remains false while nobody has won
    private boolean myTurn;    
    private boolean spacesLeft;
    
    /**
     * Constructor for Human GamePlay
     */
    public GameThread(GameController controller){
        this.controller = controller;
        connectedToOpponent = true;
        gameOver = false;
        
    }
    
    /**
     * TO DO:
     * 
     * ~ Determine method for who goes first.
     * ~ Determine end game conditions.
     * ~ Build AI
     * 
     */
    
    
    /**
     *  Main GamePlay method
     */
    @Override
    public void run() {
        
       while(connectedToOpponent && !gameOver){ //as long as a client is connected and nobody won the thread goes on
           
           while(spacesLeft){ //as long as spaces are left the game is not over
               
               
               
           }//spaceLeft
       
       }//connectedToOpponent && !gameOver
       
    }//run
    
}
