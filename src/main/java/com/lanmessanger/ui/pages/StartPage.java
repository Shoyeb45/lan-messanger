package main.java.com.lanmessanger.ui.pages;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPage extends JPanel {

    public StartPage() {
        JLabel label = new JLabel("This is start page");
        label.setBounds(0, 0, 303, 303);
        this.add(label);
    }

}
