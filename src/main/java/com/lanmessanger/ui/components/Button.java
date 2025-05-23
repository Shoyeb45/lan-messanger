package main.java.com.lanmessanger.ui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

/** Button component  */
public class Button extends JButton {

    private boolean isActive = false;

    public Button(Icon icon) {
        super(icon);
        
        setFocusable(false);
        setBackground(Color.white);
        setForeground(ColorPalette.TEXT);
        
        // setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // top, left, bottom, right

      
        // // Optional: margin inside the button
        // setMargin(new Insets(10, 20, 10, 20));
    }


    public void setIsActive(boolean isActive) {
        System.out.println("I came here");
        this.isActive = isActive;
        if (isActive) {
            this.setBorder(new MatteBorder(0, 0, 0, 2, ColorPalette.PRIMARY));
        } else {
            this.setBorder(BorderFactory.createEmptyBorder());
        }
        // this.repaint();
        revalidate();
    }
    
    public boolean getIsActive() {
        return isActive;
    }
    // private void setUpTheLabel() {
        
    //     this.add(label);
    // }
}
