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
package timbot.brain;

import timbot.HumanController;

/**
 *
 * @author Timothy Ernst
 */
public class TimbotController {
    private HumanController human;
    private TimbotModel model;
    private int gamePhase;
    
    final int WAITING_FOR_OPPONENT_MOVE = 0;
    
    private final int HUMAN = 1;
    private final int TIMBOT = 2;    
    
    
    
    /**
     * Constructor
     * @param controller 
     */
    public TimbotController(HumanController controller){
        this.human = controller;
        model = new TimbotModel(this,30);
        gamePhase = WAITING_FOR_OPPONENT_MOVE;
    }
    
    
    /**
     * Receives a human play and adds it to the model
     * @param x
     * @param y 
     */
    public void humanPlay(int x, int y){
        model.humanMove(x,y);
    }
    
    /**
     * Sends timbot's play to human
     * @param x
     * @param y 
     */
    public void timbotPlay(int x, int y){
        human.botPlay(x,y);
    }
    

}
