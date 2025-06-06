package main.java.com.lanmessanger.ui.components.chatPage;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import main.java.com.lanmessanger.ui.state.State;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import main.java.com.lanmessanger.app.Main;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.models.User;
import main.java.com.lanmessanger.network.client.Client;
import main.java.com.lanmessanger.ui.components.ChatProfile;
import main.java.com.lanmessanger.ui.components.ModernScrollBarUI;
import main.java.com.lanmessanger.ui.pages.ChatPage;
import main.java.com.lanmessanger.ui.state.StateManager;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class ChatList extends JPanel implements StateManager {

    /** Container where all the friends of the user will be displayed */
    private JPanel devicesContainer;
    /** Scroll pane to show the scroll bar */
    private JScrollPane scrollPane;
    /** Reference to parent */
    private ChatPage parentChatPage; 

    private Client clientSocket = new Client();
    // private Friend friends;

    @Override
    public void onStateChange() {
        try {
            SwingUtilities.invokeLater(() -> {
                renderFriends();
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to handle state change\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ChatList() {
        try {
            State.friendsList.addSubscribedComponent(this);
            State.messageHistory.addSubscribedComponent(this);

            initializeComponents();
            add(scrollPane, BorderLayout.CENTER);
            renderFriends();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize ChatList\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeComponents() {
        try {
            // Main container setup
            this.setBackground(ColorPalette.BACKGROUND);
            this.setLayout(new BorderLayout(0, 15));
            
            // Devices container
            devicesContainer = new JPanel();
            devicesContainer.setLayout(new BoxLayout(devicesContainer, BoxLayout.Y_AXIS));
            devicesContainer.setBackground(ColorPalette.PANEL_BACKGROUND);
            devicesContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Scroll pane
            scrollPane = new JScrollPane(devicesContainer);
            scrollPane.setBackground(ColorPalette.BACKGROUND);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    
            // Style the scrollbar
            scrollPane.getVerticalScrollBar().setBackground(ColorPalette.BACKGROUND);
            scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize components\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void renderFriends() {
        try {
            // Clear existing components
            devicesContainer.removeAll();
            HashSet<User> friends = State.friendsList.getAllFriends();

            for (User friend: friends) {
                Message message = State.messageHistory.getLastMessage(friend.getIp());
                String lastMessage = "", lastTime = "";
                
                if (message != null) {
                    lastMessage = message.getContent();
                    lastTime = message.getFormattedTime();
                    if (message.isFromCurrentUser()) {
                        lastMessage = "You: " + lastMessage;
                    }
                }
                addProfile(friend.getName(), lastMessage, lastTime, friend.getIp());
            }

            // Refresh the UI
            devicesContainer.revalidate();
            devicesContainer.repaint();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to render friends\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addProfile(String userName, String lastMessage, String messageTime, String ip) {
        try {
            JPanel deviceInfoPanel = createDeviceInfoPanel(userName, lastMessage, messageTime);
            ChatProfile chatProfile = new ChatProfile(10, deviceInfoPanel, ColorPalette.BACKGROUND);
            chatProfile.setBackground(ColorPalette.BACKGROUND);
            
            // Make the entire panel clickable
            deviceInfoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        onChatItemSelected(userName, ip);
                    } catch (Exception ex) {
                        System.out.println("[ERROR] Failed to handle chat item click\nError Message: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    try {
                        // Optional: Add hover effect
                        deviceInfoPanel.setBackground(ColorPalette.PANEL_BACKGROUND.brighter());
                        chatProfile.repaint();
                    } catch (Exception ex) {
                        System.out.println("[ERROR] Failed to handle mouse enter\nError Message: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    try {
                        // Optional: Remove hover effect
                        deviceInfoPanel.setBackground(ColorPalette.PANEL_BACKGROUND);
                        chatProfile.repaint();
                    } catch (Exception ex) {
                        System.out.println("[ERROR] Failed to handle mouse exit\nError Message: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });
            
            // Add spacing between devices
            if (devicesContainer.getComponentCount() > 0) {
                devicesContainer.add(Box.createVerticalStrut(8));
            }
            
            devicesContainer.add(chatProfile);
            devicesContainer.revalidate();
            devicesContainer.repaint();

            Thread networkThread = new Thread(() -> {
                try {
                    clientSocket.setRemoteIp(ip);
                    if (clientSocket.connect()) {
                        Main.server.addClient(clientSocket.getClientSocket());
                    }
                } catch (Exception e) {
                    System.out.println("[ERROR] Failed to connect to friend while adding it to the ChatList\nError Message: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            networkThread.setName("NetworkConnect-" + ip);
            networkThread.setDaemon(true); // Don't prevent app shutdown
            networkThread.start();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to add profile\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel createDeviceInfoPanel(String userName, String lastMessage, String messageTime) {
        try {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(ColorPalette.PANEL_BACKGROUND);
            
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Device name
            JLabel nameLabel = new JLabel(userName);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            nameLabel.setForeground(ColorPalette.TEXT);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 0, 2, 0);
            panel.add(nameLabel, gbc);
            
            // IP Address
            JLabel ipLabel = new JLabel(lastMessage);
            ipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            ipLabel.setForeground(ColorPalette.SECONDARY_TEXT);
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(ipLabel, gbc);
            
            // Add button
            JLabel lastMessageTime = new JLabel(messageTime);
            lastMessageTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            // Add functionality to add the user into our local database
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 2;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 1.0;
            gbc.insets = new Insets(0, 20, 0, 0);
            panel.add(lastMessageTime, gbc);
            
            return panel;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to create device info panel\nError Message: " + e.getMessage());
            e.printStackTrace();
            return new JPanel();
        }
    }

    public void setParentChatPage(ChatPage parent) {
        try {
            this.parentChatPage = parent;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set parent chat page\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void onChatItemSelected(String userName, String ip) {
        try {
            // Your existing logic for selecting a chat...
            
            // Notify parent to switch to chat screen in mobile mode
            if (parentChatPage != null) {
                parentChatPage.onChatSelected(userName, ip);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to handle chat item selection\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}