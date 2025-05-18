package main.java.com.lanmessanger.ui.components;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton {

    private boolean isActive = false;

    public Button(Icon icon) {
        super(icon);

        setFocusable(false);
        setBackground(Color.white);
        // setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // top, left, bottom, right

        if (isActive) {
            System.out.println("I am chat");
            this.setBackground(Color.GRAY);
        }
        // // Optional: margin inside the button
        // setMargin(new Insets(10, 20, 10, 20));
    }


    public void setIsActive(boolean isActive) {
        System.out.println("I came here");
        this.isActive = isActive;
        this.repaint();
    }
 
    // private void setUpTheLabel() {
        
    //     this.add(label);
    // }
}
