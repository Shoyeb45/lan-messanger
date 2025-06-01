package main.java.com.lanmessanger.ui.pages;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Start page to display when user first enters our application.
 * It greets the user and tells about the application
 */
public class StartPage extends JPanel {

    public StartPage() {
        JLabel label = new JLabel("This is start page");
        label.setBounds(0, 0, 303, 303);
        this.add(label);
    }

}
