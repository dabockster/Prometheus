/*
 * The MIT License
 *
 * Copyright 2015 Timothy Ernst.
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
package timbot;

import client.manager.ClientController;
import timbot.brain.TimbotController;
/**
 *
 * @author Timothy Ernst
 */
public class HumanController {
    
    private AIView view;
    private OfflineGameModel model;
    private ClientController controller;
    private boolean myTurn;
    private boolean wentFirstLast;
    private TimbotController timbot;
    
    public HumanController(ClientController controller){
        this.controller = controller;
        this.view = new AIView(this);
        view.setVisible(true);
        wentFirstLast = true; //method for picking
        model = new OfflineGameModel(this);
        timbot = new TimbotController(this);
    }
    
    public void toLogin(){
        view.dispose();
        controller.refreshClient();
    }
    
    public void userPlay(int x, int y){
        model.playMove(x,y,1);
        myTurn(false);
        timbot.humanPlay(x,y);
    }
    
    public void botPlay(int x, int y){
        model.playMove(x,y,-1);
        myTurn(true);
    }
    
    /**
     * sets myTurn equal to false
     * @param iJustPlayed 
     */
    private void myTurn(boolean turn){
        if(turn){
            myTurn = true;
            view.turnover(true);
        }else{
            myTurn = false;   
            view.turnover(false);
        }
    }
    
    public void surrender(){
        gameOver(-1);
    }
    
    /**
     * Sends these game results to the server
     * @param results
     */
    public void gameOver(int results){
        if(results == -1){  
            view.loseDisplay();         //loss
            view.playAgainDisplay();
        }else if(results == 0){
            view.tieDisplay();          //tie
            view.playAgainDisplay();
        }else if(results == 1){
            view.winDisplay();          //win
            view.playAgainDisplay();
        }
    }
    
        /**
     * Builds a new OfflineGameModel and scrubs the GameView to have a new game
     */
    public void newGame(){
        model.buildNewGrid(30,30);
        if(wentFirstLast){
            wentFirstLast = false;
            myTurn(false);
        }else{
            wentFirstLast = true;
            myTurn(true);
        }                   
    }
    
    
}
