package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.Button;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BottomPanel extends JPanel {
    private Button checkConnection;
    private Button addFriend;
    private InputField name;
    private InputField ip;
    
    public BottomPanel() {
        name = new InputField("Name");
        ip = new InputField("IP Address");
        add(name);
        add(ip);
        setBorder(BorderFactory.createLineBorder(Color.yellow));
    }
}


