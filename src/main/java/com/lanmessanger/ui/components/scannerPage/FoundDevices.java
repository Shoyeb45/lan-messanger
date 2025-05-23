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

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.ChatProfile;
import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.utils.ColorPalette;
import main.java.com.lanmessanger.ui.utils.Dialog;

public class FoundDevices extends JPanel {

    /** Container where all the nearby users will be displayed */
    private JPanel devicesContainer;
    /** Scroll pane to show the scroll bar */
    private JScrollPane scrollPane;
    /** Label which contain the heading text <h3>"Found devices"</h3> */
    private JLabel statusLabel;

    /** Initialise the {@code FoundDevices} class using empty constructor */
    public FoundDevices() {
        initializeComponents();
        setupLayout();
        addSampleDevices();
    }
    
    /** Method to initialise different components inside this component */
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


    /**
     * Method to set up the layout of this component by adding label and scroll pane
     */
    private void setupLayout() {
        add(statusLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addSampleDevices() {
        // Add some sample devices for demonstration
        addDevice("John's MacBook", "192.168.1.105");
        addDevice("Sarah's iPhone", "192.168.1.112");
        addDevice("Office PC", "192.168.1.089");
        addDevice("Gaming Laptop", "192.168.1.156");
    }
    

    /**
     * Method to add found user in the container
     * @param deviceName  The name of the device
     * @param ipAddress   IP address of the device
     */
    public void addDevice(String deviceName, String ipAddress) {
        JPanel deviceInfo = createDeviceInfoPanel(deviceName, ipAddress);
        ChatProfile deviceProfile = new ChatProfile(deviceInfo);
        
        // Add spacing between devices
        if (devicesContainer.getComponentCount() > 0) {
            devicesContainer.add(Box.createVerticalStrut(8));
        }
        
        devicesContainer.add(deviceProfile);
        devicesContainer.revalidate();
        devicesContainer.repaint();
    }
    
    /**
     * Method to create the device info panel
     * @param deviceName  Name of the device
     * @param ipAddress   IP Address of the device
     * @return
     */
    private JPanel createDeviceInfoPanel(String deviceName, String ipAddress) {
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
        
        // Add button
        ModernButton addButton = new ModernButton("Add", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
        // Add functionality to add the user into our local database
        addButton.addActionListener(e -> addUser(ipAddress));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel.add(addButton, gbc);
        
        return panel;
    }

    /**
     * Method to add the user into our database
     * @param ip IP Address of the user
     */
    private void addUser(String ip) {
        String response = Dialog.showInputDialog(null, "Enter the name of the friend", "Add friend", Dialog.QUESTION_MESSAGE);
        if (response.isBlank()) {
            Dialog.showMessageDialog(null, "Please enter name of your friend", "Invalid Name", Dialog.ERROR_MESSAGE);
        }
    }
}