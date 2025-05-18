package main.java.com.lanmessanger.ui.pages;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagingPage extends JPanel {
    public MessagingPage() {
        JLabel label = new JLabel("Messaging page");
        
        this.setLayout(null);
        label.setBounds(0, 0, 200, 300);
        this.add(label);
    }
}
