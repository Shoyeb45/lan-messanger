package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Individual message bubble component
 * @author Shoyeb Ansari
 */
class MessageBubble extends JPanel {
    /** Message to be displayed */
    private Message message;

    public MessageBubble(Message message) {
        this.message = message;
        initializeComponents();
    }

    /**
     * Method to initialise components
     */
    private void initializeComponents() {
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

    /**
     * Method to create message content panel 
     * @return Message panel
     */
    private JPanel createMessageContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
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
        
        // Simple HTML approach for reliable text display
        String content = message.getContent()
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\n", "<br>");
        
        String htmlContent = String.format(
            "<html><body style='width: 270px; font-family: Segoe UI; font-size: 12px; margin: 0; padding: 0;'>%s</body></html>", 
            content
        );
        
        JLabel textArea = new JLabel(htmlContent);
        textArea.setForeground(textColor);
        textArea.setOpaque(false);
        textArea.setVerticalAlignment(SwingConstants.TOP);
        
        // Time label
        JLabel timeLabel = new JLabel(message.getFormattedTime());
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(message.isFromCurrentUser() ? 
            ColorPalette.PANEL_BACKGROUND.darker() : ColorPalette.SECONDARY_TEXT);
        timeLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        
        // Set alignment
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Constrain the panel width
        int maxWidth = 320; // 300 + some padding
        panel.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));
        
        panel.add(textArea);
        panel.add(timeLabel);
        
        return panel;
    }
    
}