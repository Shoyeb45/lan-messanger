package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Message class represents a single message for the user
 * @author Shoyeb Ansari
 */
public class Message {

    /** Sender's ip */
    private String senderIp;               
    /** Message content */
    private String content;
    /** Time when message was sent */
    private LocalDateTime timestamp;    
    /** Flag value to check if the message was read or not */
    private boolean isMessageSeen = false;
    /** Message from the owner */
    private boolean isFromCurrentUser = false;

    /**
     * Default constructor for Message class
     */
    public Message() {
        timestamp = LocalDateTime.now();
    }

    
    /**
     * Constructor to initialise Message class
     * @param senderId sender's id
     * @param content  Message content that was sent
     */
    public Message(String senderIp, String content, boolean isFromCurrentUser) {
        this.senderIp = senderIp;
        this.content = content;
        this.isFromCurrentUser = isFromCurrentUser;
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
    public String getSenderIp() {
        return senderIp;
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
     * Sets the IP of the message sender
     * @param senderIp the IP of the sender to set
     */
    public void setSendersId(String senderIp) {
        this.senderIp = senderIp;
    }

    /**
     * 
     * @return boolean value indicating if the message is seen or not
     */
    public boolean getIsMessageSeen() {
        return isMessageSeen;
    }

    
    /**
     * Mark this message as seen message
     */
    public void markAsMessageSeent() {
        isMessageSeen = true; 
    }

    public boolean isFromCurrentUser() {
        return isFromCurrentUser;
    }
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}