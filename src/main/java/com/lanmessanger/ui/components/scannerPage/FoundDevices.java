package main.java.com.lanmessanger.ui.components.scannerPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.com.lanmessanger.ui.components.ChatProfile;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class FoundDevices extends JPanel {

    private JPanel devicesContainer;
    private JScrollPane scrollPane;
    private JLabel statusLabel;

    public FoundDevices() {
        initializeComponents();
        setupLayout();
        addSampleDevices();
    }
    
    private void initializeComponents() {
        // Main container setup
        this.setBackground(ColorPalette.BACKGROUND);
        this.setLayout(new BorderLayout(0, 15));
        
        // Status label
        statusLabel = new JLabel("Found devices:", SwingConstants.LEFT);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(ColorPalette.TEXT);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        
        // Devices container
        devicesContainer = new JPanel();
        devicesContainer.setLayout(new BoxLayout(devicesContainer, BoxLayout.Y_AXIS));
        devicesContainer.setBackground(ColorPalette.BACKGROUND);
        devicesContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Scroll pane
        scrollPane = new JScrollPane(devicesContainer);
        scrollPane.setBackground(ColorPalette.BACKGROUND);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
    
    private void setupLayout() {
        add(statusLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSampleDevices() {
        // Add some sample devices for demonstration
        addDevice("John's MacBook", "192.168.1.105", true);
        addDevice("Sarah's iPhone", "192.168.1.112", false);
        addDevice("Office PC", "192.168.1.089", true);
        addDevice("Gaming Laptop", "192.168.1.156", false);
    }
    
    public void addDevice(String deviceName, String ipAddress, boolean isOnline) {
        JPanel deviceInfo = createDeviceInfoPanel(deviceName, ipAddress, isOnline);
        ChatProfile deviceProfile = new ChatProfile(deviceInfo);
        
        // Add spacing between devices
        if (devicesContainer.getComponentCount() > 0) {
            devicesContainer.add(Box.createVerticalStrut(8));
        }
        
        devicesContainer.add(deviceProfile);
        devicesContainer.revalidate();
        devicesContainer.repaint();
    }
    
    private JPanel createDeviceInfoPanel(String deviceName, String ipAddress, boolean isOnline) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ColorPalette.PANEL_BACKGROUND);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Device name
        JLabel nameLabel = new JLabel(deviceName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(ColorPalette.TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 2, 0);
        panel.add(nameLabel, gbc);
        
        // IP Address
        JLabel ipLabel = new JLabel(ipAddress);
        ipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ipLabel.setForeground(ColorPalette.SECONDARY_TEXT);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(ipLabel, gbc);
        
        // Online status indicator
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        statusPanel.setBackground(ColorPalette.PANEL_BACKGROUND);
        
        JLabel statusDot = new JLabel("●");
        statusDot.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusDot.setForeground(isOnline ? ColorPalette.ACCENT : ColorPalette.ERROR);
        
        JLabel statusText = new JLabel(isOnline ? "Online" : "Offline");
        statusText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusText.setForeground(isOnline ? ColorPalette.ACCENT : ColorPalette.ERROR);
        
        statusPanel.add(statusDot);
        statusPanel.add(Box.createHorizontalStrut(5));
        statusPanel.add(statusText);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel.add(statusPanel, gbc);
        
        return panel;
    }
}