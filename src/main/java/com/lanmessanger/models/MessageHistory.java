package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.SwingUtilities;
import main.java.com.lanmessanger.ui.state.StateManager;

public class MessageHistory {

    /** Map of userId to list of messages with that user */
    private Map<String, List<Message>> userMessages;

    /** Map to store last message per user (optional, for efficiency) */
    private Map<String, Message> lastMessageMap;

    /** List of subscribe component for this state */
    private List<StateManager> subscribedComponents; 

    
    public MessageHistory() {
        userMessages = new HashMap<>();
        lastMessageMap = new HashMap<>();
        this.subscribedComponents = new ArrayList<StateManager>();
    }

    public void addSubscribedComponent(StateManager stateManager) {
        if (stateManager == null) {
            System.out.println("[ERROR IN MessageHistory] State manager is null, please add valid state manager for subscription");
            return;
        }
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
        try {
            String userId = message.getSenderIp();
            userMessages.putIfAbsent(userId, new ArrayList<>());
            userMessages.get(userId).add(message);
            lastMessageMap.put(userId, message);  // update last message
            updateState();
        } catch (Exception e) {
            System.out.println("[ERROR] Faled to add message\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get chat history with a specific user in chronological order.
     */
    public List<Message> getMessagesWithUser(String userId) {
        return userMessages.getOrDefault(userId, Collections.emptyList());
    }

    
    /**
     * Get last message with a user.
     * @param userIp IP address of the user 
     */
    public Message getLastMessage(String userIp) {
        try {
            return lastMessageMap.get(userIp);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get the Last Message for IP Address - " + userIp + "\nError Message: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get timestamp of last message with a user.
     * @param userIp IP address of the user 
     */
    public LocalDateTime getLastMessageTime(String userIp) {
        try {
            Message last = lastMessageMap.get(userIp);
            return (last != null) ? last.getTimestamp() : null;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get the time of the Last Message for IP Address - " + userIp + "\nError Message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get all users you have messaged with.
     * @return all users that user have chatted
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


    /**
     * Method to set the User messsage
     * @param userMessages new Map to be set
     */
    public void setUserMessages(Map<String, List<Message>> userMessages) {
        if (userMessages == null) {
            return;
        }
        this.userMessages = userMessages;
    }
}
