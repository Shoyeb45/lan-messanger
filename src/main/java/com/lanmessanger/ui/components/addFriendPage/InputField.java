package main.java.com.lanmessanger.ui.components.addFriendPage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputField extends JPanel {
    private JLabel label;
    private JTextField textField;

    public InputField(String text) {
        label = new JLabel(text);
        textField = new JTextField();
        
        add(label);
        add(textField);
        
    }
}
