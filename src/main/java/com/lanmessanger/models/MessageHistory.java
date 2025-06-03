package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;
import java.util.*;

import javax.swing.SwingUtilities;

import main.java.com.lanmessanger.ui.state.StateManager;

public class MessageHistory {

    /** Map of userId to list of messages with that user */
    private Map<String, List<Message>> userMessages;

    /** Map to store last message per user (optional, for efficiency) */
    private Map<String, Message> lastMessageMap;

    private List<StateManager> subscribedComponents; 

    
    public MessageHistory() {
        userMessages = new HashMap<>();
        lastMessageMap = new HashMap<>();
        this.subscribedComponents = new ArrayList<StateManager>();
    }

    public void addSubscribedComponent(StateManager stateManager) {
        subscribedComponents.add(stateManager);
    }
    public void removeSubscribedComponent(StateManager stateManager) {
        subscribedComponents.remove(stateManager);
    }

    private void updateState() {
        // Ensure state updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            for (StateManager subscribedComponent : subscribedComponents) {
                try {
                    subscribedComponent.onStateChange();
                } catch (Exception e) {
                    System.err.println("Error updating component state: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Add a new message to the correct user's chat history.
     */
    public void addMessage(Message message) {
        String userId = message.getSenderIp();
        userMessages.putIfAbsent(userId, new ArrayList<>());
        userMessages.get(userId).add(message);
        lastMessageMap.put(userId, message);  // update last message
        updateState();
    }

    /**
     * Get chat history with a specific user in chronological order.
     */
    public List<Message> getMessagesWithUser(String userId) {
        return userMessages.getOrDefault(userId, Collections.emptyList());
    }

    
    /**
     * Get last message with a user.
     */
    public Message getLastMessage(String userId) {
        return lastMessageMap.get(userId);
    }

    /**
     * Get timestamp of last message with a user.
     */
    public LocalDateTime getLastMessageTime(String userId) {
        Message last = lastMessageMap.get(userId);
        return (last != null) ? last.getTimestamp() : null;
    }

    /**
     * Get all users you have messaged with.
     */
    public Set<String> getAllUserIds() {
        return userMessages.keySet();
    }

    /**
     * Get full conversation map (useful for UI cache/state).
     */
    public Map<String, List<Message>> getAllConversations() {
        return userMessages;
    }
}
