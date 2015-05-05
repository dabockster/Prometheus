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
package gameplay;

/**
 *
 * @author Timothy Ernst
 */
public class GameModel {
    
    private GameController controller;
    private int[][] board;
    private int[][][] tracker;
    private int rows;
    private int columns;
    private boolean gameOver;
    
    /** 
     * Default Constructor
     * @param controller 
     */
    public GameModel(GameController controller){
        this.controller = controller;
        gameOver = false;
        buildNewGrid(30,30);
    }
    
    
    /**
     * Builds a new grid
     * @param rows
     * @param columns 
     */
    public void buildNewGrid(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        board = new int[rows][columns];
    }
    
    /**
     * Plays the specified move at the desired index (x, y)
     *      for player:
     *          0 : nobody has played
     *          1 : you played
     *          -1: opponent played
     * @param x the row that was played in
     * @param y the column that was played in
     * @param player 
     */
    public void playMove(int x, int y, int player){
        if(board[x][y] != 0){
            System.out.println("Move has already been selected.");
        }else{
            System.out.println();
            if(player == 1)
                System.out.println("I played on space ("+x+", "+y+")");
            else
                System.out.println("They played on space ("+x+", "+y+")");
            board[x][y] = player;
            winConditionsMet();
        }
    }
    
    /**
     * Checks to see if the game is still going
     */
    private void winConditionsMet(){
        int state = sweep();
        if( state == 1){       //WINNER 
            controller.gameOver(1);
        }else if(state == -1){
            controller.gameOver(-1);
        }else if( state == 10){
            controller.gameOver(0);
        }
    }
    
    /**
     * Checks each cell to see if it is part of a winning sequence
     * @return 
     */
    private int sweep(){
        tracker = new int[board.length][board.length][5];
        int spaceRemaining = 10;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++ ){
                if(checkCell(i,j)){
                    System.out.println(i+" "+ j);
                    return board[i][j];
                }else{
                    spaceRemaining++;
                }
            }
        }
        return spaceRemaining;
    }
    
    
    private void checkOff(int x, int y, int sequenceChecked){
        tracker[x][y][sequenceChecked] = 1;
        if(sequenceChecked == 4){
            checkOff(x,y,0);
            checkOff(x,y,1);
            checkOff(x,y,2);
            checkOff(x,y,3);
            checkOff(x,y,4);
        }
    }
    
    private boolean wasChecked(int x, int y){
        if(tracker[x][y][4]==1)
            return true;
        for(int i=0;i<4;i++){
            if(tracker[x][y][i]==0)
                return false;
        }
        tracker[x][y][4] = 1;
    return true;
    }
    
    /**
     * checks a cell to see if it neighboring cells with the same value
     * @param x
     * @param y
     * @return 
     */
    private boolean checkCell(int x, int y){
        int player = board[x][y];
        //Still need to check values of checked cells
        if(player == 0){
            checkOff(x,y,4);
            return false;
        }
        if( x!=0 && y!=columns-1){
            if(checkDiagonalBack(x-1,y+1,player,0)){
                return true;
            }
        }
        if( y!=columns-1){
            if( checkHorizontal(x,y+1,player,0)){
                return true;
            }
        }
        if( x!=rows-1 && y!=columns-1 ){
            if( checkDiagonalForward(x+1,y+1,player,0)){
                return true;
            }
        }
        if( x!=rows-1 ){
            if ( checkVertical(x+1,y,player,0)){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkDiagonalBack(int x, int y, int player, int inSequence){
        inSequence++;
        if(inSequence == 5)
            return true;
        else if( x!=0 && y!=columns-1){
            if( board[x][y] == player ){
                System.out.println("DiagonalBack: "+x+","+y+","+player+","+inSequence);
                return checkDiagonalBack(x-1,y+1,player,inSequence);
            }else
                return false;
        }else
            return false;
    }
    
    private boolean checkHorizontal(int x, int y, int player, int inSequence){
        inSequence++;
        if(inSequence == 5)
            return true;
        else if(y!=columns-1){
            if( board[x][y] == player ){
            System.out.println("Horizontal: "+x+","+y+","+player+","+inSequence);
                return checkHorizontal(x,y+1,player,inSequence);
            }else
                return false;
        }else
            return false;
    }
    
    private boolean checkDiagonalForward(int x, int y, int player, int inSequence){
        inSequence++;
        if(inSequence == 5)
            return true;
        else if( x!=rows-1 && y!=columns-1 ){
            if( board[x][y] == player ){
                System.out.println("DiagonalForward: "+x+","+y+","+player+","+inSequence);
                return checkDiagonalForward(x+1,y+1,player,inSequence);
            }else
                return false;
        }else
            return false;
    }
    
    private boolean checkVertical(int x, int y, int player, int inSequence){
        inSequence++;
        if(inSequence == 5)
            return true;
        else if( x!=rows-1 ){
            if( board[x][y] == player ){
                System.out.println("Vertical: "+x+","+y+","+player+","+inSequence);
                return checkVertical(x+1,y,player,inSequence);
            }else
                return false;
        }else
            return false;
    }
 
    
}
