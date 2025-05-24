package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/*
 * Component to show the chat between users
 */
public class ChatScreen extends JPanel {
    // ArrayList<> chatHistroy;
    private String userName;
    private SendMessageBox sendMessageBox;

    public ChatScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sendMessageBox = new SendMessageBox();
        add(sendMessageBox);
    }
}


class ChatHistory extends JPanel {

}

class SendMessageBox extends JPanel {
    private SendMessageTextField messageField;
    private ModernButton sendButton;

    public SendMessageBox() {
        messageField = new SendMessageTextField("Type your message here...");
        sendButton = new ModernButton("", ColorPalette.PRIMARY, ColorPalette.SECONDARY);  
        FontIcon icon = FontIcon.of(FontAwesome.SEND, 15);
        icon.setIconColor(ColorPalette.PANEL_BACKGROUND);
        sendButton.setIcon(icon);

        setLayout(new BorderLayout());

        add(messageField, BorderLayout.WEST);
        add(sendButton, BorderLayout.EAST);
    }
}