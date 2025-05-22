package main.java.com.lanmessanger.ui.components;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.utils.ColorPalette;


/** Component to render circular icon */
public class CircularIcon extends JPanel {

    /** The icon */
    private FontIcon userIcon;
    /** Color of the background of the icon */
    private Color circleColor;
    /** Color of the icon */
    private Color iconColor;

    /**
     * 
     * @param icon icon which needs to be in center of the circular panel
     */
    public CircularIcon(FontIcon icon) {
        this.userIcon = icon;
        this.circleColor = ColorPalette.PRIMARY;
        this.iconColor = Color.WHITE;
        
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(46, 46));
        this.setMinimumSize(new Dimension(46, 46));
        this.setMaximumSize(new Dimension(46, 46));
    }
    
    public CircularIcon(FontIcon icon, Color circleColor, Color iconColor) {
        this.userIcon = icon;
        this.circleColor = circleColor;
        this.iconColor = iconColor;
        
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(46, 46));
        this.setMinimumSize(new Dimension(46, 46));
        this.setMaximumSize(new Dimension(46, 46));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int diameter = Math.min(getWidth(), getHeight()) - 2; // Leave 1px margin
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        
        // Draw shadow
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillOval(x + 1, y + 1, diameter, diameter);
        
        // Draw main circle
        g2d.setColor(circleColor);
        g2d.fillOval(x, y, diameter, diameter);
        
        // Draw subtle inner border for depth
        g2d.setColor(new Color(255, 255, 255, 40));
        g2d.drawOval(x + 1, y + 1, diameter - 2, diameter - 2);

        // Paint the icon in the center
        if (userIcon != null) {
            // Set icon color
            userIcon.setIconColor(iconColor);
            
            int iconWidth = userIcon.getIconWidth();
            int iconHeight = userIcon.getIconHeight();

            int iconX = (getWidth() - iconWidth) / 2;
            int iconY = (getHeight() - iconHeight) / 2;

            userIcon.paintIcon(this, g2d, iconX, iconY);
        }

        g2d.dispose();
    }
    
    /**
     * Change the color of the background of the icon containing panel
     * @param color new color 
     */
    public void setCircleColor(Color color) {
        this.circleColor = color;
        repaint();
    }
    
    /**
     * Change the color of the icon
     * @param color new color
     */
    public void setIconColor(Color color) {
        this.iconColor = color;
        repaint();
    }
    
    /**
     * Get the color of the background
     * @return background color
     */
    public Color getCircleColor() {
        return circleColor;
    }

    
    /**
     * Get the color of the icon
     * @return icon color
     */
    public Color getIconColor() {
        return iconColor;
    }
}