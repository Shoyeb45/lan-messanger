package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

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
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);

        // Create message content panel
        JPanel messageContent = createMessageContent();
        
        // Create wrapper for alignment
        JPanel wrapper = new JPanel(new FlowLayout(
            message.isFromCurrentUser() ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(ColorPalette.BACKGROUND);
        wrapper.add(messageContent);

        add(wrapper, BorderLayout.CENTER);
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
        JTextArea textArea = new JTextArea(message.getText());
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

        return panel;
    }
}
