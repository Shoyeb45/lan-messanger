package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.components.Button;

/** 
 * Component which will have the ip of the user
 */
class IPAddressPanel extends JPanel {
    private JLabel ipAddressLabel;
    private Button copyButton;
    private String ipAddress;

    /**
     * Constructor for the IPAddressPanel
     */
    public IPAddressPanel() {
        // Get the IP address
        ipAddress = getSystemIPAddress();
        
        // Set the layout
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        // Create the IP address display
        JLabel titleLabel = new JLabel("Your IP Address:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        ipAddressLabel = new JLabel(ipAddress);
        ipAddressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ipAddressLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Create the copy button
        copyButton = new Button(FontIcon.of(FontAwesome.COPY, 14));
        copyButton.setToolTipText("Copy IP Address");
        copyButton.setFocusPainted(false);
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setBorderPainted(false);
        copyButton.setContentAreaFilled(false);
        
        // Add action listener to the copy button
        copyButton.addActionListener(e -> copyIPAddressToClipboard());
        
        // Add components to the panel
        add(titleLabel);
        add(ipAddressLabel);
        add(copyButton);
        
        // Set preferred size for the panel
        setPreferredSize(new Dimension(350, 40));
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ipAddressLabel.setForeground(new Color(0, 128, 0));
                
                // Reset color after a delay
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ipAddressLabel.setForeground(Color.BLACK);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
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
    public String getIPAddress() {
        return this.ipAddress;
    }
}