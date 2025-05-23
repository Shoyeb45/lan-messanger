package main.java.com.lanmessanger.ui.components.addFriendPage;

/** Imports */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.ui.components.RoundedPanel;
import main.java.com.lanmessanger.ui.utils.ColorPalette;


/**
 * Centeral panel for the add friend page, this contains two panles to the left and right side. 
 */
public class CenterPanel extends JPanel {

    /** Panel for the right side */
    private RightPanel rightPanel;
    /** Panel for the left side */
    private LeftPanel leftPanel;

    public CenterPanel() {
        // Set background color
        setBackground(ColorPalette.BACKGROUND);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create panels
        rightPanel = new RightPanel();
        leftPanel = new LeftPanel();
        
        // Use layout that can be changed dynamically
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        // Add spacing between panels
        add(Box.createHorizontalStrut(20));
        add(leftPanel);
        add(Box.createHorizontalStrut(40));
        add(rightPanel);
        add(Box.createHorizontalStrut(20));
        
        // Add component listener to check for resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLayout();
            }
        });
    }
    
    /**
     * Method to update the width and height of the containers upon resizing
     */
    private void updateLayout() {
        int width = getWidth();
        removeAll();
        
        // Check if we need to switch layouts based on width
        if (width < AppConfig.MEDIUM_WIDTH) {
            // Switch to vertical layout
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(Box.createVerticalStrut(20));
            add(leftPanel);
            add(Box.createVerticalStrut(40));
            add(rightPanel);
            add(Box.createVerticalStrut(20));
        } else {
            // Switch to horizontal layout
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(Box.createHorizontalStrut(20));
            add(leftPanel);
            add(Box.createHorizontalStrut(40));
            add(rightPanel);
            add(Box.createHorizontalStrut(20));
        }

        // Force panels to recompute sizes
        rightPanel.setMaximumSize(new Dimension(
            width < AppConfig.MEDIUM_WIDTH ? width - 40 : (width / 2) - 60,
            Integer.MAX_VALUE
        ));
        
        rightPanel.updateInternalWrapping(
            width < AppConfig.MEDIUM_WIDTH ? width - 40 : (width / 2) - 60, width
        );
        
        revalidate();
        repaint();
    }
}


/** Left panel of the central panel of the add friend page */
class LeftPanel extends RoundedPanel {
    /** Labe; for the LAN Messanger Logo */
    private JLabel logoLabel;
    /** Label for the title  */
    private JLabel titleLabel;
    
    public LeftPanel() {
        super(15, ColorPalette.PANEL_BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Add title
        titleLabel = new JLabel("LAN Messenger");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ColorPalette.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create logo panel
        logoLabel = new JLabel();
        loadImage();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components with spacing
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(logoLabel);
        add(Box.createVerticalStrut(10));
        
        // Add tagline
        JLabel taglineLabel = new JLabel("Connect securely with your friends");
        taglineLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        taglineLabel.setForeground(ColorPalette.SECONDARY_TEXT);
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(taglineLabel);
        add(Box.createVerticalGlue());
    }

    /** Method to load the image of the logo */
    private void loadImage() {
        ImageIcon image = new ImageIcon("public/images/app/logo.png");
        image = new ImageIcon(image.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH));
        logoLabel.setIcon(image);
    }
}

/** Right panel of the central panel of the add friend page */
class RightPanel extends RoundedPanel {
    /** Panel which contains IP Address and copy button */
    private IPAddressPanel ipAddressPanel;
    /** Text for helping the user */
    private TextLabelForRightPanel textPanel;

    public RightPanel() {
        super(15, ColorPalette.PANEL_BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title for this panel
        JLabel titleLabel = new JLabel("Share Your Connection");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ColorPalette.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        // give spacing       
        add(Box.createVerticalStrut(20));
        ipAddressPanel = new IPAddressPanel();
        add(ipAddressPanel);
       
 
        textPanel = new TextLabelForRightPanel();
        add(textPanel);
    }

    /**
     * Method which will update the dimension of the {@code textPanel} upon resizing
     * @param width       calculate width of the window to resize
     * @param parentWidth original width of the window
     * @see TextLabelForRightPanel
     */
    public void updateInternalWrapping(int width, int parentWidth) {
        textPanel.updateInternalWrapping(width);
        ipAddressPanel.setPreferredSize(new Dimension(Math.min(300, width - 60), 60));
        if (parentWidth < AppConfig.LARGE_WIDTH - 100) {
            ipAddressPanel.setPreferredSize(new Dimension(Math.min(300, width - 60), 80));
        }
    }
}

/** Separate container for the text for the user */
class TextLabelForRightPanel extends JPanel {
    /** Text area so that we can make it wrap upon resizing */
    private JTextArea instructionLabel;
    
    public TextLabelForRightPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        
        instructionLabel = new JTextArea(
            "Send this IP Address to your friend to establish a secure connection. " +
            "Once they add your address, you'll be able to start messaging."
        );
        instructionLabel.setLineWrap(true);
        instructionLabel.setWrapStyleWord(true);
        instructionLabel.setEditable(false);
        instructionLabel.setFocusable(false);
        instructionLabel.setOpaque(false);
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instructionLabel.setForeground(ColorPalette.TEXT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        
        add(instructionLabel, BorderLayout.CENTER);
    }

    /**
     * 
     * @param width Calculated new width of the {@code instructionLabel}
     */
    public void updateInternalWrapping(int width) {
        int textAreaWidth = width - 40;
        instructionLabel.setPreferredSize(new Dimension(textAreaWidth, 90));
        revalidate();
        repaint();
    }
}