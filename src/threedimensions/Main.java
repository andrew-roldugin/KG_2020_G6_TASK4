/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threedimensions;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       // new MainWindow();
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new DrawPanel());
        frame.setVisible(true);
    }
}
