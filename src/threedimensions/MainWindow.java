package threedimensions;

import models.PlatonicSolid.Solids;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel main = new JPanel();
    private JPanel paintPanel;
    private JPanel sidePanel = new JPanel();
    private DrawPanel dp;
    private JComboBox<String> comboBox = new JComboBox(Solids.getNames());

    public MainWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setVisible(true);

        this.setContentPane(main);

        comboBox.setSelectedIndex(0);
        JButton addSolid = new JButton("Добавить: ");

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.WEST, comboBox);
        container.add(BorderLayout.EAST, addSolid);

        addSolid.addActionListener((e) ->{
            dp.add();
        });
        main.add(container);

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        paintPanel = dp = new DrawPanel();

    }
}
