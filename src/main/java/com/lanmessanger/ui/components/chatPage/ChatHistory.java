package main.java.com.lanmessanger.ui.components.chatPage;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.ui.components.ModernScrollBarUI;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Chat history component with modern message bubbles
 */
public class ChatHistory extends JPanel {
    private JPanel messagesPanel;
    private JScrollPane scrollPane;

    public ChatHistory() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        setBackground(ColorPalette.BACKGROUND);

        // Messages container
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(ColorPalette.BACKGROUND);
        messagesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Scroll pane with modern styling
        scrollPane = new JScrollPane(messagesPanel);
        scrollPane.setBackground(ColorPalette.BACKGROUND);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Style the scrollbar
        scrollPane.getVerticalScrollBar().setBackground(ColorPalette.BACKGROUND);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to render all messages at once with consistent spacing
    public void renderAllMessages(List<Message> messages) {
        // Clear existing messages
        messagesPanel.removeAll();
        
        // Add all messages with consistent spacing
        for (int i = 0; i < messages.size(); i++) {
            MessageBubble bubble = new MessageBubble(messages.get(i));
            
            // Set alignment for the bubble
            bubble.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            
            // Add spacing before message (except for first message)
            if (i > 0) {
                messagesPanel.add(Box.createVerticalStrut(8));
            }
            
            messagesPanel.add(bubble);
        }
        
        // Add glue at the end to push messages to top
        messagesPanel.add(Box.createVerticalGlue());
        
        // Update UI
        messagesPanel.revalidate();
        messagesPanel.repaint();
        
        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    // Keep this method for adding individual messages (like new incoming messages)
    public void renderMessage(Message message) {
        MessageBubble bubble = new MessageBubble(message);
        bubble.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        // Add spacing between messages
        if (messagesPanel.getComponentCount() > 0) {
            // Remove the glue if it exists (it's always the last component)
            int componentCount = messagesPanel.getComponentCount();
            if (componentCount > 0 && messagesPanel.getComponent(componentCount - 1) instanceof Box.Filler) {
                messagesPanel.remove(componentCount - 1);
            }
            messagesPanel.add(Box.createVerticalStrut(8));
        }
        
        messagesPanel.add(bubble);
        // Add glue at the end
        messagesPanel.add(Box.createVerticalGlue());
        
        messagesPanel.revalidate();
        messagesPanel.repaint();

        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    public void clearMessages() {
        messagesPanel.removeAll();
        messagesPanel.revalidate();
        messagesPanel.repaint();
    }

}