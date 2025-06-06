package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.app.Main;
import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.components.RoundedBorder;
import main.java.com.lanmessanger.ui.state.State;
import main.java.com.lanmessanger.ui.utils.ColorPalette;
import main.java.com.lanmessanger.models.MessageHistory;
import main.java.com.lanmessanger.ui.state.StateManager;

/**
 * Send message box with Text Field Input and Button 
 */
class SendMessageBox extends JPanel {
    /** Message Field */
    private SendMessageTextField messageField;
    /** Button to perform the sending message action */
    private ModernButton sendButton;
    /** reference to parent Screen  */
    private ChatScreen parentScreen;

    public SendMessageBox(ChatScreen parentScreen) {
        try {
            this.parentScreen = parentScreen;
            initializeComponents();
            setupLayout();
            setupEventHandlers();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize SendMessageBox\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        try {
            setBackground(ColorPalette.PANEL_BACKGROUND);
            setBorder(new EmptyBorder(10, 15, 10, 15));
    
            // Message input field with modern styling
            messageField = new SendMessageTextField("Type your message here...");
            messageField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageField.setBackground(ColorPalette.BACKGROUND);
            messageField.setForeground(ColorPalette.TEXT);
            messageField.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(20, ColorPalette.BACKGROUND),
                    new EmptyBorder(12, 16, 12, 16)));
            messageField.setPlaceholderColor(ColorPalette.SECONDARY_TEXT);
    
            // Send button with modern styling
            sendButton = new ModernButton("", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
            FontIcon icon = FontIcon.of(FontAwesome.SEND, 16);
            icon.setIconColor(ColorPalette.PANEL_BACKGROUND);
            sendButton.setIcon(icon);
            sendButton.setPreferredSize(new Dimension(50, 44));
            sendButton.setBorder(new RoundedBorder(22, ColorPalette.PRIMARY));
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize components\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupLayout() {
        try {
            setLayout(new BorderLayout(12, 0));
            add(messageField, BorderLayout.CENTER);
            add(sendButton, BorderLayout.EAST);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set up layout\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to add the events of clicking the button and pressing the enter key */
    private void setupEventHandlers() {
        try {
            // Send button action
            sendButton.addActionListener(e -> {
                try {
                    sendMessage();
                } catch (Exception ex) {
                    System.out.println("[ERROR] Failed to handle send button action\nError Message: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            // Enter key action
            messageField.addActionListener(e -> {
                try {
                    sendMessage();
                } catch (Exception ex) {
                    System.out.println("[ERROR] Failed to handle enter key action\nError Message: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set up event handlers\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** 
     * Method which will add the message to UI and update the state of the messge history. </br>
     * And it will actually send the message using {@code Main.server}
     * @see MessageHistory
     * @see StateManager
     * @see States
     * */
    private void sendMessage() {
        try {
            String messageText = messageField.getText();

            if (!messageText.isBlank()) {
                String ip = parentScreen.getIpAddress();
                System.out.println("[INFO] Message : " + messageText);

                // Clear the field immediately for better UX
                messageField.setText("");

                // Create message and add to history immediately
                Message message = new Message(ip, messageText, true);
                State.messageHistory.addMessage(message);

                // Disable send button to prevent multiple sends
                sendButton.setEnabled(false);
                messageField.setEnabled(false);

                // Use SwingWorker for background network operation
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            // This runs in background thread
                            Main.server.sendMessage(messageText, ip);
                        } catch (Exception ex) {
                            System.out.println("[ERROR] Failed to send message in background\nError Message: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            // This runs on EDT when background task completes
                            get(); // Check if any exception occurred
                        } catch (Exception e) {
                            System.out.println("[ERROR] Failed to complete message sending\nError Message: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            // Re-enable UI components
                            sendButton.setEnabled(true);
                            messageField.setEnabled(true);
                            messageField.requestFocus();
                        }
                    }
                }.execute();
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to send message\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}