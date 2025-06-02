package main.java.com.lanmessanger.ui.state;

import main.java.com.lanmessanger.models.Friend;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.models.MessageHistory;

public class State {
    
    // friends list
    public static Friend friendsList = new Friend();

    // Message history
    public static MessageHistory messageHistory = new MessageHistory();
    static {
        messageHistory.addMessage(new Message("127.0.0.1", "Hii", true));
        messageHistory.addMessage(new Message("127.0.0.1", "Hello", true));
        messageHistory.addMessage(new Message("127.0.0.1", "How are you?", false));
        messageHistory.addMessage(new Message("127.0.0.1", "I am fine", true));
    }
}
