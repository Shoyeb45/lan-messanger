package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.addFriendPage.BottomPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.CenterPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.TopPanel;

public class AddFriendPage extends JPanel {

    private TopPanel topPanel;
    private CenterPanel centerPanel;
    private BottomPanel bottomPanel;

    public AddFriendPage() {
        setLayout(new BorderLayout());
        topPanel = new TopPanel();
        centerPanel = new CenterPanel();
        bottomPanel = new BottomPanel();
        topPanel.setBounds(0, 0, 40, getHeight());
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}


