package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;

/**
 * Message class represents a single message for the user
 * @author Shoyeb Ansari
 */
public class Message {

    /** Sender's id */
    private int senderId;               
    /** Message content */
    private String content;
    /** Time when message was sent */
    private LocalDateTime timestamp;    
    
    /**
     * Default constructor for Message class
     */
    public Message() {}

    
    /**
     * Constructor to initialise Message class
     * @param senderId sender's id
     * @param content  Message content that was sent
     */
    public Message(int senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        timestamp = LocalDateTime.now();
    }    
    
    /**
     * Gets the content of the message
     * @return the message content as a String
     */
    public String getContent() {
        return content;
    }
      
    /**
     * Gets the ID of the message sender
     * @return the sender's ID as an integer
     */
    public int getSenderId() {
        return senderId;
    }   

     /**
     * Gets the timestamp when the message was sent
     * @return the message timestamp as LocalDateTime
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }   

    /**
     * Sets the content of the message
     * @param content the message content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the ID of the message sender
     * @param senderId the ID of the sender to set
     */
    public void setSendersId(int senderId) {
        this.senderId = senderId;
    }

}