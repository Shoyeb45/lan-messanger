package main.java.com.lanmessanger.ui.components;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {

    public Button(String title, ImageIcon icon) {
        super(title, icon);

        this.setFocusable(false);
        this.setBackground(Color.white);
        // this.setBorder(null);
        
        this.setMargin(new Insets(20, 20, 20, 20));
    }

    // private void setUpTheLabel() {
        
    //     this.add(label);
    // }
}
