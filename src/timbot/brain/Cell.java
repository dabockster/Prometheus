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

/**
 *
 * @author Timothy Ernst
 */
public class Cell {
    
    public boolean isNull = false;
    public boolean isOpen = false;
    public boolean isHuman = false;
    public boolean isTimbot = false;
    
    public int x;
    public int y;
    
    private int state;
    
    private Cell north;  //north
    private Cell northEast; //northeast
    private Cell east;  //east
    private Cell southEast; //southeast
    private Cell south;  //south
    private Cell southWest; //southwest
    private Cell west;  //west
    private Cell northWest; //nortwest
    
    private Quest diagonalInc = null;
    private Quest diagonalDec = null;
    private Quest vertical = null;
    private Quest horizontal = null;
    
    private TimbotModel model;
   
    private final int NULL = -1;
    private final int OPEN = 0;
    private final int HUMAN = 1;
    private final int TIMBOT = 2;    
    private final int DIAGONAL_INC = 6;
    private final int DIAGONAL_DEC = 7;
    private final int HORIZONTAL = 8;
    private final int VERTICAL = 9;

    /**
     * Default Constructor
     * @param xCoordinate
     * @param yCoordinate 
     */
    public Cell(TimbotModel model,int xCoordinate, int yCoordinate){
        this.model = model;
        this.x = yCoordinate;
        this.y = xCoordinate;
    }
    /**
     * Creates a copy of an original cell
     * @param original The cell to be copied
     */
    public Cell( Cell original ){
        this.x = original.x;
        this.y = original.y;
        this.isNull = original.isNull;
        this.state = original.getState();
        if(original.isNull)
            return;
        this.isHuman = original.isHuman;
        this.isTimbot = original.isTimbot;
        
        this.north = original.getNorth();
        this.northEast = original.getNorthEast();
        this.east = original.getEast();
        this.southEast = original.getSouthEast();
        this.south = original.getSouth();
        this.southWest = original.getSouthWest();
        this.west = original.getWest();
        this.northWest = original.getNorthWest();
    }
    /**
     * Sets the north Cell
     * @param n 
     */
    public void setNorth(Cell n){
        if(this.isNull)
            return;
        this.north = n;
        n.south = this;
    }
    public Cell getNorth(){
        return north;
    }
    /**
     * Sets the northWest Cell
     * @param nw 
     */
    public void setNorthWest(Cell nw){
        if(this.isNull)
            return;
        this.northWest = nw;
        nw.southEast = this;
    }
    public Cell getNorthWest(){
        return northWest;
    }
    /**
     * Sets the west Cell
     * @param w 
     */
    public void setWest(Cell w){
        if(this.isNull)
            return;
        this.west = w;
        w.east = this;
    }
    public Cell getWest(){
        return west;
    }
    /**
     * Sets the southWest Cell
     * @param sw 
     */
    public void setSouthWest(Cell sw){
        if(this.isNull)
            return;
        this.southWest = sw;
        sw.northEast = this;
    }
    public Cell getSouthWest(){
        return southWest;
    }
    /**
     * Sets the south Cell
     * @param s 
     */
    public void setSouth(Cell s){
        if(this.isNull)
            return;
        this.south = s;
        s.north = this;
    }
    public Cell getSouth(){
        return south;
    }
    /**
     * Sets the southEast Cell
     * @param se 
     */
    public void setSouthEast(Cell se){
        if(this.isNull)
            return;
        this.southEast = se;
        se.northWest = this;
    }
    public Cell getSouthEast(){
        return southEast;
    }
    /**
     * Sets the east Cell
     * @param e 
     */
    public void setEast(Cell e){
        if(this.isNull)
            return;
        this.east = e;
        e.west = this;
    }
    public Cell getEast(){
        return east;
    }
    /**
     * Sets the northEast Cell
     * @param ne 
     */
    public void setNorthEast(Cell ne){
        if(this.isNull)
            return;
        this.northEast = ne;
        ne.southWest = this;
    }
    public Cell getNorthEast(){
        return northEast;
    }
    /**
     * Returns true if this has links to all corresponding cells
     * @return 
     */
    public boolean isAllSet(){
        if(this.isNull)
            return true;
        else if(this.north == null)
            return false;
        else if(this.northWest == null)
            return false;
        else if(this.west == null)
            return false;
        else if(this.southWest == null)
            return false;
        else if(this.south == null)
            return false;
        else if(this.southEast == null)
            return false;
        else if(this.east == null)
            return false;
        else if(this.northEast == null)
            return false;
        else{
            this.setState(OPEN);
            return true;
        }
    }
    /**
     * Sets this Cell to a null cell
     */
    public void setNull(){
        setState(NULL);
    }
    /**
     * Sets this cell to a human cell
     */
    public void setHuman(){
        setState(HUMAN);
        this.questSearch();
    }
    /**
     * Sets this cell to a Timbot cell
     */
    public void setTimbot(){
        setState(TIMBOT);
        this.questSearch();
    }
    /**
     * Sets the boolean value of this Cell's current state as well as
     * the int value of presentState to a specific value
     * @param state
     */
    private void setState(int state){
        if(this.isNull)
            return;
        
        this.state = state;
        
        if(state == NULL){
            this.isNull = true;
            
        }else if(state == OPEN){
            this.isOpen = true;
            
        }else if(state == TIMBOT){
            this.isOpen = false;
            this.isTimbot = true;
            
        }else if(state == HUMAN){
            this.isOpen = false;
            this.isHuman = true;
        }
    }
    /**
     * Returns this Cell's present state
     * @return 
     */
    public int getState(){
        return this.state;
    }
    /**
     * Returns true if this cell has the state otherState
     * @param otherState
     * @return 
     */
    public boolean hasState(int otherState){
        if(this.isNull){
            return false;
        }
        return this.state == otherState;
    }
    public Quest getQuest(int questOrientation){
        switch(questOrientation){
            case DIAGONAL_INC :
                return this.diagonalInc;
            case DIAGONAL_DEC :
                return this.diagonalDec;
            case HORIZONTAL :
                return this.horizontal;
            case VERTICAL : 
                return this.vertical;
            default :
                return null;
        }
    } 
    public void setQuest(int questOrientation, Quest quest){
        switch(questOrientation){
            case DIAGONAL_INC :
                this.diagonalInc = quest;
            case DIAGONAL_DEC :
                this.diagonalDec = quest;
            case HORIZONTAL :
                this.horizontal = quest;
            case VERTICAL : 
                this.vertical = quest;
        }
    } 
    /**
     * This holds true for the following four methods
     * @return 0 : no sequence
     *         1 : is first of sequence
     *         2 : is middle of sequence
     *         3 : is end of sequence
     */ 
    private Quest findDiagonalIncQuest(){
        if(this.diagonalInc == null){
            diagonalInc = new Quest(this, DIAGONAL_INC);
            model.addQuest(diagonalInc);
        }
        if(!diagonalInc.hasCell(northEast)){
            diagonalInc.considerCell(northEast, false);
        }
        if(!diagonalInc.hasCell(southWest))
            diagonalInc.considerCell(southWest,true);
        return diagonalInc;
    }
    private Quest findDiagonalDecQuest(){
        if(this.diagonalDec == null){
            diagonalDec = new Quest(this, DIAGONAL_DEC);
            model.addQuest(diagonalDec);
        }
        if(!diagonalDec.hasCell(northWest)){
            diagonalDec.considerCell(northWest, true);
        }
        if(!diagonalDec.hasCell(southEast))
            diagonalDec.considerCell(southEast,false);
        return diagonalDec;
    }
    private Quest findHorizontalQuest(){
        if(this.horizontal == null){
            horizontal = new Quest(this, HORIZONTAL);
            model.addQuest(horizontal);
        }
        if(!horizontal.hasCell(west)){
            horizontal.considerCell(west, true);
        }
        if(!horizontal.hasCell(east))
            horizontal.considerCell(east,false);
        return horizontal;
    }
    private Quest findVerticalQuest(){
        if(this.vertical == null){
            vertical = new Quest(this, VERTICAL);
            model.addQuest(vertical);
        }
        if(!vertical.hasCell(south)){
            vertical.considerCell(south, true);
        }
        if(!vertical.hasCell(north))
            vertical.considerCell(north,false);
        return vertical;
    }
    private void questSearch(){
        findDiagonalIncQuest();
        findDiagonalDecQuest();
        findHorizontalQuest();
        findVerticalQuest();
    }
    /**
     * Returns a string representation of this Cell's current state.
     * @return 
     */
    @Override
    public String toString(){
        String coordinates = "("+x+","+y+")  ";
        String cellString ="";
        if( this.isNull )
            return cellString + "is NULL";
        else if( !this.isAllSet() ){
            cellString = cellString + "is missing the following cell links: ";
            if(north == null)
                cellString = cellString + "North ";
            if(northEast == null)
                cellString = cellString + "NorthEast ";
            if(east == null)
                cellString = cellString + "East ";
            if(southEast == null)
                cellString = cellString + "SouthEast ";
            if(south == null)
                cellString = cellString + "South ";
            if(southWest == null)
                cellString = cellString + "SouthWest ";
            if(west == null)
                cellString = cellString + "West ";
            if(northWest == null)
                cellString = cellString + "NorthWest ";
            return cellString;
        }else if(isHuman)
            return "HUMN:"+coordinates;
        else if(isTimbot)
            return "TBOT:"+coordinates;
        else if(isOpen)
            return "OPEN:"+coordinates;
        else
            return cellString + "Divergent;";
    }
}
