/*
 * The MIT License
 *
 * Copyright 2015 dabockster.
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
package client.views.mainMenu;

import client.views.mainMenu.MainMenuController;
import client.views.dialogues.LoginBlankDialog;
import client.views.dialogues.ServerPromptDialog;

/**
 * MainMenuView class
 * @author dabockster
 */
public class MainMenuView extends javax.swing.JFrame {

    private MainMenuController controller;
    private LoginBlankDialog blank;
    
    /**
     * Creates new form MainMenuView
     * @param ctrl MainMenuController inheritance
     */
    public MainMenuView(MainMenuController ctrl) {
        controller = ctrl;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLayeredPane1 = new javax.swing.JLayeredPane();
        objectsLayer = new javax.swing.JPanel();
        fieldsPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameTF = new javax.swing.JTextField();
        passswordLabel = new javax.swing.JLabel();
        passwordTF = new javax.swing.JPasswordField();
        serverResponseLabel = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        registerButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        modesPanel = new javax.swing.JPanel();
        anonButton = new javax.swing.JButton();
        offlineButton = new javax.swing.JButton();
        quitPanel = new javax.swing.JPanel();
        quitButton = new javax.swing.JButton();
        backgroundLayer = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        setServerMenu = new javax.swing.JMenu();
        serverIPandPortPrompt = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prometheus & Bob");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        objectsLayer.setOpaque(false);

        fieldsPanel.setOpaque(false);

        usernameLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel.setText("Username");

        usernameTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        passswordLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        passswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        passswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passswordLabel.setText("Password");

        passwordTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        serverResponseLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        serverResponseLabel.setForeground(new java.awt.Color(255, 255, 255));
        serverResponseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serverResponseLabel.setText(" ");

        javax.swing.GroupLayout fieldsPanelLayout = new javax.swing.GroupLayout(fieldsPanel);
        fieldsPanel.setLayout(fieldsPanelLayout);
        fieldsPanelLayout.setHorizontalGroup(
            fieldsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fieldsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fieldsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordTF)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTF, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passswordLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(serverResponseLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fieldsPanelLayout.setVerticalGroup(
            fieldsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fieldsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(serverResponseLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        loginPanel.setOpaque(false);

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loginButton)
                .addContainerGap())
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerButton)
                    .addComponent(loginButton)))
        );

        modesPanel.setOpaque(false);

        anonButton.setText("Anonymous");
        anonButton.setToolTipText("");
        anonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anonButtonActionPerformed(evt);
            }
        });

        offlineButton.setText("Play Offline");
        offlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offlineButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout modesPanelLayout = new javax.swing.GroupLayout(modesPanel);
        modesPanel.setLayout(modesPanelLayout);
        modesPanelLayout.setHorizontalGroup(
            modesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modesPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(modesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(anonButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(offlineButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        modesPanelLayout.setVerticalGroup(
            modesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(anonButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offlineButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        quitPanel.setOpaque(false);

        quitButton.setText("Quit Game");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout quitPanelLayout = new javax.swing.GroupLayout(quitPanel);
        quitPanel.setLayout(quitPanelLayout);
        quitPanelLayout.setHorizontalGroup(
            quitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quitPanelLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(quitButton)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        quitPanelLayout.setVerticalGroup(
            quitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, quitPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quitButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout objectsLayerLayout = new javax.swing.GroupLayout(objectsLayer);
        objectsLayer.setLayout(objectsLayerLayout);
        objectsLayerLayout.setHorizontalGroup(
            objectsLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, objectsLayerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(objectsLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        objectsLayerLayout.setVerticalGroup(
            objectsLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(objectsLayerLayout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(fieldsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(quitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(objectsLayer, gridBagConstraints);

        backgroundLayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/views/mainMenu/bg.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(backgroundLayer, gridBagConstraints);

        setServerMenu.setText("Set Server");
        setServerMenu.setToolTipText("");

        serverIPandPortPrompt.setText("Enter Server Address");
        serverIPandPortPrompt.setToolTipText("");
        serverIPandPortPrompt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverIPandPortPromptActionPerformed(evt);
            }
        });
        setServerMenu.add(serverIPandPortPrompt);

        jMenuBar1.add(setServerMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anonButtonActionPerformed
        String username = "anonymous";
        
        controller.login(username, "anon");
    }//GEN-LAST:event_anonButtonActionPerformed

    private void offlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offlineButtonActionPerformed
        controller.openView("Offline");
        controller.dispose();
    }//GEN-LAST:event_offlineButtonActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        controller.quitProgram(0);
    }//GEN-LAST:event_quitButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String username = usernameTF.getText();
        String password = new String(passwordTF.getPassword());
        
        if (username.isEmpty() | password.isEmpty()){
            loginBlankError();
            return;
        }
        
        controller.login(username, password);
    }//GEN-LAST:event_loginButtonActionPerformed
//REGISTER BUTTON
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
       //on Register
       String username = usernameTF.getText();
       String password = new String(passwordTF.getPassword());
       controller.register(username, password);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void serverIPandPortPromptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverIPandPortPromptActionPerformed
           ServerPromptDialog serverPrompt = new ServerPromptDialog(this, this.controller);
    }//GEN-LAST:event_serverIPandPortPromptActionPerformed

    public void postServerFeedback(String response){
        serverResponseLabel.setText(response);
    }
   
    private void loginBlankError(){
        blank = new LoginBlankDialog(this);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anonButton;
    private javax.swing.JLabel backgroundLayer;
    private javax.swing.JPanel fieldsPanel;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel modesPanel;
    private javax.swing.JPanel objectsLayer;
    private javax.swing.JButton offlineButton;
    private javax.swing.JLabel passswordLabel;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JButton quitButton;
    private javax.swing.JPanel quitPanel;
    private javax.swing.JButton registerButton;
    private javax.swing.JMenuItem serverIPandPortPrompt;
    private javax.swing.JLabel serverResponseLabel;
    private javax.swing.JMenu setServerMenu;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTF;
    // End of variables declaration//GEN-END:variables

    /**
     * CLEAR TEXT FIELDS
     */
    public void clearFields() {
        this.usernameTF.setText(null);
        this.passwordTF.setText(null);
    }
}
