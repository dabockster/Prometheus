/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.manager;
import client.manager.ClientController;
import server.manager.ServerController;

/**
 *
 * @author Timothy Ernst
 */
public class ServerView extends javax.swing.JFrame {
    
    private final ServerController controller;
  
    /**
     * CONSTRUCTOR
     * Creates new form ServerView
     * @param serverControl the controller for this view
     */
    public ServerView(ServerController serverControl) {
        this.controller = serverControl;
        initComponents();
    }
    
    public void setIP(String ip){
        addressTF.setText(ip);
    }
    
    /**
     * RELAY MESSAGE
     * Relays a message from server by appending serverFeedbackTA
     * @param feedback the message to be relayed
     */
    public void serverFeedback(String feedback){
        serverFeebackTA.append(feedback + "\n\n");
    }
    
    /**
     * RELAY MESSAGE
     * Relays a message from server by appending serverFeedbackTA
     * @param feedback the message to be relayed
     */
    public void clientFeedback(String feedback){
        clientFeedbackTA.append(feedback + "\n");
    }
    
    /**
     * UPDATE REGISTERED PROFILES
     * Relays a message from server by appending registeredAccountsTA
     * @param username the message to be relayed
     */
    public void addRegisteredProfile(String username){
        registeredAccountsTA.append(username + "\n");
    }
    
    /**
     * CLEAR REGISTERED PROFILES
     */
    public void clearRegisteredProfiles(){
        registeredAccountsTA.setText("");
    }
    /**
     * UPDATE ONLINE USERS
     * Relays a message from server by appending onlineAccountsTA
     * @param username the message to be relayed
     */
    public void addOnlineProfile(String username){
        onlineAccountsTA.append(username + "\n");
    }
    
