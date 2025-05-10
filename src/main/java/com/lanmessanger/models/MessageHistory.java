package main.java.com.lanmessanger.models;

import java.util.ArrayList;

public class MessageHistory {
    
    /** ArrayList to store messages of the user with different other users */
    private ArrayList<Message> messages;

    /**
     * Constructor to initialise MessageHistory class
     */
    public MessageHistory() {
        messages = new ArrayList<Message>();
    }

    /**
     * Method to add a message to the arraylist
     * @param message Message object
     */
    public void addMessage(Message message) {
        messages.add(message);
    }


    /**
     * Method to get all the messages of the user
     * @return all messages of the user
     */
    public Message[] getAllMessages() {
        return messages.toArray(new Message[0]);
    }
}
