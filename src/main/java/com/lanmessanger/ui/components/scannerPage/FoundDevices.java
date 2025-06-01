package main.java.com.lanmessanger.ui.components.scannerPage;

import java.awt.BorderLayout;
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
import main.java.com.lanmessanger.ui.components.LoadingComponent;
import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.components.ModernScrollBarUI;
import main.java.com.lanmessanger.ui.utils.ColorPalette;
import main.java.com.lanmessanger.ui.utils.Dialog;

public class FoundDevices extends JPanel {

    /** Container where all the nearby users will be displayed */
    private JPanel devicesContainer;
    /** Scroll pane to show the scroll bar */
    private JScrollPane scrollPane;
    /** Label which contain the heading text <h3>"Found devices"</h3> */
    private JLabel statusLabel;
    /** Panel which shows  */
    private NoDevicePanel noDeviceFoundPanel;

    /** Loader component to show the loading animation */
    private JPanel loader;

    private LoadingComponent loadingComponent;
    /** List of the found devices, state of this component */
    String[] foundDevices;

    /** Initialise the {@code FoundDevices} class using empty constructor 
     * @ */
    public FoundDevices()  {
        initializeComponents();
        setupLayout();
        addFoundDevices();
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
        devicesContainer.setBackground(ColorPalette.PANEL_BACKGROUND);
        devicesContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Scroll pane
        scrollPane = new JScrollPane(devicesContainer);
        scrollPane.setBackground(ColorPalette.BACKGROUND);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Style the scrollbar
        scrollPane.getVerticalScrollBar().setBackground(ColorPalette.BACKGROUND);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        // no device found panel

        // loader
        loader = new JPanel();
        loader.setLayout(new BorderLayout());
        loadingComponent = new LoadingComponent();
        loadingComponent.setLoadingText("Scanning nearby devices....");
        loader.add(loadingComponent, BorderLayout.CENTER);
        noDeviceFoundPanel = new NoDevicePanel();
    }


    /**
     * Method to set up the layout of this component by adding label and scroll pane
     */
    private void setupLayout() {
        add(statusLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Add found devices to main container
     * @ 
     */
    private void addFoundDevices() {
        if (foundDevices == null) {
            // No devices panel
            addNoDevicesFoundPanel();
            return;
        }

        // remove the text which shows that no device found
        for (String ip: foundDevices) {
            addDevice("User", ip);
        }
    }
    
    /**
     * Method for updating the found devices state.
     * @param foundDevices new array of found devices
     * @ 
     */
    public void setFoundDevices(String[] foundDevices) {
        // Clear all previous rendering
        devicesContainer.removeAll();

        // update the state
        this.foundDevices = foundDevices;
        // re-render all the users
        addFoundDevices();
    }

    

    /** Method to add the no Device Panel */
    private void addNoDevicesFoundPanel() {
        devicesContainer.add(noDeviceFoundPanel);
    }


    /**
     * Method to add found user in the container
     * @param deviceName  The name of the device
     * @param ipAddress   IP address of the device
     * @ 
     */
    public void addDevice(String deviceName, String ipAddress)  {
        
        JPanel deviceInfo = createDeviceInfoPanel(deviceName, ipAddress);
        ChatProfile deviceProfile = new ChatProfile(12, deviceInfo, ColorPalette.BACKGROUND);
        deviceProfile.setBackground(ColorPalette.BACKGROUND);
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
        String response = Dialog.showInputDialog(this, "Enter the name of the friend", "Add friend", Dialog.QUESTION_MESSAGE);
        if (response != null && response.isBlank()) {
            Dialog.showMessageDialog(this, "Please enter name of your friend", "Invalid Name", Dialog.ERROR_MESSAGE);
        }
    }

    public void setLoadingPanel() {
        devicesContainer.removeAll();
        devicesContainer.add(loader);
        devicesContainer.revalidate();
        loadingComponent.startLoading();
    }
}

/** Component Which shows <i>"No devices Found"</i> */
class NoDevicePanel extends JPanel {
    JLabel label;
    public NoDevicePanel() {
        label = new JLabel("No devices found", SwingConstants.CENTER);
        label.setForeground(ColorPalette.SECONDARY_TEXT);
        label.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        setBackground(ColorPalette.PANEL_BACKGROUND);
        add(label);
    }
}

