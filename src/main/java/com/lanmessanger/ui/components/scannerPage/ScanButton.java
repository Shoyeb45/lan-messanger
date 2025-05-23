package main.java.com.lanmessanger.ui.components.scannerPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.Timer;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/** Scan Button for the scanner page */
public class ScanButton extends JButton {
    
    /** Flag to indicate if the user is scanning or not */
    private boolean isScanning = false;
    /** Flag to indicate the hover on the button */
    private boolean isHovered = false;
    /** Scan Icon */
    private FontIcon scanIcon;
    /** Loading Icon */
    private FontIcon loadingIcon;

    private Timer animationTimer;
    private int rotationAngle = 0;
    private List<ActionListener> scanListeners = new ArrayList<>();
    

    public ScanButton() {
        initializeIcons();
        initializeButton();
        setupAnimationTimer();
        setupInteractions();
    }
    
    /** Method to initialise icons */
    private void initializeIcons() {
        scanIcon = FontIcon.of(FontAwesome.SEARCH, 16);
        scanIcon.setIconColor(Color.WHITE);
        
        loadingIcon = FontIcon.of(FontAwesome.REFRESH, 16);
        loadingIcon.setIconColor(Color.WHITE);
    }
    
    /** Method to initialise the button */
    private void initializeButton() {
        this.setText("Scan for Devices");
        this.setFont(new Font("Segoe UI", Font.BOLD, 14));
        this.setForeground(Color.WHITE);
        this.setBackground(ColorPalette.PRIMARY);
        this.setPreferredSize(new Dimension(180, 45));
        this.setMinimumSize(new Dimension(180, 45));
        this.setMaximumSize(new Dimension(180, 45));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
    }
    
    private void setupAnimationTimer() {
        animationTimer = new Timer(50, e -> {
            if (isScanning) {
                rotationAngle += 15;
                if (rotationAngle >= 360) {
                    rotationAngle = 0;
                }
                repaint();
            }
        });
    }
    
    /** Method to change the value of the event indicating flags */
    private void setupInteractions() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isScanning) {
                    isHovered = true;
                    repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isScanning) {
                    startScanning();
                }
            }
        });
    }
    
    /**
     * Overridden method to draw the component by customizing it
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine button color based on state
        Color buttonColor;
        if (isScanning) {
            buttonColor = ColorPalette.SECONDARY;
        } else if (isHovered) {
            buttonColor = ColorPalette.PRIMARY.darker();
        } else {
            buttonColor = ColorPalette.PRIMARY;
        }
        
        // Draw button shadow
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 22, 22);
        
        // Draw button background
        g2d.setColor(buttonColor);
        g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 22, 22);
        
        // Draw button border highlight
        g2d.setColor(new Color(255, 255, 255, 60));
        g2d.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 20, 20);
        
        g2d.dispose();
        
        // Paint text and icon
        super.paintComponent(g);
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw icon
        FontIcon currentIcon = isScanning ? loadingIcon : scanIcon;
        
        if (isScanning && loadingIcon != null) {
            // Rotate the loading icon
            int centerX = 25;
            int centerY = getHeight() / 2;
            
            g2d.translate(centerX, centerY);
            g2d.rotate(Math.toRadians(rotationAngle));
            g2d.translate(-8, -8); // Half of icon size
            
            loadingIcon.paintIcon(this, g2d, 0, 0);
        } else if (!isScanning && scanIcon != null) {
            scanIcon.paintIcon(this, g2d, 15, (getHeight() - 16) / 2);
        }
        
        // Draw text
        g2d.setColor(Color.WHITE);
        g2d.setFont(getFont());
        
        // String text = isScanning ? "Scanning..." : "";
        // int textWidth = g2d.getFontMetrics().stringWidth(text);
        // int textX = (getWidth() - textWidth) / 2 + (isScanning ? 0 : 10);
        // int textY = (getHeight() + g2d.getFontMetrics().getAscent()) / 2 - 2;
        
        // g2d.drawString(text, textX, textY);
        
        g2d.dispose();
    }
    
    /**
     * Method to start the scanning process
     */
    public void startScanning() {
        if (!isScanning) {
            isScanning = true;
            rotationAngle = 0;
            animationTimer.start();
            
            // Notify listeners that scanning started
            // fireScanStarted();
            
            // Auto-stop scanning after 3 seconds (simulate scan duration)
            Timer stopTimer = new Timer(3000, e -> stopScanning());
            stopTimer.setRepeats(false);
            stopTimer.start();
            
            repaint();
        }
    }
    
    public void stopScanning() {
        if (isScanning) {
            isScanning = false;
            animationTimer.stop();
            
            // Notify listeners that scanning stopped
            // fireScanCompleted();
            
            repaint();
        }
    }
    
    /**
     * Get the flag of the scanning process
     * @return if the button is scanning or not
     */
    public boolean isScanning() {
        return isScanning;
    }
    
    // Event handling for scan actions
    public void addScanListener(ActionListener listener) {
        scanListeners.add(listener);
    }
    
    public void removeScanListener(ActionListener listener) {
        scanListeners.remove(listener);
    }
    
    // private void fireScanStarted() {
    //     ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SCAN_STARTED");
    //     for (ActionListener listener : scanListeners) {
    //         listener.actionPerformed(event);
    //     }
    // }
    
    // private void fireScanCompleted() {
    //     ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SCAN_COMPLETED");
    //     for (ActionListener listener : scanListeners) {
    //         listener.actionPerformed(event);
    //     }
    // }
}