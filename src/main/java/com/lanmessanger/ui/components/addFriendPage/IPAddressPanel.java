package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.components.Button;
import main.java.com.lanmessanger.ui.components.RoundedPanel;
import main.java.com.lanmessanger.ui.utils.ColorPalette;


/** Panel for add friend page which displays the IP address of the user and provides copy button */
class IPAddressPanel extends RoundedPanel {
    /** Label which will hold the IP address */
    private JLabel ipAddressLabel;
    /** title label for this component */
    private JLabel titleLabel;
    /** Button component which will have copy functionality */
    private Button copyButton;
    /** IP address of the user */
    private String ipAddress;
    
    public IPAddressPanel() {
        super(10, ColorPalette.INPUT_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        // Get the IP address from the system
        ipAddress = getSystemIPAddress();
        
        // Create the title label
        titleLabel = new JLabel("Your IP Address:");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(ColorPalette.SECONDARY_TEXT);
        
        // Create the IP address label
        ipAddressLabel = new JLabel(ipAddress);
        ipAddressLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ipAddressLabel.setForeground(ColorPalette.PRIMARY);
        
        // Create a copy button with icon
        copyButton = new Button(FontIcon.of(FontAwesome.COPY, 15));
        copyButton.setToolTipText("Copy IP Address");
        
        // Add action listener to the copy button
        copyButton.addActionListener(e -> copyIPAddressToClipboard());
        
        // Add components to the panel
        add(titleLabel);
        add(ipAddressLabel);
        add(copyButton);
    }
    
    /**
     * Gets the system's IP address
     * @return String representation of the IP address
     */
    private String getSystemIPAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            return "Unable to determine IP";
        }
    }
    
    /**
     * Copies the IP address to clipboard
     */
    private void copyIPAddressToClipboard() {
        StringSelection stringSelection = new StringSelection(ipAddress);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
        // Provide visual feedback
        SwingUtilities.invokeLater(() -> {
            // Change text color for visual feedback
            ipAddressLabel.setForeground(ColorPalette.ACCENT);
            
            // Reset color after a delay
            Timer timer = new Timer(1000, evt -> {
                ipAddressLabel.setForeground(ColorPalette.PRIMARY);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
    
    /**
     * Updates the IP address displayed in the panel
     * @param newIpAddress The new IP address to display
     */
    public void updateIPAddress(String newIpAddress) {
        this.ipAddress = newIpAddress;
        this.ipAddressLabel.setText(newIpAddress);
    }
    
    /**
     * Returns the current IP address
     * @return The current IP address
     */
    public String getIpAddress() {
        return this.ipAddress;
    }
}