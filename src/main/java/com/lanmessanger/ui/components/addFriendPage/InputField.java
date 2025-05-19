package main.java.com.lanmessanger.ui.components.addFriendPage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;

public class InputField extends JPanel {
    private JLabel label;
    private JTextField textField;

    public InputField(String text) {
        label = new JLabel(text);
        textField = new JTextField();
        textField.setSize(100, 20);
        textField.setPreferredSize(new Dimension(100, 20));
        add(label);
        add(textField);
        
    }
}
