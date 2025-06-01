package main.java.com.lanmessanger.ui.components.scannerPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/** Scan Button for the scanner page */
public class ScanButton extends ModernButton {
    
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
        super("Scan for Devices", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
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
        setIcon(scanIcon);
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
}