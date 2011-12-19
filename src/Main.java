
import javax.swing.SwingUtilities;

/**
 *
 * @author stealthii
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runnable r;

        r = new Runnable() {

            public void run() {
                new PacmanGUI().startGUI();
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
