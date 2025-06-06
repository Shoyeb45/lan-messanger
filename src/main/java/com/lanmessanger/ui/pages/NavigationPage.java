package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLayeredPane;

import main.java.com.lanmessanger.ui.components.navBar.NavBar;

/** 
 * Navigation Page on the left side of the application
 * @author Shoyeb Ansari
 * */
public class NavigationPage extends JLayeredPane {
  
    /** 
     * Nav Bar component for navigation page
     */
    private NavBar navBar;

    public NavigationPage() {
        // initialise new NavBar
        navBar = new NavBar();
        
        // Setting layout manager for JLayeredPane 
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        // add navbar to our pane 
        this.add(navBar, BorderLayout.WEST, Integer.valueOf(2));
    }
}