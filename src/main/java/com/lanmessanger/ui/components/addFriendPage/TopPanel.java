package main.java.com.lanmessanger.ui.components.addFriendPage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopPanel extends JPanel {
    private JLabel textLabel;
    
    public TopPanel() {
        textLabel = new JLabel("Add friend to start chatting");
        textLabel.setBounds(0, 0, 100, 100);
        this.add(textLabel);

    
    }
}
