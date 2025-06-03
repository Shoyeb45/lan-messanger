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
        this.parentScreen = parentScreen;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
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
    }

    private void setupLayout() {
        setLayout(new BorderLayout(12, 0));
        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    /** Method to add the events of clicking the button and pressing the enter key */
    private void setupEventHandlers() {
        // Send button action
        sendButton.addActionListener(e -> sendMessage());

        // Enter key action
        messageField.addActionListener(e -> sendMessage());
    }

    /** 
     * Method which will add the message to UI and update the state of the messge history. </br>
     * And it will actually send the message using {@code Main.server}
     * @see MessageHistory
     * @see StateManager
     * @see States
     * */
    private void sendMessage() {
        String messageText = messageField.getText();
        System.out.println("[DEBUG] Before send - messageText: '" + messageText + "'");
        System.out.println("[DEBUG] Before send - field enabled: " + messageField.isEnabled());
        System.out.println("[DEBUG] Before send - showing placeholder: " + messageField.isShowingPlaceholder());
        System.out.println("[DEBUG] Before send - has focus: " + messageField.hasFocus());

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
                    // This runs in background thread
                    Main.server.sendMessage(messageText, ip);
                    return null;
                }

                @Override
                protected void done() {
                    // This runs on EDT when background task completes
                    try {
                        get(); // Check if any exception occurred
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle error - maybe show error message to user
                    } finally {
                        // Re-enable UI components
                        sendButton.setEnabled(true);
                        messageField.setEnabled(true);
                        messageField.requestFocus();
                    }
                }
            }.execute();
        }
    }
}