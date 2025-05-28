package main.java.com.lanmessanger.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * Custom rounded border implementation
 */
public class RoundedBorder implements javax.swing.border.Border {
    private int radius;
    private Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(2, 2, 2, 2);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color.darker());
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}