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
    
    private int presentState;
    
    private Cell north;  //north
    private Cell northEast; //northeast
    private Cell east;  //east
    private Cell southEast; //southeast
    private Cell south;  //south
    private Cell southWest; //southwest
    private Cell west;  //west
    private Cell northWest; //nortwest
   
    private final int NULL = -1;
    private final int OPEN = 0;
    private final int HUMAN = 1;
    private final int TIMBOT = 2;    
    

    /**
     * Default Constructor
     * @param xCoordinate
     * @param yCoordinate 
     */
    public Cell(int xCoordinate, int yCoordinate){
        this.x = xCoordinate;
        this.y = yCoordinate;
    }
    /**
     * Creates a copy of an original cell
     * @param original The cell to be copied
     */
    public Cell( Cell original ){
        this.x = original.x;
        this.y = original.y;
        this.isNull = original.isNull;
        this.presentState = original.getState();
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
    }
    /**
     * Sets this cell to a Timbot cell
     */
    public void setTimbot(){
        setState(TIMBOT);
    }
    /**
     * Sets the boolean value of this Cell's current state as well as
     * the int value of presentState to a specific value
     * @param state
     */
    private void setState(int state){
        if(this.isNull)
            return;
        
        this.presentState = state;
        
        if(presentState == NULL){
            this.isNull = true;
            
        }else if(presentState == OPEN){
            this.isOpen = true;
            
        }else if(presentState == TIMBOT){
            this.isOpen = false;
            this.isTimbot = true;
            
        }else if(presentState == HUMAN){
            this.isOpen = false;
            this.isHuman = true;
        }
    }
    /**
     * Returns this Cell's present state
     * @return 
     */
    public int getState(){
        return this.presentState;
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
        return this.presentState == otherState;
    }  
    /**
     * This holds true for the following four methods
     * @return 0 : no sequence
     *         1 : is first of sequence
     *         2 : is middle of sequence
     *         3 : is end of sequence
     */
    private int diagIncreasing(int state){
        if(northEast.hasState(state)){
            if(southWest.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(southWest.hasState(state)){
            return 3;
        }else
            return 0;
    }
    private int diagDecreasing(int state){
        if(northWest.hasState(state)){
            if(southEast.hasState(state)){
                return 2;
            }else{
                return 3;
            }
        }else if(southEast.hasState(state)){
            return 1;
        }else
            return 0;
    }
    private int horizontal(int state){
        if(east.hasState(state)){
            if(west.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(west.hasState(state)){
            return 3;
        }else{
            return 0;
        }
    }
    private int vertical(int state){
        if(north.hasState(state)){
            if(south.hasState(state)){
                return 2;
            }else{
                return 1;
            }
        }else if(south.hasState(state)){
            return 3;
        }else{
            return 0;
        }
    }
    /**
     * Returns a string representation of this Cell's current state.
     * @return 
     */
    @Override
    public String toString(){
        String cellString = "Cell "+"("+x+", "+y+") : ";
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
        }else if(isOpen)
            return cellString + "is linked and open";
        else if(isHuman)
            return cellString + "is HUMAN";
        else if(isTimbot)
            return cellString + "is TIMBOT";
        else
            return cellString + "is divergent";
    }
}
