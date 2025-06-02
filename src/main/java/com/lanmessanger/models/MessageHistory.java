package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;
import java.util.*;

public class MessageHistory {

    /** Map of userId to list of messages with that user */
    private Map<Integer, List<Message>> userMessages;

    /** Map to store last message per user (optional, for efficiency) */
    private Map<Integer, Message> lastMessageMap;

    public MessageHistory() {
        userMessages = new HashMap<>();
        lastMessageMap = new HashMap<>();
    }

    /**
     * Add a new message to the correct user's chat history.
     */
    public void addMessage(Message message) {
        int userId = message.getSenderId();
        userMessages.putIfAbsent(userId, new ArrayList<>());
        userMessages.get(userId).add(message);
        lastMessageMap.put(userId, message);  // update last message
    }

    /**
     * Get chat history with a specific user in chronological order.
     */
    public List<Message> getMessagesWithUser(int userId) {
        return userMessages.getOrDefault(userId, Collections.emptyList());
    }

    /**
     * Get last message with a user.
     */
    public Message getLastMessage(int userId) {
        return lastMessageMap.get(userId);
    }

    /**
     * Get timestamp of last message with a user.
     */
    public LocalDateTime getLastMessageTime(int userId) {
        Message last = lastMessageMap.get(userId);
        return (last != null) ? last.getTimestamp() : null;
    }

    /**
     * Get all users you have messaged with.
     */
    public Set<Integer> getAllUserIds() {
        return userMessages.keySet();
    }

    /**
     * Get full conversation map (useful for UI cache/state).
     */
    public Map<Integer, List<Message>> getAllConversations() {
        return userMessages;
    }
}
