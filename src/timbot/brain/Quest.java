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

package timbot.brain;

import java.util.ArrayList;

/**
 *
 * @author PLUCSCE
 */
public class Quest {
    
    private TimbotModel model;
    
    private int orientation;
    
    private final int NULL = -1;
    private final int OPEN = 0;
    private final int HUMAN = 1;
    private final int TIMBOT = 2;    
    private final int DIAGONAL_INC = 6;
    private final int DIAGONAL_DEC = 7;
    private final int HORIZONTAL = 8;
    private final int VERTICAL = 9;
    
    private final boolean isHuman;
    private final boolean isTimbot;
    private final int state;
    
    private ArrayList<Cell> cells;
    private Cell head;
    private Cell tail;
    private Cell fissure;
    
    private ArrayList<Quest> intersecting;
    
    /**
     * Default Constructor
     * @param cell
     * @param orientation
     */
    public Quest(Cell cell, int orientation){
        this.orientation = orientation;
        this.isHuman = cell.isHuman;
        this.isTimbot = cell.isTimbot;
        cells = new ArrayList<Cell>();
        if(this.isTimbot)
            state = 2;
        else
            state = 1;
        cells.add(cell);
    }
    /**
     * Returns the specified cell from this Quest's cells
     * @param i
     * @return 
     */
    private Cell getCell(int i){
        return cells.get(i);
    }
    /**
     * Returns this orientation
     * @param otherOrientation
     * @return 
     */
    public boolean hasOrientation(int otherOrientation){
        return this.orientation == otherOrientation;
    }
    /**
     * Adds a cell to the array of cells in this Quest
     * @param cell
     * @param addFirst 
     */
    public void addCell(Cell cell, boolean addFirst){
        if(addFirst)
            cells.add(0,cell);
        else
            cells.add(cell);
    }
    /**
     * Returns true if a specific cell is part of this Quest
     * @param cell
     * @param inQuestion
     * @return 
     */
    public boolean hasCell(Cell cell){
        if(cells.isEmpty())
            return false;
       return cells.contains(cell);
    }
    /**
     * Considers a cell to be part of a quest
     * @param cell the cell to be considered
     * @param inFront cell is in front of this
     */
    public void considerCell(Cell cell, boolean inFront){
        Quest quest;
        if( cell.hasState(state) ){
            quest = cell.getQuest(orientation);
            if(quest == null)
                this.addCell(cell,inFront);
            else{
                System.out.println("BINDING");
                this.bind(quest,inFront);
            }
        }else if( cell.hasState(OPEN) ){
            this.addCell(cell,inFront);
        }
    }
    /**
     * Binds two Quests together
     * @param quest
     * @param other
     * @param overlap
     * @param inFront if other is in front of this
     */
    public void bind(Quest quest, boolean inFront){
        Cell cell;
        while(quest.hasNext()){
            cell = quest.getNext(inFront);
            cell.setQuest(orientation, this);
            this.addCell( cell, inFront );
        }
        quest.pullQuest();
    }
    public boolean hasNext(){
        return !cells.isEmpty();
    }
    /**
     * Gets the next cell in cells from the front if inFront else from tail 
     * @param fromBack
     * @param inFront
     * @return 
     */
    public Cell getNext(boolean fromBack){
        Cell cell;
        if(cells.isEmpty())
            throw new NullPointerException();
        if(fromBack){
            cell = cells.remove(cells.size()-1);
        }else
            cell = cells.remove(0);
        return cell;
    }  
    public void pullQuest(){
        model.removeQuest(this);
    }
    
    public void assessThreat(){
        //get series size
        //determine state of head & tail
        //
    }
    private Cell getHead(){
        this.head = cells.get(0);
        return head;
    }
    private Cell getTail(){
        this.tail = cells.get(cells.size()-1);
        return tail;
    }
    private boolean hasFissure(){
        return getFissure() != null;
    }
    private Cell getFissure(){
        for(int i=1; i<cells.size()-1;i++){
            if(cells.get(i).hasState(OPEN)){
                fissure = cells.get(i);
                return fissure;
            }
        }
        fissure = null;
        return null;
    }
    
    
    @Override
    public String toString(){
        String orientationStr;
        if( orientation == DIAGONAL_INC)
            orientationStr = "D UP";
        else if(orientation == DIAGONAL_DEC)
            orientationStr = "D DN";
        else if(orientation == HORIZONTAL)
            orientationStr = "HORZ";
        else
            orientationStr = "VERT";
        
        String str = "Quest("+orientationStr+"):";
        for(int i=0;i<cells.size();i++){
            str = str+" "+(i+1)+"."+cells.get(i).toString();
        }
        return str;
    }
}
