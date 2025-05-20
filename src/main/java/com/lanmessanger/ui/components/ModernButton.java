package main.java.com.lanmessanger.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class ModernButton extends JButton {
    private boolean isHover = false;
    private Color normalColor = ColorPalette.PRIMARY;
    private Color hoverColor = ColorPalette.SECONDARY;
    
    public ModernButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHover = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHover = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint button background
        if (isHover) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }
        
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        
        // Draw text
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getHeight();
        g2.drawString(getText(), (getWidth() - textWidth) / 2, 
                    (getHeight() - textHeight) / 2 + fm.getAscent());
        
        g2.dispose();
    }
}