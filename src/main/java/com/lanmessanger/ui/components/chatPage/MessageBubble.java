package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Individual message bubble component
 */
class MessageBubble extends JPanel {
    private Message message;

    public MessageBubble(Message message) {
        this.message = message;
        initializeComponents();
    }

    private void initializeComponents() {
        // Use BoxLayout instead of BorderLayout to prevent extra spacing
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(ColorPalette.BACKGROUND);
        
        // Set maximum height to prevent excessive vertical space
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Create message content panel
        JPanel messageContent = createMessageContent();
        
        // Add alignment based on sender
        if (message.isFromCurrentUser()) {
            // Right align for current user
            add(Box.createHorizontalGlue());
            add(messageContent);
        } else {
            // Left align for other user
            add(messageContent);
            add(Box.createHorizontalGlue());
        }
    }

    private JPanel createMessageContent() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Set bubble colors based on sender
        Color bubbleColor = message.isFromCurrentUser() ? 
            ColorPalette.PRIMARY : ColorPalette.PANEL_BACKGROUND;
        Color textColor = message.isFromCurrentUser() ? 
            ColorPalette.PANEL_BACKGROUND : ColorPalette.TEXT;

        panel.setBackground(bubbleColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.black),
            new EmptyBorder(12, 16, 12, 16)
        ));

        // Message text
        JTextArea textArea = new JTextArea(message.getContent());
        textArea.setBackground(bubbleColor);
        textArea.setForeground(textColor);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);

        // Time label
        JLabel timeLabel = new JLabel(message.getFormattedTime());
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(message.isFromCurrentUser() ? 
            ColorPalette.PANEL_BACKGROUND.darker() : ColorPalette.SECONDARY_TEXT);
        timeLabel.setBorder(new EmptyBorder(4, 0, 0, 0));

        panel.add(textArea, BorderLayout.CENTER);
        panel.add(timeLabel, BorderLayout.SOUTH);

        // Set maximum width to prevent overly wide bubbles
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        // Set preferred size to help with layout calculations
        panel.setPreferredSize(new Dimension(Math.min(300, panel.getPreferredSize().width), 
                                           panel.getPreferredSize().height));

        return panel;
    }
}