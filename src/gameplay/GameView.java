package gameplay;

import gameplay.views.BoardCell;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

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
        controller.sendPlay(currentSelected.getRow(), currentSelected.getColumn());
        currentSelected.playMove(true);
        currentSelected = null;
    }
    
    /**
     * Play Move OPPONENT
     * @param row
     * @param column
     */
    public void playMove(int row, int column){
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
        setMaximumSize(new java.awt.Dimension(880, 590));
        setMinimumSize(new java.awt.Dimension(880, 590));
        setPreferredSize(new java.awt.Dimension(880, 590));

        boardPanel.setBackground(new java.awt.Color(0, 102, 102));
        boardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51), new java.awt.Color(0, 153, 153), new java.awt.Color(51, 51, 51)));
        boardPanel.setFocusable(false);

        cellGrid = new BoardCell[30][30];

        for( int i=0; i<30; i++ ){
            for( int j=0; j<30; j++ ){
                cellGrid[i][j] = new BoardCell(this,i,j);
            }
        }

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
                .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
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
                .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(interfacePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(interfacePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    /**
     * SURRENDER BUTTON
     * Surrenders this game over to the opponent. This Client receives a loss
     * @param evt 
     */
    private void surrenderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surrenderButtonActionPerformed
        //send surrender message to opponent (a method 'receiveSurrender' will be implemented)
        //record game as loss
        //close GameController (rematch?)        
    }//GEN-LAST:event_surrenderButtonActionPerformed

    private void playMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playMoveButtonActionPerformed
        playMove();
        
        //sends a message with game play coordinates to other player
        //plays move (Game model? idk)
        //end turn
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
