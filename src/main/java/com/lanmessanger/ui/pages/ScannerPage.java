package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.addFriendPage.HeadingPanel;
import main.java.com.lanmessanger.ui.components.scannerPage.FoundDevices;
import main.java.com.lanmessanger.ui.components.scannerPage.ScanButton;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class ScannerPage extends JPanel {
    private HeadingPanel headingPanel;
    private ScanButton scanButton;
    private FoundDevices foundDevices;
    
    public ScannerPage() {
        headingPanel = new HeadingPanel("Scan for nearby friends");
        scanButton = new ScanButton();
        foundDevices = new FoundDevices();
        
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        // Set modern background
        this.setBackground(ColorPalette.BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
    
    private void setupLayout() {
        this.setLayout(new BorderLayout(0, 20));
        
        // Top panel with heading and scan button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ColorPalette.BACKGROUND);
        
        topPanel.add(headingPanel);
        topPanel.add(Box.createVerticalStrut(15));
        
        // Center the scan button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(ColorPalette.BACKGROUND);
        buttonPanel.add(scanButton);
        topPanel.add(buttonPanel);
        
        this.add(topPanel, BorderLayout.NORTH);
        this.add(foundDevices, BorderLayout.CENTER);
    }
    
    public ScanButton getScanButton() {
        return scanButton;
    }
}