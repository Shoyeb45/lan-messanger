package main.java.com.lanmessanger.ui.pages;

import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.addFriendPage.TopPanel;

public class AddFriendPage extends JPanel {

    private TopPanel topPanel;

    public AddFriendPage() {
        topPanel = new TopPanel();
        topPanel.setBounds(0, 0, getWidth(), getHeight());
        
        this.add(topPanel);
    }
}


