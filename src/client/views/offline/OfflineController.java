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
package client.views.offline;

import client.manager.ClientController;

/**
 * OfflineController class
 * @author dabockster
 */
public class OfflineController {
    
    private OfflineView view;
    private ClientController controller;
    private OfflineDefeatDialog defeat;
    private OfflineGameController gameController;
    
    /**
     * OfflineController constructor
     * @param ctrl ClientController inheritance
     */
    public OfflineController(ClientController ctrl){
        view = new OfflineView(this);
        view.setVisible(true);
        controller = ctrl;
    }
    /**
     * 
     */
    public void createOffliePlaySession(int playerType1, int playerType2, int rows, int columns){
        gameController = new OfflineGameController(playerType1, playerType2, rows, columns, controller);
        view.setVisible(false);
    }
    
    /**
     * Opens the view
     */
    public void openView(){
        view = new OfflineView(this);
        view.setVisible(true);
    }
    
    /**
     * Closes the view
     */
    public void disposeView(){
        view.dispose();
    }
    
    /**
     * Closes the view and returns to the MainMenu view
     */
    public void toLogin(){
        disposeView();
        controller.refreshClient();        
    }

    /**
     * Opens the defeat Dialog
     */
    public void defeat(){
        defeat = new OfflineDefeatDialog(this);
    }
}
