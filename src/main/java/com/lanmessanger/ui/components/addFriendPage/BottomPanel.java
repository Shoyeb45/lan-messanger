package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.ButtonUI;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.Button;

public class BottomPanel extends JPanel {
    private Button checkConnection;
    private Button addFriend;
    private InputField name;
    private InputField ip;
    
    public BottomPanel() {
        name = new InputField("Name");
        ip = new InputField("IP Address");
        checkConnection = new Button(null);
        checkConnection.setText("Connect");

        addFriend = new Button(FontIcon.of(FontAwesome.PLUS, 12));
        addFriend.setText("Add");

        add(name);
        add(ip);
        add(checkConnection);
        add(addFriend);
        setBorder(BorderFactory.createLineBorder(Color.yellow));
    }
}


