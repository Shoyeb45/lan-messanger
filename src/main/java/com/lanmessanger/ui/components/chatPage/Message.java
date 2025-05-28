package main.java.com.lanmessanger.ui.components.chatPage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Message data class
 */
public class Message {
    private String text;
    private boolean isFromCurrentUser;
    private LocalTime timestamp;

    public Message(String text, boolean isFromCurrentUser, LocalTime timestamp) {
        this.text = text;
        this.isFromCurrentUser = isFromCurrentUser;
        this.timestamp = timestamp;
    }

    public String getText() { return text; }
    public boolean isFromCurrentUser() { return isFromCurrentUser; }
    public LocalTime getTimestamp() { return timestamp; }
    
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
