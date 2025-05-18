package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.navBar.NavBar;

/** Navigation Page on the left side of the application */
public class NavigationPage extends JLayeredPane {
  

    private NavBar navBar;

    private JPanel mainContentPage;

    public NavigationPage(JPanel mainContentPage) {

        // by default set start page
        if (this.mainContentPage == null) {
            mainContentPage = new StartPage();
            mainContentPage.setBounds(0, 0, 120, 120);
        }
        
        // initialise new NavBar
        navBar = new NavBar();
        // Setting layout manager for JLayeredPane 
        this.setLayout(new BorderLayout());

        // give padding 
        this.setBackground(Color.white);

        // add navbar to our pane 
        this.add(navBar, BorderLayout.WEST, Integer.valueOf(2));

        
        
    }
}