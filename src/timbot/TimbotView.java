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

import gameplay.BoardCell;
import java.awt.Color;
import java.awt.GridLayout;

/**
 *
 * @author Timothy Ernst
 */
public class TimbotView extends javax.swing.JFrame {
    
    private TimbotController controller;
    private BoardCell currentSelected; //the currently selected BoardCell
    private BoardCell[][] cellGrid;
    
    /**
     * Creates new form timbotView
     * @param controller
     */
    public TimbotView(TimbotController controller) {
        this.controller = controller;
        initComponents();
    }
    
    public void close(){
        controller.toLogin();    
    }
    
    /**
     * Keeps track of the selected BoardCell
     * @param selected the BoardCell that has been selected
     */
    public void selectedCell(BoardCell selected){
        unselect();
        currentSelected = selected;
    }
    
    /**
     * Play Move YOU
     */
    public void playMove(){
        if(currentSelected == null){
            return;
        }
        currentSelected.playMove(true);
        controller.userPlay(currentSelected.getRow(), currentSelected.getColumn());
        currentSelected = null;
    }
    
    /**
     * Play Move OPPONENT
     * @param row
     * @param column
     */
    public void playMove(int row, int column){
        unselect();
        cellGrid[row][column].playMove(false);
    }
    
    /**
     *  unselects the currentSelected cell
     */
    private void unselect(){
        if(currentSelected != null)
            currentSelected.unselect();
        currentSelected = null;
    }
    
   private void darkenBoard(){
        for(int i=0; i < cellGrid.length; i++ ){
            for (int j=0; j< cellGrid.length; j++ ){
                cellGrid[i][j].darken();
            }
        }
    }
   
   /**
     * Ends your turn
     * @param turn 
     */
    public void turnover(boolean turn){
        if(turn){
            turnLabel.setText("Your Turn");
            turnLabel.setForeground(Color.black);
            turnLabel.setBackground(new Color(0,235,128));
            playMoveButton.setEnabled(true);
            playMoveButton.setBackground(new Color(80,200,80));
        }else{
            turnLabel.setText("Not Your Turn");
            turnLabel.setForeground(Color.white);
            turnLabel.setBackground(new Color(235, 0, 107));
            playMoveButton.setEnabled(false);
            playMoveButton.setBackground(new Color(96,96,96));
        }
    }

    
    public void winDisplay(){
        hlBorders(1);
        
        hlStraightLine(2,2,2,7,1);   //draw W
        hlStraightLine(3,7,3,8,1);
        hlStraightLine(4,8,4,9,1);
        hlStraightLine(5,7,5,8,1);
        hlStraightLine(6,6,6,7,1);
        hlStraightLine(7,7,7,8,1);
        hlStraightLine(8,8,8,9,1);
        hlStraightLine(9,7,9,8,1);
        hlStraightLine(10,2,10,7,1);
        
        hlStraightLine(13,3,17,3,1); //draw I
        hlStraightLine(15,4,15,8,1);
        hlStraightLine(13,9,17,9,1);
        
        hlStraightLine(20,3,20,9,1);
        hlCell(21,4,1);
        hlCell(22,5,1);
        hlCell(23,6,1);
        hlCell(24,7,1);
        hlCell(25,8,1);
        hlStraightLine(26,3,26,9,1);
        
        hlStraightLine(2,11,26,11,1);
        
    }
    
    public void loseDisplay(){
        hlBorders(-1);
        hlStraightLine(2,11,27,11,-1); //underline
        
        hlStraightLine(2,2,2,9,-1); //draw L
        hlStraightLine(3,9,6,9,-1);
        
        hlStraightLine(8,4,8,8,-1); //draw O
        hlStraightLine(11,4,11,8,-1);
        hlStraightLine(9,3,10,3,-1);
        hlStraightLine(9,9,10,9,-1);
        
        hlStraightLine(13,4,13,5,-1); //draw s
        hlStraightLine(14,3,15,3,-1);
        hlCell(16,4,-1);
        hlStraightLine(14,6,15,6,-1);
        hlStraightLine(16,7,16,8,-1);
        hlStraightLine(14,9,15,9,-1);
        hlCell(13,8,-1);
        
        hlStraightLine(18,3,18,9,-1);   //draw E
        hlStraightLine(19,3,21,3,-1);
        hlStraightLine(19,6,20,6,-1);
        hlStraightLine(19,9,21,9,-1);

        hlStraightLine(23,3,23,9,-1);   //draw r
        hlCell(24,4,-1);
        hlStraightLine(25,3,26,3,-1);
        hlCell(27,4,-1);
    }
    
