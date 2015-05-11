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

import java.util.LinkedList;

/**
 *
 * @author Timothy Ernst
 */
public class TimbotModel {
    
    private final TimbotController controller;
    private final int size;
    private int[][] board;
    private Cell[][] cells;
    
    //ADD A LINKED LIST OF PLAYS TO KEEP TRACK OF THE MOVES
    private final int moveNumber;
    private Move humanMoves;
    private Move timbotMoves;
    
    private final int HUMAN = 1;
    private final int TIMBOT = 2;
    
    /**
     * Default Constructor
     * @param controller
     */
    public TimbotModel(TimbotController controller, int size){
        this.size = size;
        this.controller = controller;
        scrubBoard();
        moveNumber = 1;
        humanMoves = null;
        timbotMoves = null;
    }
    
    /**
     * Sets all values in the currentBoard equal to zero
     */
    public void scrubBoard(){
        board = new int[size][size];
        cells = new Cell[size+2][size+2];
        //initialize the null cells
        for(int i=0; i<size+2; i++){
            cells[i][0] = new Cell(i-1,-1);
            cells[i][0].setNull();
            cells[i][size+1] = new Cell(i-1,size);
            cells[i][size+1].setNull();
            cells[0][i] = new Cell(-1,i-1);
            cells[0][i].setNull();
            cells[size+1][i] = new Cell(size,i-1);
            cells[size+1][i].setNull();
            
        }
        for(int i=1; i<=board.length; i++){
            for(int j=1;j<=board.length; j++){
                Cell newCell = new Cell(j-1,i-1);
                cells[j][i] = newCell;
                newCell.setNorthEast(cells[j+1][i-1]);
                newCell.setNorth(cells[j][i-1]);
                newCell.setWest(cells[j-1][i]);
                newCell.setNorthWest(cells[j-1][i-1]);
            }
        }
        for(int j=0; j<cells.length; j++){
            for(int i=0; i<cells.length; i++){
                Cell currentCell = cells[i][j];
                if( !currentCell.isAllSet() ){
                    currentCell.setSouth(cells[i][j+1]);
                    currentCell.setSouthWest(cells[i-1][j+1]);
                    currentCell.setSouthEast(cells[i+1][j+1]);
                    currentCell.setEast(cells[i+1][j]);
                }
                System.out.println(currentCell.toString());
            }
        }        
    }
    /**
     * Returns the cell at the specified index
     * @param x
     * @param y
     * @return the cell
     */
    private Cell getCell(int x, int y){
        return cells[x+1][y+1];
    }
    
    /**
     * Plays a human move into the board
     * @param x
     * @param y 
     */
    public void humanMove(int x, int y){
        board[x][y] = -1;
        Cell cell = getCell(x,y);
        cell.setHuman();
        humanMoves = new Move(HUMAN, cell, humanMoves);        
    }
    
    /**
     * Plays a bot move onto the board
     * @param x
     * @param y 
     */
    public void timbotMove(int x, int y){
        board[x][y] = 1;
        Cell cell = getCell(x,y);
        cell.setTimbot();
        timbotMoves = new Move(TIMBOT, cell, timbotMoves);
    }
}
