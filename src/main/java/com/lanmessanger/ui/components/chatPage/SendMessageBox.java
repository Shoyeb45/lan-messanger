package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.components.RoundedBorder;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Modern send message box with improved styling
 */
class SendMessageBox extends JPanel {
    private SendMessageTextField messageField;
    private ModernButton sendButton;
    private ChatScreen parentScreen;

    public SendMessageBox(ChatScreen parentScreen) {
        this.parentScreen = parentScreen;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setBackground(ColorPalette.PANEL_BACKGROUND);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        // Message input field with modern styling
        messageField = new SendMessageTextField("Type your message here...");
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageField.setBackground(ColorPalette.BACKGROUND);
        messageField.setForeground(ColorPalette.TEXT);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(20, ColorPalette.BACKGROUND),
            new EmptyBorder(12, 16, 12, 16)
        ));
        messageField.setPlaceholderColor(ColorPalette.SECONDARY_TEXT);

        // Send button with modern styling
        sendButton = new ModernButton("", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
        FontIcon icon = FontIcon.of(FontAwesome.SEND, 16);
        icon.setIconColor(ColorPalette.PANEL_BACKGROUND);
        sendButton.setIcon(icon);
        sendButton.setPreferredSize(new Dimension(50, 44));
        sendButton.setBorder(new RoundedBorder(22, ColorPalette.PRIMARY));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(12, 0));
        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    private void setupEventHandlers() {
        // Send button action
        sendButton.addActionListener(e -> sendMessage());

        // Enter key action
        messageField.addActionListener(e -> sendMessage());
    }

    private void sendMessage() {
        String messageText = messageField.getText();
        if (!messageText.trim().isEmpty()) {

            System.out.println(messageText);
            messageField.setText(""); // Clear the field
        }
    }
}

