package main.java.com.lanmessanger.ui.components;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int radius;
    private Color backgroundColor;
    
    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.radius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        
        // Add subtle shadow effect
        g2.setColor(new Color(0, 0, 0, 0));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}