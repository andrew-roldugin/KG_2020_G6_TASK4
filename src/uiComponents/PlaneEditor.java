package uiComponents;

import threedimensions.geometry.Plane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.beancontext.BeanContext;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PlaneEditor extends JPanel {
    private Plane plane;
    private boolean isVisibleP = false;

    public PlaneEditor(Plane plane){
        this.plane = plane;
    }

    public void create() {
        Font standardFont = new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16);
        String str = "start: плоскость ," +
                "param: A," +
                "symbol: x," +
                "op: + " +
                "param: B," +
                "symbol: y," +
                "op: + " +
                "param: C," +
                "symbol: z," +
                "op: + " +
                "param: D," +
                "symbol: = ," +
                "num: 0";
        StringTokenizer st = new StringTokenizer(str, ": ,");
        while (st.hasMoreTokens()) {
            String key = st.nextToken();
            String value = st.nextToken();
            switch (key) {
                case "start":
                case "op":
                case "symbol":
                case "num":
                    JLabel label = new JLabel(value);
                    label.setFont(standardFont);
                    this.add(label);
                    break;
                case "param":
                    //JTextField tf = new JTextField(String.valueOf(plane.getCoef()[value.charAt(0) - 65]));
                    final JTextField tf = new JTextField("0", 3);
                    plane.addPropertyChangeListener(value, new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            SwingUtilities.invokeLater(() -> {
                                tf.setText(evt.getNewValue().toString());
                            });
                        //   tf.setText(evt.getNewValue().toString());
                        }
                    });
                    tf.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {

                        }

                        protected void onUpdate() {
                            String text = tf.getText();
                            assert text != null;
                            if (text.length() == 0) {
                                return;
                            }
                            float num = 0;
                            try {
                                num = Float.parseFloat(text);
                            } catch (NumberFormatException e) {
                                // TODO ИГНОРИРУЕМ ВОЗМОЖНЫЕ ОШИБКИ, ИСПРАВИТЬ
                            }
                           // plane.setNewValue(value, num);
                        }
                    });
                    this.add(tf);
                    break;
            }
        }
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(e -> setVisibleP(checkBox.isSelected()));
        JLabel label = new JLabel("Отображать: ");
        label.setLabelFor(checkBox);
        this.add(label);
        this.add(checkBox);
    }

    public void setVisibleP(boolean visible) {
        isVisibleP = visible;
    }

    public boolean isVisibleP() {
        return isVisibleP;
    }

    public void onValueChanged() {
        /*Arrays.stream(this.getComponents())
                .filter(component -> component instanceof JTextField)
                .forEach(tf ->{
                    ((JTextField) tf).setText();
                    tf.revalidate();
                });
        this.repaint();

         */
    }
}
