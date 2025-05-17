package main.java.com.lanmessanger.ui.pages;


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.Button;

public class MainPage extends JPanel {
    public MainPage() {
        // creating app in 3 parts
        ImageIcon icon = new ImageIcon("./public/images/navbar/1.png");

        // Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        this.add(new Button("Menu", icon));
    }

    private void setUpLayout() {
    }
}
