package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.app.Main;
import main.java.com.lanmessanger.ui.components.chatPage.ChatList;
import main.java.com.lanmessanger.ui.components.chatPage.ChatScreen;
import main.java.com.lanmessanger.ui.state.State;

/** 
 * Page for Chat section
 * @author Shoyeb Ansari
 */
public class ChatPage extends JPanel {
    /** Component to hold all the chat list */
    private ChatList chatList;
    /** Component to hold the current chatscreen */
    private ChatScreen chatScreen;
    /** Split pane for effectively showing both chat screen and chat list */
    private JSplitPane splitPane;
    /** Card Layout for dynamic rendering between the chat screen and chat list in mobile mode */
    private CardLayout cardLayout;
    /** Panel for mobile app */
    private JPanel mobileView;
    /** Main container to switch between desktop and mobile views */
    private JPanel mainContainer;
    /** Layout on the page will be seen */  
    private CardLayout mainLayout;
    /** Flag to indicate if the current mode is mobile mode or not */
    private boolean isMobileMode = false;
    
    public ChatPage() {
        try {
            setLayout(new BorderLayout());
            // Initialize components first
            initializeComponents();
            
            // Setup layouts
            setupLayouts();
            
            // Set initial state
            setInitialState();
            
            // Add resize listener
            addResizeListener();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize ChatPage\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to initialise all the components of this page */    
    private void initializeComponents() {
        try {
            chatList = new ChatList();
            chatScreen = new ChatScreen();
            
            // Set parent references
            chatList.setParentChatPage(this);
            chatScreen.setParentChatPage(this);
            
            // Ensure components are visible and have preferred sizes
            chatList.setPreferredSize(new Dimension(300, 400));
            chatScreen.setPreferredSize(new Dimension(500, 400));
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to initialize components\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to set up the layout of this page */
    private void setupLayouts() {
        try {
            // Main container with CardLayout to switch between desktop and mobile
            mainLayout = new CardLayout();
            mainContainer = new JPanel(mainLayout);
            
            // Setup desktop view
            setupDesktopView();
            
            // Setup mobile view  
            setupMobileView();
            
            // Add both views to main container
            mainContainer.add(splitPane, "DESKTOP");
            mainContainer.add(mobileView, "MOBILE");
            
            // Add main container to this panel
            add(mainContainer, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to setup layouts\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Initial state of the page, i.e., desktop mode */
    private void setInitialState() {
        try {
            // Show desktop view initially
            mainLayout.show(mainContainer, "DESKTOP");
            chatScreen.setMobileMode(false);
            
            // Force initial layout
            SwingUtilities.invokeLater(() -> {
                revalidate();
                repaint();
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set initial state\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Setting up the layout in resizing event */
    private void addResizeListener() {
        try {
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    try {
                        // Use SwingUtilities.invokeLater to ensure proper event handling
                        SwingUtilities.invokeLater(() -> checkAndUpdateLayout());
                    } catch (Exception ex) {
                        System.out.println("[ERROR] Failed during resize event\nError Message: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to add resize listener\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to setup the desktop view of chat page
     */
    private void setupDesktopView() {
        try {
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(chatList);
            splitPane.setRightComponent(chatScreen);
            splitPane.setResizeWeight(0.4); // 40% for chat list
            splitPane.setDividerSize(2);
            splitPane.setContinuousLayout(true);
            splitPane.setOneTouchExpandable(false);
            
            // Set minimum and preferred sizes
            chatList.setMinimumSize(new Dimension(250, 0));
            chatScreen.setMinimumSize(new Dimension(300, 0));
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to setup desktop view\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to set up mobile view
     */
    private void setupMobileView() {
        try {
            cardLayout = new CardLayout();
            mobileView = new JPanel(cardLayout);
            
            // Create wrapper panels to avoid component sharing issues
            JPanel chatListWrapper = new JPanel(new BorderLayout());
            JPanel chatScreenWrapper = new JPanel(new BorderLayout());
            
            mobileView.add(chatListWrapper, "CHAT_LIST");
            mobileView.add(chatScreenWrapper, "CHAT_SCREEN");
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to setup mobile view\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method will called when the resize event will happen. It will adjust the layout based on the width and height of the window
     */
    private void checkAndUpdateLayout() {
        try {
            if (!isDisplayable()) {
                return; // Don't process if component isn't ready
            }
            
            int currentWidth = getWidth();
            boolean shouldBeMobile = currentWidth < (AppConfig.MEDIUM_WIDTH + 120);
            
            if (shouldBeMobile && !isMobileMode) {
                switchToMobileMode();
            } else if (!shouldBeMobile && isMobileMode) {
                switchToDesktopMode();
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to check and update layout\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to switch to mobile mode */
    private void switchToMobileMode() {
        try {
            isMobileMode = true;
            
            // Remove components from split pane
            splitPane.remove(chatList);
            splitPane.remove(chatScreen);
            
            // Get wrapper panels and add components
            JPanel chatListWrapper = (JPanel) ((JPanel) mobileView.getComponent(0));
            JPanel chatScreenWrapper = (JPanel) ((JPanel) mobileView.getComponent(1));
            
            chatListWrapper.removeAll();
            chatScreenWrapper.removeAll();
            
            chatListWrapper.add(chatList, BorderLayout.CENTER);
            chatScreenWrapper.add(chatScreen, BorderLayout.CENTER);
            
            // Switch to mobile view
            mainLayout.show(mainContainer, "MOBILE");
            
            // Show chat list by default in mobile mode
            showChatList();
            
            // Update components for mobile mode
            chatScreen.setMobileMode(true);
            
            // Force layout update
            SwingUtilities.invokeLater(() -> {
                mobileView.revalidate();
                mobileView.repaint();
                mainContainer.revalidate();
                mainContainer.repaint();
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to switch to mobile mode\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to switch to desktop mode */
    private void switchToDesktopMode() {
        try {
            isMobileMode = false;
            
            // Remove components from mobile view wrappers
            JPanel chatListWrapper = (JPanel) ((JPanel) mobileView.getComponent(0));
            JPanel chatScreenWrapper = (JPanel) ((JPanel) mobileView.getComponent(1));
            
            chatListWrapper.remove(chatList);
            chatScreenWrapper.remove(chatScreen);
            
            // Add components back to split pane
            splitPane.setLeftComponent(chatList);
            splitPane.setRightComponent(chatScreen);
            
            // Switch to desktop view
            mainLayout.show(mainContainer, "DESKTOP");
            
            // Update components for desktop mode
            chatScreen.setMobileMode(false);
            
            // Force layout update
            SwingUtilities.invokeLater(() -> {
                splitPane.revalidate();
                splitPane.repaint();
                mainContainer.revalidate();
                mainContainer.repaint();
            });
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to switch to desktop mode\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Method to show chat list
     */
    public void showChatList() {
        try {
            if (isMobileMode) {
                cardLayout.show(mobileView, "CHAT_LIST");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to show chat list\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Method to show chat screen
     */
    public void showChatScreen() {
        try {
            if (isMobileMode) {
                cardLayout.show(mobileView, "CHAT_SCREEN");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to show chat screen\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * return if the current window is mobile mode or not
     * @return boolean indicating mobile mode status
     */
    public boolean isMobileMode() {
        try {
            return isMobileMode;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to check mobile mode status\nError Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /** Method to be called when a chat is selected from ChatList */
    public void onChatSelected(String username, String ipAddress) {
        try {
            chatScreen.setSelectedUser(username);
            chatScreen.setIpAddress(ipAddress);
            chatScreen.setMessages(State.messageHistory.getMessagesWithUser(ipAddress));
            chatScreen.setUserStatus(Main.server.checkUserIsOnline(ipAddress));
            if (isMobileMode) {
                showChatScreen();
            }
            chatScreen.revalidate();
            chatScreen.repaint();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to handle chat selection\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}