package client.views.offline;

import gameplay.*;
import client.views.offline.*;
import gameplay.*;
import java.awt.Color;
import java.awt.GridLayout;

/**
 *
 * @author PLUCSCE
 */
public class OfflineGameViewLEGACY extends javax.swing.JPanel {
    private int numPlayerMoves = 0; // used for determining which HUMAN player move is registered
    private OfflineGameController controller;
    private int bSizeX;
    private int bSizeY;
/**
     * Creates new form GameView
     * @param controller
     */
    public OfflineGameViewLEGACY(OfflineGameController controller) {
        this.controller = controller;
        bSizeX = 30;
        bSizeY = 30;
        initComponents();
        this.setVisible(true); 
    }
    /**
     * Creates new form GameView, Specifies Dimensions
     * @param rows - integer size Y, number of board rows
     * @param columns - integer size X, number of board columns
     * @param controller
     */
    public OfflineGameViewLEGACY(OfflineGameController controller, int columns, int rows) {
        this.controller = controller;
        bSizeX = columns;
        bSizeY = rows;
        initComponents();
        System.out.println("Built Game View");
        this.setVisible(true); 
    }
    
    /**
     * Closes this GameView
     */
    public void close(){
        try{
            this.getParent().remove(this);
        }catch(NullPointerException ex){
            System.out.println("View was already closed");
        }
    }
    
    
    /**
     * Posts message to GameView with a blank line following
     * @param msg 
     */
    
    
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

    // ---------------------------- GRID UPDATING ------------------------------
    
    private OfflineBoardCell currentSelected; //the currently selected BoardCell
    private OfflineBoardCell[][] cellGrid;
    
    
    /**
     * Keeps track of the selected BoardCell
     * @param selected the BoardCell that has been selected
     */
    public void selectedCell(OfflineBoardCell selected){
        unselect();
        currentSelected = selected;
    }
    
    /**
     * Play Move HUMAN
     * Accommodates multiple HUMANS (x2) by using MODULUS to determine the player
     * Player1 moves first, thus Player1 is elected in the set
     *      of all sequence numbers N 
     *      such that N = {x%2 = 0, for all x in R}
     * Player2 moves second, thus Player2 is elected in the set
     *      of all sequence numbers S
     *      such that S = {x%2 = 1, for all x in R}
     */
    public void playMove(){
        if(currentSelected == null){
            return;
        }
        controller.verifyMove();
        
        
    }
    /**
     * Invoked by OfflineGameController to determine which player designation
     * to ascribe to a the selected cell
     * @param playerDesignation 
     */
    public void playerDesignatedMove(boolean playerDesignation){
        if(currentSelected == null){
            return;
        }
        if(!currentSelected.getOccupied()){
            currentSelected.playMove(playerDesignation);
            //controller.sendPlay(currentSelected.getRow(), currentSelected.getColumn()); //updates gameModel
        }
        currentSelected = null;
    }
    
