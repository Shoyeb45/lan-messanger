package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import main.java.com.lanmessanger.ui.components.ChatProfile;
import main.java.com.lanmessanger.ui.components.chatPage.ChatList;
import main.java.com.lanmessanger.ui.components.chatPage.ChatScreen;

public class ChatPage extends JPanel {
    private ChatList chatList;
    private ChatScreen chatScreen;

    public ChatPage() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        chatList = new ChatList();
        // chatList.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        chatScreen = new ChatScreen();

        add(chatList);
        add(chatScreen);
    }
}
