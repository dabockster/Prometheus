
package client.manager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author Timothy Ernst
 */
public class ClientStartup {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //set Swing UI components to cross platform mode
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ClientStartup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ClientController client = new ClientController();
    }
}
