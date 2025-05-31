package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import main.java.com.lanmessanger.ui.pages.ChatPage;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Modern component to show the chat between users
 */
public class ChatScreen extends JPanel {
    private String selectedUser = "Select a chat";
    private ChatHistory chatHistory;
    private SendMessageBox sendMessageBox;
    private ChatHeader chatHeader;
    private List<Message> messages;
    /** Reference to parent */
    private ChatPage parentChatPage;

    public ChatScreen() {
        this.messages = new ArrayList<>();
        initializeComponents();
        setupLayout();
        addSampleMessages(); // For demonstration
    }

    private void initializeComponents() {
        setBackground(ColorPalette.BACKGROUND);
        
        chatHeader = new ChatHeader(selectedUser);
        chatHeader.getBackButton().addActionListener(e -> goToChatListPage()); 

        chatHistory = new ChatHistory(messages);
        sendMessageBox = new SendMessageBox(this);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(chatHeader, BorderLayout.NORTH);
        add(chatHistory, BorderLayout.CENTER);
        add(sendMessageBox, BorderLayout.SOUTH);
    }

    private void addSampleMessages() {
        // Add some sample messages for demonstration
        addMessage("Hello there!", true);
        addMessage("Hi! How are you doing?", false);
        addMessage("I'm doing great, thanks for asking!", true);
        addMessage("That's wonderful to hear! What have you been up to lately?", false);
        for (int i = 0; i < 3; i++)
        addMessage("Just working on some projects. How about you?", true);
        for (int i = 0; i < 3; i++)
        addMessage(" How about you?", false);
    }

    private void sendMessage(String messageText) {
        if (!messageText.trim().isEmpty()) {
            addMessage(messageText, true);
        }
    }

    public void addMessage(String text, boolean isFromCurrentUser) {
        Message message = new Message(text, isFromCurrentUser, LocalTime.now());
        messages.add(message);
        chatHistory.addMessage(message);
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

    // Method to set parent reference
    public void setParentChatPage(ChatPage parent) {
        this.parentChatPage = parent;
    }

    private void goToChatListPage() {
        parentChatPage.showChatList();  
    }
}
