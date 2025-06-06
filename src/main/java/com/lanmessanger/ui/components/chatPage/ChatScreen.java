package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.ui.pages.ChatPage;
import main.java.com.lanmessanger.ui.state.State;
import main.java.com.lanmessanger.ui.state.StateManager;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Modern component to show the chat between users
 */
public class ChatScreen extends JPanel implements StateManager {
    /** Name of the user in top */
    private String selectedUser = "Select a chat";
    /** IP Address of the user */
    private String ipAddress;
    /** Chat history component to show the conversation */
    private ChatHistory chatHistory;
    /** Send message box which contains both text area and send  button */
    private SendMessageBox sendMessageBox;
    /** Header component which contains the name and the status of the user */
    private ChatHeader chatHeader;
    /** List of the messages that needs to be displayed */
    private List<Message> messages;
    /** Reference to parent */
    private ChatPage parentChatPage;

    public ChatScreen() {
        try {
            State.messageHistory.addSubscribedComponent(this);
            this.messages = new ArrayList<>();
            initializeComponents();
            setupLayout();
            renderAllMessages();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize ChatScreen\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        try {
            setBackground(ColorPalette.BACKGROUND);
            
            chatHeader = new ChatHeader(selectedUser, ipAddress);
            chatHeader.getBackButton().addActionListener(e -> {
                try {
                    goToChatListPage();
                } catch (Exception ex) {
                    System.out.println("[ERROR] Failed to handle back button action\nError Message: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }); 
    
            chatHistory = new ChatHistory();
            sendMessageBox = new SendMessageBox(this);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize components\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupLayout() {
        try {
            setLayout(new BorderLayout());
            add(chatHeader, BorderLayout.NORTH);
            add(chatHistory, BorderLayout.CENTER);
            add(sendMessageBox, BorderLayout.SOUTH);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to setup layout\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void renderAllMessages() {
        try {
            // Clear existing messages first
            chatHistory.clearMessages();
            
            // Render all messages at once to ensure consistent spacing
            chatHistory.renderAllMessages(messages);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to render all messages\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addMessage(String text, boolean isFromCurrentUser) {
        try {
            Message message = new Message("", text, isFromCurrentUser);
            messages.add(message);
            // Only render single message when adding new ones
            chatHistory.renderMessage(message);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to add message\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setMessages(List<Message> messages) {
        try {
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
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set messages\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setSelectedUser(String userName) {
        try {
            this.selectedUser = userName;
            chatHeader.setUserName(userName);
            // Clear messages when switching users
            messages.clear();
            chatHistory.clearMessages();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set selected user\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setMobileMode(boolean isMobile) {
        try {
            chatHeader.setMobileMode(isMobile);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set mobile mode\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to set parent reference  
     * @param parent parent chat page
     * */
    public void setParentChatPage(ChatPage parent) {
        try {
            this.parentChatPage = parent;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set parent chat page\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Go to chat list page from parent Chat Page
     */
    private void goToChatListPage() {
        try {
            parentChatPage.showChatList();  
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to go to chat list page\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return IP Address of the selected user
     */
    public String getIpAddress() {
        try {
            return ipAddress;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get IP address\nError Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to set the ip addres
     * @param ipAddress IP Address
     */
    public void setIpAddress(String ipAddress) {
        try {
            this.ipAddress = ipAddress;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set IP address\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onStateChange() {
        try {
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
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to handle state change\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setUserStatus(boolean isOnline) {
        try {
            chatHeader.setStatus(isOnline);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set user status\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