    public void tieDisplay(){
        hlBorders(0);
        
        hlStraightLine(2,11,24,11,0);   //draw underline
        
        hlStraightLine(2,2,8,2,0);  //draw T
        hlStraightLine(5,3,5,9,0);
        
        hlStraightLine(11,3,15,3,0); //draw I
        hlStraightLine(13,4,13,8,0);
        hlStraightLine(11,9,15,9,0);
        
        hlStraightLine(19,3,19,9,0);    //draw E
        hlStraightLine(20,3,23,3,0);
        hlStraightLine(20,6,22,6,0);
        hlStraightLine(20,9,23,9,0);

    }
    
    public void playAgainDisplay(){
        hlStraightLine(3,13,3,17,2); //draw p
        hlStraightLine(4,13,5,13,2);
        hlStraightLine(4,15,5,15,2);
        hlCell(5,14,2);
        
        hlStraightLine(7,13,7,17,2); //draw l
        
        hlStraightLine(9,14,9,17,2); //draw A
        hlStraightLine(11,14,11,17,2);
        hlCell(10,13,2);
        hlCell(10,15,2);
        
        hlStraightLine(14,15,14,17,2);  //draw Y
        hlStraightLine(13,13,13,14,2);
        hlStraightLine(15,13,15,14,2);
        
        hlStraightLine(3,20,3,23,2);    //draw A
        hlStraightLine(5,20,5,23,2);
        hlCell(4,19,2);
        hlCell(4,21,2);
        
        hlStraightLine(7,19,7,21,2);     //draw g
        hlCell(8,19,2);
        hlCell(8,21,2);
        hlStraightLine(9,19,9,23,2);
        hlStraightLine(7,23,8,23,2);
        
        hlStraightLine(11,20,11,23,2);  //draw A
        hlStraightLine(13,20,13,23,2);
        hlCell(12,19,2);
        hlCell(12,21,2);
        
        hlStraightLine(15,19,17,19,2);  //draw I
        hlStraightLine(15,23,17,23,2);
        hlStraightLine(16,20,16,22,2);
        
        hlStraightLine(19,19,19,23,2);  //draw N
        hlCell(20,20,2);
        hlCell(21,19,2);
        hlStraightLine(22,20,22,23,2);
        
        hlCell(24,19,2);    //draw ?
        hlCell(25,18,2);
        hlStraightLine(26,19,26,20,2);
        hlCell(25,21,2);
        hlCell(25,23,2);
        
        for(int i=25; i<28; i++){       //change button action to playAgain = yes
            for(int j=7; j<12; j++){
                cellGrid[i][j].setEffect(2);
                cellGrid[i][j].setGreen();
            }
        }
        
        cellGrid[26][8].setLetter("Y");
        cellGrid[26][9].setLetter("E");
        cellGrid[26][10].setLetter("S");
        
        for(int i=25; i<28; i++){       //change button action to playAgain = no
            for(int j=19; j<23; j++){
                cellGrid[i][j].setEffect(3);
                cellGrid[i][j].setRed();
            }
        }
        
        cellGrid[26][20].setLetter("N");
        cellGrid[26][21].setLetter("O");       
        
    }
    
    
    /**
     * highlights the outer columns and rows of the cellGrid in 
     * the specified color
     * @param color the color to be used
     */
    private void hlBorders(int color){
        darkenBoard();
        hlStraightLine(0,0,0,cellGrid.length-1,color);
        hlStraightLine(cellGrid.length-1,0,cellGrid.length-1,cellGrid.length-1,color);
        hlStraightLine(0,0,cellGrid.length-1,0,color);
        hlStraightLine(0,cellGrid.length-1,cellGrid.length-1,cellGrid.length-1,color);
    }
    
