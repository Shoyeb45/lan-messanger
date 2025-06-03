package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.*;
import java.rmi.server.SocketSecurityException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.ui.pages.ChatPage;
import main.java.com.lanmessanger.ui.state.State;
import main.java.com.lanmessanger.ui.state.StateManager;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Modern component to show the chat between users
 */
public class ChatScreen extends JPanel implements StateManager {
    private String selectedUser = "Select a chat";
    private String ipAddress;
    private ChatHistory chatHistory;
    private SendMessageBox sendMessageBox;
    private ChatHeader chatHeader;
    private List<Message> messages;
    /** Reference to parent */
    private ChatPage parentChatPage;

    public ChatScreen() {
        State.messageHistory.addSubscribedComponent(this);
        this.messages = new ArrayList<>();
        initializeComponents();
        setupLayout();
        addSampleMessages(); // For demonstration
        renderAllMessages();
    }

    private void initializeComponents() {
        setBackground(ColorPalette.BACKGROUND);
        
        chatHeader = new ChatHeader(selectedUser);
        chatHeader.getBackButton().addActionListener(e -> goToChatListPage()); 

        chatHistory = new ChatHistory();
        sendMessageBox = new SendMessageBox(this);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(chatHeader, BorderLayout.NORTH);
        add(chatHistory, BorderLayout.CENTER);
        add(sendMessageBox, BorderLayout.SOUTH);
    }

    private void renderAllMessages() {
        // Clear existing messages first
        chatHistory.clearMessages();
        
        // Render all messages at once to ensure consistent spacing
        chatHistory.renderAllMessages(messages);
    }

    private void addSampleMessages() {
        // Add some sample messages for demonstration
        addMessage("Hello there!", true);
        addMessage("Hi! How are you doing?", false);
        addMessage("I'm doing great, thanks for asking!", true);
        addMessage("That's wonderful to hear! What have you been up to lately?", false);
        addMessage("Just working on some projects. How about you?", true);
        addMessage("Just working on some projects. How about you?", true);
        addMessage("Just working on some projects. How about you?", true);
        addMessage("Just working on some projects. How about you?", true);
        addMessage(" How about you?", false);
    }
    


    public void addMessage(String text, boolean isFromCurrentUser) {
        Message message = new Message("", text, isFromCurrentUser);
        messages.add(message);
        // Only render single message when adding new ones
        chatHistory.renderMessage(message);
    }

    public void setMessages(List<Message> messages) {
         // Clear current messages
        this.messages.clear();
        chatHistory.clearMessages();
        
        // Set new messages
        if (messages != null) {
            this.messages.addAll(messages);
        }
        
        // Render all messages at once for consistent spacing
        renderAllMessages();
        
        // Force UI update
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    public void setSelectedUser(String userName) {
        this.selectedUser = userName;
        chatHeader.setUserName(userName);
        // Clear messages when switching users
        messages.clear();
        chatHistory.clearMessages();
    }

    public void setMobileMode(boolean isMobile) {
        chatHeader.setMobileMode(isMobile);
    }

    /**
     * Method to set parent reference  
     * @param parent parent chat page
     * */
    public void setParentChatPage(ChatPage parent) {
        this.parentChatPage = parent;
    }

    /**
     * Go to chat list page from parent Chat Page
     */
    private void goToChatListPage() {
        parentChatPage.showChatList();  
    }

    /**
     * @return IP Address of the selected user
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Method to set the ip addres
     * @param ipAddress IP Address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public void onStateChange() {
        // This method now runs on EDT thanks to MessageHistory.updateState()
        if (ipAddress != null) {
            Message lastMessage = State.messageHistory.getLastMessage(ipAddress);
            if (lastMessage != null && ipAddress.equals(lastMessage.getSenderIp())) {
                chatHistory.renderMessage(lastMessage);
                // Ensure UI updates are visible
                revalidate();
                repaint();
            }
        }
    }
}
