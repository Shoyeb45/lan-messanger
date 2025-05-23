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

/** 
 * Scanner Page where user can scan the nearby devices which are connected to same network 
 * @see HeadingPanel
 * @see ScanButton
 * @see FoundDevices
 * @author Shoyeb Ansari
*/
public class ScannerPage extends JPanel {
    /** Heading panel, which display the heading of the page */
    private HeadingPanel headingPanel;
    /** Scan button for start scanning */
    private ScanButton scanButton;
    /** A component which will display all found devices */
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
        
        // add scanning functionality
        scanButton.addActionListener(e -> scanNearbyUsers());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(foundDevices, BorderLayout.CENTER);
    }
    
    private void scanNearbyUsers() {
        System.out.println("Did I came here?");
        String[] s = {"192.232.123.12", "141.89.12.00", "10.109.178.12"};
        foundDevices.setFoundDevices(s);
    }
}