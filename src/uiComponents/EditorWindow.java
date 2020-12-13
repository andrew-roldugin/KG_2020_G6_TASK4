package uiComponents;

import controllers.SolidController;
import models.AbstractModel;
import models.Plane3D;
import models.PlatonicSolid.Cube;
import models.PlatonicSolid.SolidFactory;
import models.PlatonicSolid.Solids;
import threedimensions.DrawPanel;
import threedimensions.geometry.Plane;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditorWindow extends JFrame {
    private JPanel contentPane;
    private JComboBox<String> solidsComboBox;
    private JSlider Aslider;
    private JSlider Bslider;
    private JSlider Cslider;
    private JSlider Dslider;
    private JButton OKButton;
    private JComboBox<SolidController.Parts> partComboBox;
    private JTextField ATextField;
    private JTextField BtextField;
    private JTextField CtextField;
    private JTextField DtextField;
    private JCheckBox visibleCheckBox;
    private JButton applyButton;
    private final SolidController controller;
    private AbstractModel solid = new Cube(1);
    private SolidController.Parts part = SolidController.Parts.ORIGINAL;
    private DrawPanel drawPanel;

    private final Plane plane = new Plane();

    public EditorWindow() {
        this.setSize(550, 220);
        this.setContentPane(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        revalidate();

        controller = new SolidController(plane);

        Aslider.addChangeListener(e -> {
            plane.setNewValue("A", Aslider.getValue() / 10.f);
            fullyRepaint();
        });
        Bslider.addChangeListener(e -> {
            plane.setNewValue("B", Bslider.getValue() / 10.f);
            fullyRepaint();
        });
        Cslider.addChangeListener(e -> {
            plane.setNewValue("C", Cslider.getValue() / 10.f);
            fullyRepaint();
        });
        Dslider.addChangeListener(e -> {
            plane.setNewValue("D", Dslider.getValue() / 10.f);
            fullyRepaint();
        });

        initTextFields();
        setupButtons();
    }

    private void fullyRepaint(){
        AbstractModel model = controller.getRequiredPart(solid, part);
        drawPanel.clear();
        drawPanel.add(model);
        if(visibleCheckBox.isSelected() && plane.isPresent())
            drawPanel.add(new Plane3D(plane));
    }

    private void createUIComponents() {
        partComboBox = new JComboBox<>(SolidController.Parts.values());
        partComboBox.setSelectedIndex(0);

        solidsComboBox = new JComboBox<String>(Solids.getNames());
        solidsComboBox.setSelectedIndex(1);
        solidsComboBox.addActionListener((e) -> {
            solid = SolidFactory.createSolid(Solids.getType(String.valueOf(solidsComboBox.getSelectedItem())), 1);
        });

        partComboBox.addActionListener(e -> {
             part = (SolidController.Parts) partComboBox.getSelectedItem();
        });
    }

    private void initTextFields(){
        final char[] ch = {'A'};
        List<JTextField> tfs = new ArrayList<>(Arrays.asList(ATextField, BtextField, CtextField, DtextField));
        tfs.forEach(tf -> {
            plane.addPropertyChangeListener(String.valueOf(ch[0]),
                    evt -> SwingUtilities.invokeLater(
                            () -> tf.setText(evt.getNewValue().toString()))
            );
            ch[0]++;
        });
    }
    private void setupButtons(){
        applyButton.addActionListener(e -> {
            plane.setNewValue("A", Float.parseFloat(ATextField.getText()));
            plane.setNewValue("B", Float.parseFloat(BtextField.getText()));
            plane.setNewValue("C", Float.parseFloat(CtextField.getText()));
            plane.setNewValue("D", Float.parseFloat(DtextField.getText()));
            fullyRepaint();
        });
        OKButton.addActionListener(e -> {
            fullyRepaint();
        });
    }

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }
}
