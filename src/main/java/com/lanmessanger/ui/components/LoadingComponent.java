package main.java.com.lanmessanger.ui.components;

import javax.swing.*;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;

/**
 * Loading component for time taking operations, like scanning the users
 * @author Claude 4 sonnet
 */
public class LoadingComponent extends JPanel {
    private Timer timer;
    private int angle = 0;
    private final int SPINNER_SIZE = 40;
    private final int STROKE_WIDTH = 4;
    private String loadingText = "Loading...";
    private boolean showText = true;
    
    public LoadingComponent() {
        setPreferredSize(new Dimension(150, 100));
        setBackground(ColorPalette.PANEL_BACKGROUND);
        
        // Animation timer - updates every 50ms for smooth rotation
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 10;
                if (angle >= 360) {
                    angle = 0;
                }
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable anti-aliasing for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        // Draw the spinning arc
        drawSpinner(g2d, centerX, centerY - (showText ? 10 : 0));
        
        // Draw loading text if enabled
        if (showText) {
            drawLoadingText(g2d, centerX, centerY + 25);
        }
        
        g2d.dispose();
    }
    
    private void drawSpinner(Graphics2D g2d, int centerX, int centerY) {
        int x = centerX - SPINNER_SIZE / 2;
        int y = centerY - SPINNER_SIZE / 2;
        
        // Draw background circle with secondary color
        g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(ColorPalette.SECONDARY);
        g2d.drawOval(x, y, SPINNER_SIZE, SPINNER_SIZE);
        
        // Draw the animated arc with primary color
        g2d.setColor(ColorPalette.PRIMARY);
        Arc2D.Double arc = new Arc2D.Double(x, y, SPINNER_SIZE, SPINNER_SIZE, angle, 90, Arc2D.OPEN);
        g2d.draw(arc);
    }
    
    private void drawLoadingText(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(ColorPalette.TEXT);
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 15));
        
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(loadingText);
        int textX = centerX - textWidth / 2;
        
        g2d.drawString(loadingText, textX, centerY);
    }
    
    // Public methods to control the loading component
    public void startLoading() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }
    
    public void stopLoading() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }
    
    public boolean isLoading() {
        return timer.isRunning();
    }
    
    public void setLoadingText(String text) {
        this.loadingText = text;
        repaint();
    }
    
    public void setShowText(boolean showText) {
        this.showText = showText;
        repaint();
    }
    
    // Getters and setters for customization
    // public void setPrimaryColor(Color color) {
    //     this.ColorPallete.PRIMARY = color;
    //     repaint();
    // }
    
    // public void setColorPalette.SECONDARY(Color color) {
    //     this.ColorPalette.SECONDARY = color;
    //     repaint();
    // }
    
    // public void setTextColor(Color color) {
    //     this.textColor = color;
    //     repaint();
    // }

}