    /**
     * Highlights a line of buttons from (xStart, yStart) to (xEnd, yEnd) in
     * a specific color.
     *      color -1: red
     *      color  0: yellow
     *      color  1: green
     * @param xStart begin x coord
     * @param yStart begin y coord
     * @param xEnd end x coord
     * @param yEnd end y coord
     * @param color specified in method highlightCell()
     */
    private void hlStraightLine(int xStart,int yStart,int xEnd,int yEnd, int color){
        for( int i=xStart; i<=xEnd; i++ ){
            for( int j=yStart; j<=yEnd; j++ ){
                hlCell(i,j,color);
            }
        }
    }
    /**
     * highlights a specific cell
     *  @param color 
     *      color -1: red
     *      color  0: yellow
     *      color  1: green
     *      color  2: white
     */
    private void hlCell(int x, int y, int color){
        if(color == -1)
            cellGrid[y][x].setRed();
        else if(color == 0)
            cellGrid[y][x].setYellow();
        else if(color == 1)
            cellGrid[y][x].setGreen();
        else if(color == 2)
            cellGrid[y][x].setWhite();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        baseLayeredPanel = new javax.swing.JLayeredPane();
        timbotBoardPanel = new javax.swing.JPanel();
        turnLabel = new javax.swing.JLabel();
        playMoveButton = new javax.swing.JButton();
        surrenderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 760));
        setMinimumSize(new java.awt.Dimension(900, 760));
        setPreferredSize(new java.awt.Dimension(900, 760));
        setResizable(false);

        baseLayeredPanel.setMaximumSize(new java.awt.Dimension(900, 750));
        baseLayeredPanel.setMinimumSize(new java.awt.Dimension(900, 750));
        baseLayeredPanel.setName(""); // NOI18N
        baseLayeredPanel.setPreferredSize(new java.awt.Dimension(900, 750));

        timbotBoardPanel.setBackground(new java.awt.Color(0, 102, 102));
        timbotBoardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51), new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51)));
        timbotBoardPanel.setFocusable(false);
        timbotBoardPanel.setMaximumSize(new java.awt.Dimension(570, 650));
        timbotBoardPanel.setMinimumSize(new java.awt.Dimension(570, 650));
        timbotBoardPanel.setName(""); // NOI18N

        cellGrid = new BoardCell[30][30];

        for( int i=0; i<30; i++ ){
            for( int j=0; j<30; j++ ){
                cellGrid[j][i] = new BoardCell(true,this,j,i);
            }
        }

        javax.swing.GroupLayout timbotBoardPanelLayout = new javax.swing.GroupLayout(timbotBoardPanel);
        timbotBoardPanel.setLayout(timbotBoardPanelLayout);
        timbotBoardPanelLayout.setHorizontalGroup(
            timbotBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        timbotBoardPanelLayout.setVerticalGroup(
            timbotBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        timbotBoardPanel.setLayout(new GridLayout(30,30));
        for( int i=0; i<30; i++ ){
            for( int j=0; j<30; j++ ){
                timbotBoardPanel.add(cellGrid[i][j]);
            }
        }

        turnLabel.setFont(new java.awt.Font("Rockwell Condensed", 1, 36)); // NOI18N
        turnLabel.setForeground(new java.awt.Color(204, 204, 204));
        turnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnLabel.setText("turnLabel");
        turnLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));
        turnLabel.setOpaque(true);

        playMoveButton.setBackground(new java.awt.Color(80, 200, 80));
        playMoveButton.setFont(new java.awt.Font("Segoe UI Symbol", 0, 18)); // NOI18N
        playMoveButton.setForeground(new java.awt.Color(250, 250, 250));
        playMoveButton.setText("Play Move");
        playMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playMoveButtonActionPerformed(evt);
            }
        });

        surrenderButton.setBackground(new java.awt.Color(240, 70, 70));
        surrenderButton.setFont(new java.awt.Font("Segoe UI Symbol", 0, 18)); // NOI18N
        surrenderButton.setForeground(new java.awt.Color(240, 240, 240));
        surrenderButton.setText("Surrender");
        surrenderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surrenderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout baseLayeredPanelLayout = new javax.swing.GroupLayout(baseLayeredPanel);
        baseLayeredPanel.setLayout(baseLayeredPanelLayout);
        baseLayeredPanelLayout.setHorizontalGroup(
            baseLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseLayeredPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(timbotBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(baseLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(surrenderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playMoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        baseLayeredPanelLayout.setVerticalGroup(
            baseLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(baseLayeredPanelLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addGroup(baseLayeredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(baseLayeredPanelLayout.createSequentialGroup()
                        .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(playMoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(surrenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(timbotBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        baseLayeredPanel.setLayer(timbotBoardPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        baseLayeredPanel.setLayer(turnLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        baseLayeredPanel.setLayer(playMoveButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        baseLayeredPanel.setLayer(surrenderButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(baseLayeredPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(baseLayeredPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMoveButtonActionPerformed
        playMove();
    }//GEN-LAST:event_playMoveButtonActionPerformed

    private void surrenderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surrenderButtonActionPerformed
        //Are you sure?
        controller.surrender();
        //send surrender message to opponent (a method 'receiveSurrender' will be implemented)
        //record game as loss
        //close GameController (rematch?)
    }//GEN-LAST:event_surrenderButtonActionPerformed

    /**
     * Scrubs the GameView to have a rematch
     */
    public void playAgain(boolean rematch){
        if(rematch){    
            controller.newGame();
            for(int i=0; i<cellGrid.length; i++){
                for(int j=0; j<cellGrid.length; j++){
                    cellGrid[i][j].scrub();
                }
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane baseLayeredPanel;
    private javax.swing.JButton playMoveButton;
    private javax.swing.JButton surrenderButton;
    private javax.swing.JPanel timbotBoardPanel;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}
