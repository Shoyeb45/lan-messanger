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
import main.java.com.lanmessanger.ui.components.ModernScrollBarUI;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Chat history component with modern message bubbles
 */
public class ChatHistory extends JPanel {
    private JPanel messagesPanel;
    private JScrollPane scrollPane;
    private List<Message> messages;

    public ChatHistory(List<Message> messages) {
        this.messages = messages;
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

    public void addMessage(Message message) {
        MessageBubble bubble = new MessageBubble(message);
        
        // Add spacing between messages
        if (messagesPanel.getComponentCount() > 0) {
            messagesPanel.add(Box.createVerticalStrut(8));
        }
        
        messagesPanel.add(bubble);
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