package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
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
        super(10, ColorPalette.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        
        // Get the IP address from the system
        ipAddress = getWifiIPAddress();
        
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
    private String getWifiIPAddress() {
        try {
            // Common wireless interface names across different operating systems
            Set<String> wifiInterfaceIdentifiers = new HashSet<>(Arrays.asList(
                // Windows naming patterns
                "wi-fi", "wireless", "wlan", 
                // Linux naming patterns
                "wlp", "wlo", "wlx", "wls", "ath", "wifi",
                // macOS naming patterns
                "en", "airport",
                // Generic patterns
                "wireless"
            ));
            
            // First approach: Try to find interfaces that are up, not loopback, not virtual
            // and match common wireless naming patterns
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                
                // Skip interfaces that are down, loopback, virtual, or not supporting multicast
                if (!networkInterface.isUp() || networkInterface.isLoopback() || 
                    networkInterface.isVirtual() || !networkInterface.supportsMulticast()) {
                    continue;
                }
                
                String interfaceName = networkInterface.getName().toLowerCase();
                String displayName = networkInterface.getDisplayName().toLowerCase();
                
                // Check if this interface matches any known Wi-Fi pattern
                boolean isWifi = false;
                for (String pattern : wifiInterfaceIdentifiers) {
                    if (interfaceName.contains(pattern) || displayName.contains(pattern)) {
                        isWifi = true;
                        break;
                    }
                }
                
                // For Linux, sometimes the interface is simply named "wlan0" or similar
                if (!isWifi && (interfaceName.matches("wlan\\d+") || displayName.matches("wlan\\d+"))) {
                    isWifi = true;
                }
                
                if (isWifi) {
                    // Get all IP addresses assigned to this interface
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    
                    // Look for an IPv4 address
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress address = inetAddresses.nextElement();
                        
                        // Check if it's IPv4 and not a loopback address
                        if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
            
            // Second approach: If the above approach fails, try to find a wireless IP
            // by checking only active, non-loopback interfaces
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                
                // Skip interfaces that are down, loopback, or don't have IP addresses
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
                
                // Skip virtual interfaces
                if (networkInterface.isVirtual() || networkInterface.isPointToPoint()) {
                    continue;
                }
                
                // Get all IP addresses assigned to this interface
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    
                    // Check if it's IPv4, not a loopback, not a link local address
                    if (address instanceof Inet4Address && !address.isLoopbackAddress() 
                        && !address.isLinkLocalAddress() && !address.isMulticastAddress()) {
                        // Found a good candidate
                        String ipAddress = address.getHostAddress();
                        // Avoid returning local network or docker/VM addresses when possible
                        if (!(ipAddress.startsWith("192.168.") || ipAddress.startsWith("172.") || ipAddress.startsWith("10."))) {
                            return ipAddress;
                        } else {
                            // Store this as a backup in case we don't find a better address
                            ipAddress = address.getHostAddress();
                            return ipAddress;
                        }
                    }
                }
            }
            
            // Fall back to the original method if all else fails
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
            
        } catch (Exception e) {
            return "Unable to determine Wi-Fi IP: " + e.getMessage();
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