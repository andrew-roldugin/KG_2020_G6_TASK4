package uiComponents;

import threedimensions.DrawPanel;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JPanel main;
    private JPanel paintPanel;
    private DrawPanel dp;
    private EditorWindow window;

    public MainWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);

        this.setContentPane(main);
        window = new EditorWindow();
        window.setDrawPanel(dp);
    }

    private void createUIComponents() {
        paintPanel = dp = new DrawPanel();
    }
}
