package main.java.com.lanmessanger.ui.components.navBar;

// Imports
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.components.Button;
import main.java.com.lanmessanger.ui.router.RouterManager;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Navbar component which have menu button and other buttons for navigation
 * @author Shoyeb Anasri
 */
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
        add(scanButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Methdod for initialising buttons with icons, size and tooltip text
     */
    private void initialiseButtons() {
        // set icons
        FontIcon icon = FontIcon.of(FontAwesome.LIST, 24, ColorPalette.PRIMARY);  

        menuButton = new Button(icon);  
        icon = FontIcon.of(FontAwesome.COMMENTS, 24, ColorPalette.PRIMARY);  
        chatButton = new Button(icon);
        icon = FontIcon.of(FontAwesome.USER_PLUS, 24, ColorPalette.PRIMARY);
        addFriendButton = new Button(icon);
      
        icon = FontIcon.of(FontAwesome.WIFI, 24, ColorPalette.PRIMARY);
        scanButton = new Button(icon);
        
        // add on click functionality
        menuButton.addActionListener(e -> toggleNav());   // add logic of expanding the navbar
        chatButton.addActionListener(e -> openChatsPage());
        scanButton.addActionListener(e -> openScannerPage());
        addFriendButton.addActionListener(e -> openAddFriendPage());

        // set tooltip text
        menuButton.setToolTipText("Open Navigation");
        addFriendButton.setToolTipText("Add new friend");
        chatButton.setToolTipText("Chats");        
        scanButton.setToolTipText("Scan nearby friends");

        // give padding to the buttons
        menuButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 12));
        chatButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 15));
        addFriendButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 11));
        scanButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 8, 11));


        Button[] buttons = {menuButton, chatButton, addFriendButton};

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
        scanButton.setText("Scan nearby friends");
    }
    
    /** Method to remove the title to the buttons when collapsed */
    public void removeTitleToButtons() {
        chatButton.setText("");
        addFriendButton.setText("");
        scanButton.setText("");
    }

    /** Method to open the chat page */
    private void openChatsPage() {
        RouterManager.getInstance().navigateTo("chats");
    }
    
    /** Method to open the scanner page */
    private void openScannerPage() {
        RouterManager.getInstance().navigateTo("scanner");
    }
    
    /** Method to open the add friend page */
    private void openAddFriendPage() {
        RouterManager.getInstance().navigateTo("addFriend");
    }

    /**
     * Method to toggle the navbar, expand or collapse the navbar
     */
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
