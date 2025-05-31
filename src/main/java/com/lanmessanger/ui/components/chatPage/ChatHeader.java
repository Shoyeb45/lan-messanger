package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.components.ChatProfile;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Header component showing the selected user
 */
public class ChatHeader extends JPanel {
    /**Label for username */
    private JLabel userNameLabel;
    /** Wrapper chat Profile component */
    private ChatProfile chatProfile;
    /** Panel for username and status */
    private JPanel userInformation;
    /** Label for status of the user, online or offline */
    private JLabel statusLabel;

    /**
     * Initialise ChatHeader class for selected user
     * @param userName : Name of the user
     */
    public ChatHeader(String userName) {
        setLayout(new BorderLayout());
        initializeComponents(userName);
    }

    /**
     * method ot initialise components
     * @param userName Name of the user
     */
    private void initializeComponents(String userName) {
        // User info panel
        userInformation = createDeviceInfoPanel(userName, false);
        chatProfile = new ChatProfile(10, userInformation, ColorPalette.PANEL_BACKGROUND);
        userInformation.setBackground(Color.white);
        add(chatProfile);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.SECONDARY_TEXT.brighter()));

    }

    /**
     * Setter method to set the username
     * @param userName Name of the user
     */
    public void setUserName(String userName) {
        userNameLabel.setText(userName);
    }

    /**
     * Setter method to set the status of the user
     * @param isOnline Boolean value indicating if the user is online or offline
     */
    public void setStatus(boolean isOnline) {
        if (isOnline) {
            statusLabel.setText("Online");
            return;
        }
        statusLabel.setText("Offline");
    }

    /**
     * Method to create device info panel
     * @param userName Name of the user
     * @param isOnline   Status of the user, true if the user is online
     * @return Info panel with adjusted layout, which have name and the status of the user 
     */
    private JPanel createDeviceInfoPanel(String userName, boolean isOnline) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Device name
        userNameLabel = new JLabel(userName);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameLabel.setForeground(ColorPalette.TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 2, 0);
        panel.add(userNameLabel, gbc);

        statusLabel = new JLabel(
            isOnline? "Online": "Offline"
        );
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(ColorPalette.SECONDARY_TEXT);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(statusLabel, gbc);

        JLabel lastMessageTime = new JLabel("");
        lastMessageTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel.add(lastMessageTime, gbc);

        return panel;
    }
}
