package main.java.com.lanmessanger.models;

import java.time.LocalDateTime;
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
     * @param senderId sender's IP Addres
     * @param content  Message content that was sent
     */
    public Message(String senderIp, String content, boolean isFromCurrentUser) {
        this.senderIp = senderIp;
        this.content = content;
        this.isFromCurrentUser = isFromCurrentUser;

        if (this.isFromCurrentUser) {
            isMessageSeen = true;
        }
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
     * Gets the IP Address of the message sender
     * @return the sender's IP address
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

    /**
     * Get if the message was from the current user
     * @return boolean value indicating if the message was send by the current user or it's from someone different person
     */
    public boolean isFromCurrentUser() {
        return isFromCurrentUser;
    }
    
    /**
     * @return Formatted time of the {@code Message}
     */
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}