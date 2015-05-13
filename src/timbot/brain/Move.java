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

import java.util.ArrayList;

/**
 *
 * @author Timothy Ernst
 */
public class Move {
    public boolean byHuman = false;
    public boolean byTimbot = false;
    
    private Cell cell;
    
    private final Move prior;    
    private final int count;
    private final int HUMAN = 1;
    private final int TIMBOT = 2;    
    
    /**
     * Default Constructor
     * @param player
     */
    public Move( int player, Cell cellPlayed, Move priorMove ){
        if(priorMove != null){
            this.count = priorMove.getCount()+1;
        }else
            this.count = 1;
        this.prior = priorMove;
        if(player == HUMAN)
            byHuman = true;
        else if(player == TIMBOT)
            byTimbot = true;
        cell = new Cell(cellPlayed);
    }
    /**
     * Returns a string representation of this move
     * @return 
     */
    public String toString(){
        String moveString = "Move number: "+this.count+" Played by: ";
        String player = "unknown";
        if( byHuman )
            player = "Human ";
        else if( byTimbot )
            player = "Timbot ";
        moveString = moveString + player + "on cell ("+cell.x+", "+cell.y+")";
        return moveString;
    }
    /**
     * Returns this move count
     * @return 
     */
    public int getCount(){
        return this.count;
    }
    /**
     * Returns true if this was played by the player
     * @param player
     * @return 
     */
    public boolean playedBy(int player){
        if( player == TIMBOT)
            return byTimbot;
        else
            return byHuman;
    }
    /**
     * Returns true if this has a prior move
     * @return 
     */
    public boolean hasPrior(){
        return prior != null;
    }
    /**
     * Checks for new quests
     */
    private void checkNewQuests(){
        
    }
}
