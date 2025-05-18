package main.java.com.lanmessanger.ui.pages;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainPage extends JPanel {
    private NavigationPage navigationPage;

    public MainPage() {
        // creating app in 3 parts
        this.setBackground(Color.green);
        this.setLayout(new BorderLayout());
        // this.setBounds(0, 0, getWidth(), 100);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        navigationPage = new NavigationPage();

        this.add(navigationPage, BorderLayout.WEST);
    }
   
}
