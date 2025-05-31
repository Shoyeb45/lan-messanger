package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.ui.components.chatPage.ChatList;
import main.java.com.lanmessanger.ui.components.chatPage.ChatScreen;

/** Page for Chat section */
public class ChatPage extends JPanel {
    /** Component to hold all the chat list */
    private ChatList chatList;
    /** Component to hold the current chatscreen */
    private ChatScreen chatScreen;
    /** Split pane for effectively showing both chat screen and chat list */
    private JSplitPane splitPane;
    private CardLayout cardLayout;
    /** Panel for mobile app */
    private JPanel mobileView;
    private boolean isMobileMode = false;
    
  
    
    public ChatPage() {
        setLayout(new BorderLayout());
        
        // Initialize components
        chatList = new ChatList();
        chatScreen = new ChatScreen();
        
        // Set parent references
        chatList.setParentChatPage(this);
        chatScreen.setParentChatPage(this);
        
        // Setup desktop view with split pane
        setupDesktopView();
        
        // Setup mobile view with card layout
        setupMobileView();
        
        // Add desktop view initially
        add(splitPane, BorderLayout.CENTER);
        
        // Listen for resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                checkAndUpdateLayout();
            }
        });
    }
    
    /**
     * Method to setup the desktop view of chat page
     */
    private void setupDesktopView() {
        // Using JSplitPane to correcty divide the chat list and screen in ratio of 40% to 60%
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(chatList);
        splitPane.setRightComponent(chatScreen);
        splitPane.setResizeWeight(0.4); // 40% for chat list
        splitPane.setDividerSize(1);
        splitPane.setContinuousLayout(true);
        
        // Set minimum sizes to prevent components from disappearing
        chatList.setMinimumSize(new Dimension(250, 0));
        chatScreen.setMinimumSize(new Dimension(300, 0));
    }
    
    /**
     * Method to set up mobile view
     */
    private void setupMobileView() {
        cardLayout = new CardLayout();
        mobileView = new JPanel(cardLayout);
        
        // Create mobile-friendly versions
        JPanel chatListPanel = createMobileChatListPanel();
        JPanel chatScreenPanel = createMobileChatScreenPanel();
        
        mobileView.add(chatListPanel, "CHAT_LIST");
        mobileView.add(chatScreenPanel, "CHAT_SCREEN");
    }
    
    /**
     * Method to create chat list panel for mobile 
     * @return Mobile compatible chat list panel
     */
    private JPanel createMobileChatListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chatList, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Method to create message panel for mobile
     * @return Mobile compatible message panel
     */
    private JPanel createMobileChatScreenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // You'll need to modify your ChatScreen to include a back button
        // For now, we'll assume ChatScreen handles its own mobile layout
        panel.add(chatScreen, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void checkAndUpdateLayout() {
        int currentWidth = getWidth();
        boolean shouldBeMobile = currentWidth < AppConfig.MEDIUM_WIDTH + 120;
        
        if (shouldBeMobile && !isMobileMode) {
            // Switch to mobile mode
            switchToMobileMode();
        } else if (!shouldBeMobile && isMobileMode) {
            // Switch to desktop mode
            switchToDesktopMode();
        }
    }
    
    private void switchToMobileMode() {
        isMobileMode = true;
        removeAll();
        
        // Remove components from split pane and add to mobile view
        splitPane.remove(chatList);
        splitPane.remove(chatScreen);
        
        // Recreate mobile panels with current components
        mobileView.removeAll();
        mobileView.add(createMobileChatListPanel(), "CHAT_LIST");
        mobileView.add(createMobileChatScreenPanel(), "CHAT_SCREEN");
        
        add(mobileView, BorderLayout.CENTER);
        
        // Show chat list by default in mobile mode
        showChatList();
        
        // Update components for mobile mode
        chatScreen.setMobileMode(true);
        
        revalidate();
        repaint();
    }
    
    /** Method to switch to dektop mode */
    private void switchToDesktopMode() {
        isMobileMode = false;
        removeAll();
        
        // Remove components from mobile view and add back to split pane
        mobileView.removeAll();
        
        splitPane.setLeftComponent(chatList);
        splitPane.setRightComponent(chatScreen);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Update components for desktop mode
        chatScreen.setMobileMode(false);
        
        revalidate();
        repaint();
    }
    
    // Public methods for navigation (call these from your ChatScreen back button)
    /**
     * To show chat list
     */
    public void showChatList() {
        if (isMobileMode) {
            cardLayout.show(mobileView, "CHAT_LIST");
        }
    }
    
    /**
     * To show chat screen
     */
    public void showChatScreen() {
        if (isMobileMode) {
            cardLayout.show(mobileView, "CHAT_SCREEN");
        }
    }
    
    /**
     * return if the current window is mobile mode or not
     * @return
     */
    public boolean isMobileMode() {
        return isMobileMode;
    }
    
    /** Method to be called when a chat is selected from ChatList  */
    public void onChatSelected(String username) {
        chatScreen.setSelectedUser(username);
        if (isMobileMode) {
            showChatScreen();
        }
    }
}