package gameplay;

import java.awt.Color;
import java.awt.GridLayout;

/**
 *
 * @author PLUCSCE
 */
public class GameView extends javax.swing.JPanel {
    
    private GameController controller;

    /**
     * Creates new form GameView
     * @param controller
     */
    public GameView(GameController controller) {
        this.controller = controller;
        initComponents();
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
     * Appends username to front of message
     * Posts message to GameView
     * Sends message to opponent
     * @param msg 
     */
    private void sendMsg(String msg){
        controller.sendMsg(msg);        
    }
    
    /**
     * Posts message to GameView with a blank line following
     * @param msg 
     */
    public void postMsg(String msg){
        msgBoardTA.append(msg+"\n\n");
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

    // ---------------------------- GRID UPDATING ------------------------------
    
    private BoardCell currentSelected; //the currently selected BoardCell
    private BoardCell[][] cellGrid;
    
    
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
        controller.sendPlay(currentSelected.getRow(), currentSelected.getColumn());
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
        msgBoardPanel = new javax.swing.JScrollPane();
        msgBoardTA = new javax.swing.JTextArea();
        msgTF = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
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

        cellGrid = new BoardCell[30][30];

        for( int i=0; i<30; i++ ){
            for( int j=0; j<30; j++ ){
                cellGrid[j][i] = new BoardCell(this,j,i);
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

        boardPanel.setLayout(new GridLayout(30,30));
        for( int i=0; i<30; i++ ){
            for( int j=0; j<30; j++ ){
                boardPanel.add(cellGrid[i][j]);
            }
        }

        interfacePanel.setBackground(new java.awt.Color(62, 98, 151));
        interfacePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        interfacePanel.setFocusable(false);
        interfacePanel.setMaximumSize(new java.awt.Dimension(274, 570));
        interfacePanel.setMinimumSize(new java.awt.Dimension(274, 570));

        msgBoardTA.setEditable(false);
        msgBoardTA.setColumns(20);
        msgBoardTA.setRows(5);
        msgBoardPanel.setViewportView(msgBoardTA);

        msgTF.setText("Write smack-talk here");
        msgTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                msgTFMouseClicked(evt);
            }
        });
        msgTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgTFActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
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
                    .addGroup(interfacePanelLayout.createSequentialGroup()
                        .addComponent(msgTF)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton))
                    .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(msgBoardPanel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        interfacePanelLayout.setVerticalGroup(
            interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interfacePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(msgBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msgTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(interfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playMoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(surrenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
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
                .addContainerGap())
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

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String msg = msgTF.getText();
        msgTF.setText(null);
        sendMsg(msg);
    }//GEN-LAST:event_sendButtonActionPerformed

    private void msgTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgTFActionPerformed
        String msg = msgTF.getText();
        msgTF.setText(null);
        sendMsg(msg);
    }//GEN-LAST:event_msgTFActionPerformed

    private void msgTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_msgTFMouseClicked
        msgTF.selectAll();
    }//GEN-LAST:event_msgTFMouseClicked

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
    private javax.swing.JScrollPane msgBoardPanel;
    private javax.swing.JTextArea msgBoardTA;
    private javax.swing.JTextField msgTF;
    private javax.swing.JButton playMoveButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton surrenderButton;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}
