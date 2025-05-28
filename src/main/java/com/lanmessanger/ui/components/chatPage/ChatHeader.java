package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Header component showing the selected user
 */
public class ChatHeader extends JPanel {
    private JLabel userNameLabel;
    private JLabel statusLabel;

    public ChatHeader(String userName) {
        setBackground(ColorPalette.PANEL_BACKGROUND);
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setLayout(new BorderLayout());

        initializeComponents(userName);
    }

    private void initializeComponents(String userName) {
        // User info panel
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setBackground(ColorPalette.PANEL_BACKGROUND);

        userNameLabel = new JLabel(userName);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userNameLabel.setForeground(ColorPalette.TEXT);

        statusLabel = new JLabel("Online");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(ColorPalette.SECONDARY_TEXT);

        userInfoPanel.add(userNameLabel, BorderLayout.NORTH);
        userInfoPanel.add(statusLabel, BorderLayout.SOUTH);

        // Profile icon
        JLabel profileIcon = new JLabel();
        FontIcon icon = FontIcon.of(FontAwesome.USER_CIRCLE, 32);
        icon.setIconColor(ColorPalette.PRIMARY);
        profileIcon.setIcon(icon);

        add(profileIcon, BorderLayout.WEST);
        add(Box.createHorizontalStrut(12), BorderLayout.CENTER);
        add(userInfoPanel, BorderLayout.CENTER);

        // Add a subtle border at the bottom
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.SECONDARY_TEXT.brighter()),
            new EmptyBorder(15, 20, 15, 20)
        ));
    }

    public void setUserName(String userName) {
        userNameLabel.setText(userName);
    }
}
