package main.java.com.lanmessanger.ui.state;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import main.java.com.lanmessanger.database.DatabaseOperations;
import main.java.com.lanmessanger.models.Friend;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.models.MessageHistory;
import main.java.com.lanmessanger.models.User;

public class State {
    // friends list
    public static Friend friendsList = new Friend();
    
    // Message history
    public static MessageHistory messageHistory = new MessageHistory();
    
    static {
        DatabaseOperations.createFiles();
        
        // Load friends list, use default if null
        HashSet<User> loadedFriends = DatabaseOperations.readFriend();
        if (loadedFriends != null) {
            friendsList.setFriends(loadedFriends);
        }
        
        // Load message history, use default if null
        Map<String, List<Message>> loadedHistory = DatabaseOperations.readMessageHistory();
        if (loadedHistory != null) {
            messageHistory.setUserMessages(loadedHistory);
        }
    }
}