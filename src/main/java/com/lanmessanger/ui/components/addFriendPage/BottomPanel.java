package main.java.com.lanmessanger.ui.components.addFriendPage;

// Imports 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.components.ModernButton;
import main.java.com.lanmessanger.ui.components.RoundedPanel;
import main.java.com.lanmessanger.ui.utils.ColorPalette;
import main.java.com.lanmessanger.ui.utils.Dialog;

public class BottomPanel extends RoundedPanel {

    /** Button to check the connection first */
    private ModernButton checkConnectionButton;
    /** Button to create new user and add new friend */
    private ModernButton addFriendButton;
    /** Input field for the taking input name */
    private InputField nameField;
    /** Input field for the taking input of the IP address */
    private InputField ipField;
    
    public BottomPanel() {
        super(15, ColorPalette.BACKGROUND);
        
        // Initialize components
        nameField = new InputField("Name", 50);
        ipField = new InputField("IP Address", 50);
        
        checkConnectionButton = new ModernButton("Connect", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
        checkConnectionButton.setPreferredSize(new Dimension(120, 40));
        
        addFriendButton = new ModernButton("Add", ColorPalette.PRIMARY, ColorPalette.SECONDARY);
        addFriendButton.setPreferredSize(new Dimension(120, 40));
        
        // Create and add a plus icon to the Add button
        FontIcon plusIcon = FontIcon.of(FontAwesome.PLUS, 14);
        plusIcon.setIconColor(Color.WHITE);
        addFriendButton.setIcon(plusIcon);
        
        // Configure layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Set a fixed preferred size for the whole panel
        setPreferredSize(new Dimension(400, 200)); // Adjust these values as needed
        setMinimumSize(new Dimension(350, 180));   // Prevents shrinking too much
        
        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridLayout(2, 1, 0, 15));
        
        // Fix input field sizes
        nameField.setPreferredSize(new Dimension(350, 50)); // Adjust width as needed
        ipField.setPreferredSize(new Dimension(350, 50));   // Adjust width as needed
        
        inputPanel.add(nameField);
        inputPanel.add(ipField);
        
        // Make input panel respect its children's preferred sizes
        inputPanel.setPreferredSize(new Dimension(350, 115)); // Adjust based on fields + spacing
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(checkConnectionButton);
        buttonPanel.add(addFriendButton);
        
        // Fix button panel size
        buttonPanel.setPreferredSize(new Dimension(350, 50));

        // add methods to implement the functionality of the buttons
        addFriendButton.addActionListener(e -> addFriend(e));
        // Add components to the panel with alignment
        add(inputPanel);
        add(Box.createVerticalStrut(20)); // Spacing between inputs and buttons
        add(buttonPanel);
    }

    /**
     * Method for adding new friend
     * @param e
     */
    private void addFriend(ActionEvent e) {
        String name = nameField.getTextField().getText();
        String ip = ipField.getTextField().getText();
        System.out.println(name);
        System.out.println(ip);
        if (name == null || name.isBlank()) {
            Dialog.showMessageDialog(null, "Please enter name of your friend", "Empty input fields",Dialog.ERROR_MESSAGE);
            return;
        }

        if (!isValidIPAddress(ip)) {
            Dialog.showMessageDialog(null, "Please provide valid IP address", "Invalid IP address", Dialog.ERROR_MESSAGE);
            return;
        }
        System.out.println(nameField.getTextField().getText());
    }

    public boolean isValidIPAddress(String ip) {
        if (ip == null || ip.isBlank()) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;

        for (String part : parts) {
            try {
                if (part.length() == 0 || (part.length() > 1 && part.startsWith("0")))
                    return false; // No leading zeros

                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
}