    /**
     * CLEAR ONLINE USERS
     */
    public void clearOnlineProfiles(){
        onlineAccountsTA.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverMessageTA = new javax.swing.JScrollPane();
        serverFeebackTA = new javax.swing.JTextArea();
        serverFeedbackLabel = new javax.swing.JLabel();
        serverInfoLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        portTF = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        clientInfoLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientFeedbackTA = new javax.swing.JTextArea();
        newClientButton = new javax.swing.JButton();
        serverTitleLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        clientFeedbackLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        registeredAccountsTA = new javax.swing.JTextArea();
        registeredAccountsLabel = new javax.swing.JLabel();
        onlineAccountsLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        onlineAccountsTA = new javax.swing.JTextArea();
        shutDownButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        serverFeebackTA.setEditable(false);
        serverFeebackTA.setColumns(20);
        serverFeebackTA.setFont(new java.awt.Font("Lucida Console", 0, 10)); // NOI18N
        serverFeebackTA.setLineWrap(true);
        serverFeebackTA.setRows(5);
        serverFeebackTA.setWrapStyleWord(true);
        serverMessageTA.setViewportView(serverFeebackTA);

        serverFeedbackLabel.setFont(new java.awt.Font("FangSong", 0, 18)); // NOI18N
        serverFeedbackLabel.setText("ServerConnection Feedback:");

        serverInfoLabel.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        serverInfoLabel.setText("Server Information");

        portLabel.setFont(new java.awt.Font("FangSong", 0, 18)); // NOI18N
        portLabel.setText("Port Number:");

        addressTF.setEditable(false);

        portTF.setText("8080");
        portTF.setToolTipText("");

        jButton1.setText("Setup Server");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildServerButton(evt);
            }
        });

        clientInfoLabel.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 24)); // NOI18N
        clientInfoLabel.setText("UserConnection Information");

        clientFeedbackTA.setEditable(false);
        clientFeedbackTA.setColumns(20);
        clientFeedbackTA.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        clientFeedbackTA.setLineWrap(true);
        clientFeedbackTA.setRows(5);
        clientFeedbackTA.setWrapStyleWord(true);
        jScrollPane1.setViewportView(clientFeedbackTA);

        newClientButton.setText("New Client View");
        newClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newClientButtonActionPerformed(evt);
            }
        });

        serverTitleLabel.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 40)); // NOI18N
        serverTitleLabel.setText("S e r v e r  M a i n  F r a m e");

        addressLabel.setFont(new java.awt.Font("FangSong", 0, 18)); // NOI18N
        addressLabel.setText("IP Address:");

        clientFeedbackLabel.setFont(new java.awt.Font("FangSong", 0, 18)); // NOI18N
        clientFeedbackLabel.setText("UserConnection Feedback:");

        registeredAccountsTA.setEditable(false);
        registeredAccountsTA.setColumns(17);
        registeredAccountsTA.setLineWrap(true);
        registeredAccountsTA.setRows(5);
        registeredAccountsTA.setWrapStyleWord(true);
        jScrollPane2.setViewportView(registeredAccountsTA);

        registeredAccountsLabel.setFont(new java.awt.Font("FangSong", 0, 14)); // NOI18N
        registeredAccountsLabel.setText("Registered Accounts:");

        onlineAccountsLabel.setFont(new java.awt.Font("FangSong", 0, 14)); // NOI18N
        onlineAccountsLabel.setText("Online Accounts:");

        onlineAccountsTA.setEditable(false);
        onlineAccountsTA.setColumns(17);
        onlineAccountsTA.setLineWrap(true);
        onlineAccountsTA.setRows(5);
        onlineAccountsTA.setWrapStyleWord(true);
        jScrollPane4.setViewportView(onlineAccountsTA);

        shutDownButton.setText("Shut Down Server");
        shutDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shutDownButtonbuildServerButton(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addressTF, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(portLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(portTF))
                                .addComponent(newClientButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(serverMessageTA, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(serverInfoLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(serverFeedbackLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(shutDownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(clientInfoLabel))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientFeedbackLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registeredAccountsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(onlineAccountsLabel)
                        .addGap(13, 13, 13)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(serverTitleLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(serverTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverInfoLabel)
                    .addComponent(clientInfoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel)
                    .addComponent(clientFeedbackLabel))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(portLabel)
                            .addComponent(portTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shutDownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newClientButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registeredAccountsLabel)
                    .addComponent(onlineAccountsLabel)
                    .addComponent(serverFeedbackLabel))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(serverMessageTA, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buildServerButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildServerButton
        if(controller.serverRunning()){
            serverFeedback("Server is already running.");
            return;           
        }
        if(portTF.getText().isEmpty()) {
            serverFeedback("Please enter a port number.");            
            return;
        }
        int portNum = Integer.parseInt(portTF.getText());
        controller.setupConnection(portNum);
        portTF.setEditable(false);
    }//GEN-LAST:event_buildServerButton

    /**
     * Creates a new Client view from which one can log on to the server
     * @param evt 
     */
    private void newClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newClientButtonActionPerformed
        ClientController newControl = new ClientController();
    }//GEN-LAST:event_newClientButtonActionPerformed

    /**
     * Terminates the server
     * @param evt 
     */
    private void shutDownButtonbuildServerButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shutDownButtonbuildServerButton
        if(!controller.serverRunning()){
            serverFeedback("Server was not running.");
            return;           
        }
        portTF.setText(null);
        portTF.setEditable(true);
        controller.teardownModel();
        clearRegisteredProfiles();
        controller.teardownConnection();
    }//GEN-LAST:event_shutDownButtonbuildServerButton


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTF;
    private javax.swing.JLabel clientFeedbackLabel;
    private javax.swing.JTextArea clientFeedbackTA;
    private javax.swing.JLabel clientInfoLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton newClientButton;
    private javax.swing.JLabel onlineAccountsLabel;
    private javax.swing.JTextArea onlineAccountsTA;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTF;
    private javax.swing.JLabel registeredAccountsLabel;
    private javax.swing.JTextArea registeredAccountsTA;
    private javax.swing.JTextArea serverFeebackTA;
    private javax.swing.JLabel serverFeedbackLabel;
    private javax.swing.JLabel serverInfoLabel;
    private javax.swing.JScrollPane serverMessageTA;
    private javax.swing.JLabel serverTitleLabel;
    private javax.swing.JButton shutDownButton;
    // End of variables declaration//GEN-END:variables


}
