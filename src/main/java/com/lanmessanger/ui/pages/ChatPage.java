package main.java.com.lanmessanger.ui.pages;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import main.java.com.lanmessanger.ui.components.chatPage.ChatList;
import main.java.com.lanmessanger.ui.components.chatPage.ChatScreen;

public class ChatPage extends JPanel {
    private ChatList chatList;
    private ChatScreen chatScreen;

    public ChatPage() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        chatList = new ChatList();
        chatList.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        chatScreen = new ChatScreen();

        add(chatList);
        add(chatScreen);
    }
}
