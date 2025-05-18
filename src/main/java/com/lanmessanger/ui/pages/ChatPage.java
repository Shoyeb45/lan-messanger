package main.java.com.lanmessanger.ui.pages;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatPage extends JPanel {
    public ChatPage() {
        setLayout(new FlowLayout());  
        JLabel label = new JLabel("This is chat page");
        
        this.setLayout(null);
        label.setBounds(0, 0, 200, 300);
        this.add(label);
    }
}