    /**
     * Play Move AI
     * !!!!!!!!!!!!!!!DEPRECATE!!!!!!!!!!!!!!!!!!!!!
     * +++++Use playMoveAI+++++
     * @param row
     * @param column
     */
    public void playMove(int row, int column){
        unselect();
        cellGrid[row][column].playMove(false);
    }
    /**
     * 
     * @param coord - board coordinates: coord[0] = x-coordinate; coord[1] = y-coordinate
     * @param player - determines status of AI as Player1 or Player2
     */
    public void playMoveAI(int[] coord, int player){
        
        unselect();
        if(player == 0){cellGrid[coord[0]][coord[1]].playMove(true);}
        else{cellGrid[coord[0]][coord[1]].playMove(false);}
        
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
   
   public void toggleGameButtons(boolean buttonsOn){
       if(buttonsOn){
            playMoveButton.setEnabled(true);
            surrenderButton.setEnabled(true);
            surrenderButton.setBackground(new Color(240,70,70));
       }else{
            playMoveButton.setEnabled(false);
            surrenderButton.setEnabled(false);
            surrenderButton.setBackground(new Color(96,96,96));
            playMoveButton.setBackground(new Color(96,96,96));
       }
   }
    
    public void winDisplay(){
        toggleGameButtons(false);
        hlBorders(1);
        turnLabel.setText("You Win!");
        turnLabel.setForeground(Color.black);
        turnLabel.setBackground(new Color(0,235,128));
        
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
        toggleGameButtons(false);
        hlBorders(-1);
        turnLabel.setText("You Lose!");
        turnLabel.setForeground(Color.black);
        turnLabel.setBackground(new Color(235, 0, 107));
        
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
        toggleGameButtons(false);
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
        
        turnLabel.setText("TIE GAME!");
        turnLabel.setForeground(Color.black);
        turnLabel.setBackground(Color.yellow);
        
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
    
    public void opponentLeftDisplay(){
        for(int i=1; i<cellGrid.length; i++){       
            for(int j=13; j<cellGrid.length; j++){
                cellGrid[j][i].scrub();
                cellGrid[j][i].darken();
            }
        }
        
        cellGrid[14][5].setLetter("Y");
        cellGrid[14][6].setLetter("O");
        cellGrid[14][7].setLetter("U");
        cellGrid[14][8].setLetter("R");
        
        cellGrid[14][11].setLetter("O");
        cellGrid[14][12].setLetter("P");
        cellGrid[14][13].setLetter("P");
        cellGrid[14][14].setLetter("O");
        cellGrid[14][15].setLetter("N");
        cellGrid[14][16].setLetter("E");
        cellGrid[14][17].setLetter("N");
        cellGrid[14][18].setLetter("T");
        
        cellGrid[14][21].setLetter("L");
        cellGrid[14][22].setLetter("E");
        cellGrid[14][23].setLetter("F");
        cellGrid[14][24].setLetter("T");
        
        cellGrid[16][9].setLetter("C");
        cellGrid[16][10].setLetter("L");
        cellGrid[16][11].setLetter("O");
        cellGrid[16][12].setLetter("S");
        cellGrid[16][13].setLetter("E");
        
        cellGrid[16][16].setLetter("V");
        cellGrid[16][17].setLetter("I");
        cellGrid[16][18].setLetter("E"); 
        cellGrid[16][19].setLetter("W");
        
        for(int i=18; i<21; i++){       //change button action to playAgain = yes
            for(int j=12; j<18; j++){
                cellGrid[i][j].setEffect(4);
                cellGrid[i][j].setBlack();
            }
        }
        
        cellGrid[19][13].setLetter("O");
        cellGrid[19][14].setLetter("K");
        cellGrid[19][15].setLetter("A");
        cellGrid[19][16].setLetter("Y");
    }
    
    public void receiveSurrender(){
        hlStraightLine(2,11,27,11,2); //underline
        
        cellGrid[11][4].setLetter("O");
        cellGrid[11][5].setLetter("P");
        cellGrid[11][6].setLetter("P");
        cellGrid[11][7].setLetter("O");
        cellGrid[11][8].setLetter("N");
        cellGrid[11][9].setLetter("E");
        cellGrid[11][10].setLetter("N");
        cellGrid[11][11].setLetter("T");
        
        
        cellGrid[11][14].setLetter("S");
        cellGrid[11][15].setLetter("U");
        cellGrid[11][16].setLetter("R");
        cellGrid[11][17].setLetter("R");
        cellGrid[11][18].setLetter("E");
        cellGrid[11][19].setLetter("N");
        cellGrid[11][20].setLetter("D");
        cellGrid[11][21].setLetter("E");
        cellGrid[11][22].setLetter("R");
        cellGrid[11][23].setLetter("E");
        cellGrid[11][24].setLetter("D");
        
        
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
    

    //-------------------------------END GRID UPDATING------------------------------
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardPanel = new javax.swing.JPanel();
        interfacePanel = new javax.swing.JPanel();
        surrenderButton = new javax.swing.JButton();
        playMoveButton = new javax.swing.JButton();
        turnLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 207, 208));
        setToolTipText("");
        setMaximumSize(new java.awt.Dimension(880, 680));
        setMinimumSize(new java.awt.Dimension(880, 680));
        setPreferredSize(new java.awt.Dimension(880, 590));

        boardPanel.setBackground(new java.awt.Color(0, 102, 102));
        boardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51), new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51)));
        boardPanel.setFocusable(false);
        boardPanel.setMaximumSize(new java.awt.Dimension(570, 650));
        boardPanel.setMinimumSize(new java.awt.Dimension(570, 650));
        boardPanel.setName(""); // NOI18N

        cellGrid = new OfflineBoardCell[bSizeX][bSizeY];

        for( int i=0; i< bSizeX; i++ ){
            for( int j=0; j< bSizeY; j++ ){
                //MODIFIED 05/09/15
                //TEMPORARY SOLUTION
                //cellGrid[i][j] = new OfflineBoardCell(this,i,j);
            }
        }

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );

        boardPanel.setLayout(new GridLayout(bSizeX, bSizeY));
        for( int i=0; i< bSizeX; i++ ){
            for( int j=0; j< bSizeY; j++ ){
                boardPanel.add(cellGrid[i][j]);
            }
        }

        interfacePanel.setBackground(new java.awt.Color(62, 98, 151));
        interfacePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        interfacePanel.setFocusable(false);
        interfacePanel.setMaximumSize(new java.awt.Dimension(274, 570));
        interfacePanel.setMinimumSize(new java.awt.Dimension(274, 570));

        surrenderButton.setBackground(new java.awt.Color(240, 70, 70));
        surrenderButton.setFont(new java.awt.Font("Segoe UI Symbol", 0, 18)); // NOI18N
        surrenderButton.setForeground(new java.awt.Color(240, 240, 240));
        surrenderButton.setText("Surrender");
        surrenderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surrenderButtonActionPerformed(evt);
            }
        });

        playMoveButton.setBackground(new java.awt.Color(80, 200, 80));
        playMoveButton.setFont(new java.awt.Font("Segoe UI Symbol", 0, 18)); // NOI18N
        playMoveButton.setForeground(new java.awt.Color(250, 250, 250));
        playMoveButton.setText("Play Move");
        playMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playMoveButtonActionPerformed(evt);
            }
        });

        turnLabel.setFont(new java.awt.Font("Rockwell Condensed", 1, 36)); // NOI18N
        turnLabel.setForeground(new java.awt.Color(204, 204, 204));
        turnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnLabel.setText("turnLabel");
        turnLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));
        turnLabel.setOpaque(true);

        javax.swing.GroupLayout interfacePanelLayout = new javax.swing.GroupLayout(interfacePanel);
        interfacePanel.setLayout(interfacePanelLayout);
        interfacePanelLayout.setHorizontalGroup(
            interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interfacePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(interfacePanelLayout.createSequentialGroup()
                        .addComponent(playMoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(surrenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        interfacePanelLayout.setVerticalGroup(
            interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interfacePanelLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45)
                .addGroup(interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playMoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(surrenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(213, 213, 213))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(interfacePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(interfacePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents
                
    /**
     * SURRENDER BUTTON
     * Surrenders this game over to the opponent. This Client receives a loss
     * @param evt 
     */
    private void surrenderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surrenderButtonActionPerformed
        //Are you sure?
        controller.sendSurrender();
        //send surrender message to opponent (a method 'receiveSurrender' will be implemented)
        //record game as loss
        //close GameController (rematch?)        
    }//GEN-LAST:event_surrenderButtonActionPerformed

    private void playMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMoveButtonActionPerformed
        playMove();
    }//GEN-LAST:event_playMoveButtonActionPerformed

    /**
     * Forwards the users response to playing again to the GameController
     * @param rematch 
     */
    public void playAgain(boolean rematch){
        darkenBoard();
        controller.myChoice(rematch);      
    }
    
    /**
     * Scrubs the GameView to have a rematch
     */
    public void newGame(){
        for(int i=0; i<cellGrid.length; i++){
            for(int j=0; j<cellGrid.length; j++){
                cellGrid[i][j].scrub();
            }
        }
    }
 
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JPanel interfacePanel;
    private javax.swing.JButton playMoveButton;
    private javax.swing.JButton surrenderButton;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
    
}
