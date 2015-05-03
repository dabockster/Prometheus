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
package client.views.lobby;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author Timothy Ernst
 */
public class RejectionFrame extends javax.swing.JInternalFrame {
    
    private LobbyController controller;

    /**
     * Creates new form noticeFrame
     */
    public RejectionFrame(LobbyController controller, String opName) {
        this.controller = controller;
        initComponents();
        opNameLabel.setText(opName);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        statement1Label = new javax.swing.JLabel();
        statement2Label = new javax.swing.JLabel();
        okayButton = new javax.swing.JButton();
        opNameLabel = new javax.swing.JLabel();

        setBorder(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sorry.");
        setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));

        statement1Label.setFont(new java.awt.Font("Eras Demi ITC", 1, 14)); // NOI18N
        statement1Label.setForeground(new java.awt.Color(204, 204, 204));
        statement1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statement1Label.setText("rejected your");

        statement2Label.setFont(new java.awt.Font("Eras Demi ITC", 1, 14)); // NOI18N
        statement2Label.setForeground(new java.awt.Color(204, 204, 204));
        statement2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statement2Label.setText("challenge request.");

        okayButton.setBackground(new java.awt.Color(255, 255, 255));
        okayButton.setFont(new java.awt.Font("Eras Demi ITC", 1, 14)); // NOI18N
        okayButton.setForeground(new java.awt.Color(102, 102, 102));
        okayButton.setText("Okay");
        okayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okayButtonActionPerformed(evt);
            }
        });

        opNameLabel.setFont(new java.awt.Font("Eras Bold ITC", 1, 18)); // NOI18N
        opNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        opNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opNameLabel.setText("opName");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(opNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statement2Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
            .addComponent(statement1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(okayButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opNameLabel)
                .addGap(4, 4, 4)
                .addComponent(statement1Label)
                .addGap(1, 1, 1)
                .addComponent(statement2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(okayButton)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("REJECTED");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okayButtonActionPerformed
        controller.disposeRejectionFrame(this);
    }//GEN-LAST:event_okayButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton okayButton;
    private javax.swing.JLabel opNameLabel;
    private javax.swing.JLabel statement1Label;
    private javax.swing.JLabel statement2Label;
    // End of variables declaration//GEN-END:variables
}