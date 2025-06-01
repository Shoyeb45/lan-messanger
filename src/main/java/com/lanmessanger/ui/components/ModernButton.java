package main.java.com.lanmessanger.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class ModernButton extends JButton {
    /** Boolean indicating hover or not */
    private boolean isHover = false;
    /** Default Color */
    private Color normalColor = ColorPalette.PRIMARY;
    private Color hoverColor = ColorPalette.SECONDARY;
    
    public ModernButton(String text, Color normalColor, Color hoverColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // listen for mouse events
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
            if (isEnabled()) {
                g2.setColor(normalColor);
            } else {
                g2.setColor(hoverColor);
            }
        }
        
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        
        // Get icon and text
        Icon icon = getIcon();
        String text = getText();
        FontMetrics fm = g2.getFontMetrics();
        
        // Calculate positions for icon and text
        int iconWidth = (icon != null) ? icon.getIconWidth() : 0;
        int iconHeight = (icon != null) ? icon.getIconHeight() : 0;
        int textWidth = (text != null) ? fm.stringWidth(text) : 0;
        int gap = (icon != null && text != null && !text.isEmpty()) ? getIconTextGap() : 0;
        
        int totalWidth = iconWidth + gap + textWidth;
        int startX = (getWidth() - totalWidth) / 2;
        
        // Draw icon
        if (icon != null) {
            int iconX = startX;
            int iconY = (getHeight() - iconHeight) / 2;
            icon.paintIcon(this, g2, iconX, iconY);
        }
        
        // Draw text
        if (text != null && !text.isEmpty()) {
            g2.setColor(Color.WHITE);
            int textX = startX + iconWidth + gap;
            int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(text, textX, textY);
        }
        
        g2.dispose();
    }
}