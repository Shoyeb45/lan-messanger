package main.java.com.lanmessanger.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * A card like representation of the user
 */
public class ChatProfile extends RoundedPanel {
    /** Circular icon  */
    private CircularIcon icon;
    /** The right panel of the ChatProfile */
    private JPanel contentPanel;
    /** Flag for if it is hovered or not */
    private boolean isHovered = false;
    /** Flag for if it is selected or not */
    private boolean isSelected = false;

    /**
     * 
     * @param contentPanel Right panel of the chat profile
     */
    public ChatProfile(int radius, JPanel contentPanel) {
        super(radius, ColorPalette.BACKGROUND);
        this.icon = new CircularIcon(FontIcon.of(FontAwesome.USER, 30));
        this.contentPanel = contentPanel;
        
        initializeComponents();
        setupLayout();
        setupInteractions();
    }
    
    /** 
     * Method to initialise all the components of the Chat Profile
     */
    private void initializeComponents() {
        // Main panel setup
        this.setBackground(ColorPalette.PANEL_BACKGROUND);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        this.setPreferredSize(new Dimension(0, 70));
        
        // Content panel setup
        contentPanel.setBackground(ColorPalette.PANEL_BACKGROUND);
        contentPanel.setOpaque(false);
    }
    
    /** Method to set up the layout of the Chat Profile */
    private void setupLayout() {
        this.setLayout(new BorderLayout(15, 0));
        this.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        
        this.add(icon, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Method for setting up the user interactions, like hover, click...
     */
    private void setupInteractions() {
        // Add hover effects
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isSelected = !isSelected;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Background color based on state
        Color bgColor;
        if (isSelected) {
            bgColor = ColorPalette.SECONDARY.brighter();
        } else if (isHovered) {
            bgColor = new Color(ColorPalette.INPUT_BACKGROUND.getRed(), 
                              ColorPalette.INPUT_BACKGROUND.getGreen(), 
                              ColorPalette.INPUT_BACKGROUND.getBlue(), 180);
        } else {
            bgColor = ColorPalette.PANEL_BACKGROUND;
        }
        
        // Draw rounded rectangle background
        g2d.setColor(bgColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        
        // Draw subtle border
        if (isSelected) {
            g2d.setColor(ColorPalette.PRIMARY);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        } else if (isHovered) {
            g2d.setColor(new Color(ColorPalette.SECONDARY_TEXT.getRed(), 
                                 ColorPalette.SECONDARY_TEXT.getGreen(), 
                                 ColorPalette.SECONDARY_TEXT.getBlue(), 60));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        }
        
        g2d.dispose();
        super.paintComponent(g);
    }
    
    /** 
     * To set up the selected user
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }
    
    /**
     * Get if the user is selected or not
     * @return
     */
    public boolean isSelected() {
        return isSelected;
    }
}