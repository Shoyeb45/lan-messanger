package main.java.com.lanmessanger.ui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class NavBar extends JPanel {

    // all buttons
    /** Button for menu */
    private Button menuButton;  
    /** Button for chat */
    private Button chatButton;
    /** Button for adding a friend */
    private Button addFriendButton;
    /** Button for scaning nearby friends */
    private Button scanButton;
    /** Button for showing all the friends */
    private Button friendsButton;
    /** flag to know if the nav bar is expanded or not */
    private boolean isExpanded = false;

    public NavBar() {
        // initialise all the buttons with icons
        initialiseButtons();   
        
        // Set vertical layout to nav bar
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Give positioninig
        this.setBounds(0, 0, 200, getHeight());
        this.setBackground(Color.white);

        // add all the buttons
        addButtons();
    }

    /** Add buttons to the nav bar */
    private void addButtons() {
        add(menuButton);
        add(Box.createRigidArea(new Dimension(0, 20)));  // add gap between menu and rest of the buttons
        add(chatButton);
        add(addFriendButton);
        add(friendsButton);
        add(scanButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Methdod for initialising buttons with icons, size and tooltip text
     */
    private void initialiseButtons() {
        // set icons
        FontIcon icon = FontIcon.of(FontAwesome.LIST, 24);  
        menuButton = new Button(icon);  
        icon = FontIcon.of(FontAwesome.COMMENTS, 24);  
        chatButton = new Button(icon);
        icon = FontIcon.of(FontAwesome.USER_PLUS, 24);
        addFriendButton = new Button(icon);
        icon = FontIcon.of(FontAwesome.USERS, 24);
        friendsButton = new Button(icon);
        icon = FontIcon.of(FontAwesome.WIFI, 24);
        scanButton = new Button(icon);
        
        // add on click functionality
        menuButton.addActionListener(e -> toggleNav());   // add logic of expanding the navbar

        // set tooltip text
        menuButton.setToolTipText("Open Navigation");
        addFriendButton.setToolTipText("Add new friend");
        chatButton.setToolTipText("Chats");        
        friendsButton.setToolTipText("All friends");
        scanButton.setToolTipText("Scan nearby friends");

        // give padding to the buttons
        menuButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 12));
        chatButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 15));
        addFriendButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 11));
        friendsButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 12));
        scanButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 11));


        Button[] buttons = {menuButton, chatButton, addFriendButton, friendsButton};

        for (Button button: buttons) {
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            button.setHorizontalAlignment(JButton.LEFT);
            button.setIconTextGap(8);
        }
    } 

    /** Method to add the title to the buttons when expanded */
    public void setTitleToButtons() {
        chatButton.setText("Chats");
        addFriendButton.setText("Add new friend");
        friendsButton.setText("Friends");
        scanButton.setText("Scan nearby friends");
    }
    
    /** Method to remove the title to the buttons when collapsed */
    public void removeTitleToButtons() {
        chatButton.setText("");
        addFriendButton.setText("");
        friendsButton.setText("");
        scanButton.setText("");
    }




    private void toggleNav() {
        isExpanded = !isExpanded;

        if (isExpanded) {
            setTitleToButtons();
        } else {
            removeTitleToButtons();
        }

        this.repaint();
        this.revalidate();
    }
}
