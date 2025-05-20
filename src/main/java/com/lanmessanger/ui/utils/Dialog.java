package main.java.com.lanmessanger.ui.utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import main.java.com.lanmessanger.ui.components.ModernButton;

/**
 * ModernDialog - A custom JOptionPane implementation with modern styling
 * Supports information, warning, error, and confirmation dialogs with consistent styling
 */
public class Dialog {
    // Message types corresponding to JOptionPane types
    public static final int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
    
    // Button types
    public static final int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
    public static final int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;
    public static final int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION;
    
    // Button return values
    public static final int YES_OPTION = JOptionPane.YES_OPTION;
    public static final int NO_OPTION = JOptionPane.NO_OPTION;
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    public static final int OK_OPTION = JOptionPane.OK_OPTION;
    public static final int CLOSED_OPTION = JOptionPane.CLOSED_OPTION;
    
    // Display a message dialog with custom styling
    public static void showMessageDialog(Component parentComponent, Object message, 
                                         String title, int messageType) {
        JDialog dialog = createStyledDialog(parentComponent, message, title, messageType, 
                                            JOptionPane.DEFAULT_OPTION, null);
        dialog.setVisible(true);
    }
    
    // Display a confirmation dialog with custom styling
    public static int showConfirmDialog(Component parentComponent, Object message,
                                       String title, int optionType, int messageType) {
        JDialog dialog = createStyledDialog(parentComponent, message, title, messageType, 
                                           optionType, null);
        
        // We need to get the return value from the option pane
        dialog.setVisible(true);
        JOptionPane optionPane = (JOptionPane) dialog.getContentPane().getComponent(0);
        Object selectedValue = optionPane.getValue();
        
        if (selectedValue == null)
            return CLOSED_OPTION;
        
        if (selectedValue instanceof Integer)
            return ((Integer) selectedValue).intValue();
            
        return CLOSED_OPTION;
    }
    
    // Display an input dialog with custom styling
    public static String showInputDialog(Component parentComponent, Object message,
                                        String title, int messageType) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
            textField.getBorder(),
            new EmptyBorder(5, 5, 5, 5)));
        
        JDialog dialog = createStyledDialog(parentComponent, 
                                          createInputPanel(message, textField), 
                                          title, messageType, 
                                          JOptionPane.OK_CANCEL_OPTION, textField);
        
        dialog.setVisible(true);
        
        JOptionPane optionPane = (JOptionPane) dialog.getContentPane().getComponent(0);
        Object selectedValue = optionPane.getValue();
        
        if (selectedValue != null && ((Integer) selectedValue).intValue() == OK_OPTION) {
            return textField.getText();
        }
        
        return null;
    }
    
    // Helper method to create an input panel
    private static JPanel createInputPanel(Object message, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 10));
        panel.setOpaque(false);
        
        if (message instanceof Component) {
            panel.add((Component) message, BorderLayout.NORTH);
        } else {
            JLabel messageLabel = new JLabel(message.toString());
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setForeground(ColorPalette.TEXT);
            panel.add(messageLabel, BorderLayout.NORTH);
        }
        
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }
    
    // Create a styled dialog based on the message type
    private static JDialog createStyledDialog(Component parentComponent, Object message,
                                             String title, int messageType, int optionType,
                                             JComponent focusComponent) {
        // Create a styled option pane
        JOptionPane optionPane = new JOptionPane(message, messageType, optionType);
        
        // Create custom buttons
        customizeButtons(optionPane);
        
        // Create the dialog
        JDialog dialog = optionPane.createDialog(parentComponent, title);
        
        // Make the title bar use your color palette
        dialog.setBackground(ColorPalette.BACKGROUND);
        
        // Style the dialog content pane
        Container contentPane = dialog.getContentPane();
        if (contentPane instanceof JComponent) {
            JComponent cp = (JComponent) contentPane;
            cp.setBackground(ColorPalette.BACKGROUND);
            cp.setBorder(new EmptyBorder(15, 15, 15, 15));
        }
        
        // Style the message part
        customizeMessageArea(optionPane, messageType);
        
        // Set icon
        setCustomIcon(optionPane, messageType);
        
        // Set focus on the input component if provided
        if (focusComponent != null) {
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    focusComponent.requestFocusInWindow();
                }
            });
        }
        
        dialog.pack();
        centerDialog(dialog, parentComponent);
        
        return dialog;
    }
    
    // Replace standard buttons with ModernButtons
    private static void customizeButtons(JOptionPane optionPane) {
        // Get the original buttons panel
        Component[] components = optionPane.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component[] panelComps = panel.getComponents();
                
                for (Component panelComp : panelComps) {
                    if (panelComp instanceof JPanel) {
                        JPanel buttonPanel = (JPanel) panelComp;
                        Component[] buttons = buttonPanel.getComponents();
                        
                        // Replace each button with a ModernButton
                        for (int i = 0; i < buttons.length; i++) {
                            if (buttons[i] instanceof JButton) {
                                JButton oldButton = (JButton) buttons[i];
                                ModernButton newButton = new ModernButton(oldButton.getText(), ColorPalette.PRIMARY, ColorPalette.SECONDARY);
                                newButton.setPreferredSize(new Dimension(100, 36));
                                
                                // Copy action listeners
                                for (ActionListener listener : oldButton.getActionListeners()) {
                                    newButton.addActionListener(listener);
                                }
                                
                                // Add proper spacing
                                buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
                                
                                // Replace the button
                                buttonPanel.remove(oldButton);
                                buttonPanel.add(newButton, i);
                                buttonPanel.revalidate();
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Set custom icons based on dialog type
    private static void setCustomIcon(JOptionPane optionPane, int messageType) {
        FontIcon icon;
        
        switch (messageType) {
            case INFORMATION_MESSAGE:
                icon = FontIcon.of(FontAwesome.INFO_CIRCLE, 32);
                icon.setIconColor(new Color(0, 120, 212)); // Info blue
                break;
            case WARNING_MESSAGE:
                icon = FontIcon.of(FontAwesome.EXCLAMATION_TRIANGLE, 32);
                icon.setIconColor(new Color(255, 186, 8)); // Warning yellow
                break;
            case ERROR_MESSAGE:
                icon = FontIcon.of(FontAwesome.TIMES_CIRCLE, 32);
                icon.setIconColor(new Color(232, 17, 35)); // Error red
                break;
            case QUESTION_MESSAGE:
                icon = FontIcon.of(FontAwesome.QUESTION_CIRCLE, 32);
                icon.setIconColor(new Color(0, 120, 212)); // Question blue
                break;
            default:
                return;
        }
        
        optionPane.setIcon(icon);
    }
    
    // Style the message area
    private static void customizeMessageArea(JOptionPane optionPane, int messageType) {
        Component[] components = optionPane.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setOpaque(false);
                panel.setBackground(ColorPalette.BACKGROUND);
                
                Component[] panelComps = panel.getComponents();
                for (Component panelComp : panelComps) {
                    if (panelComp instanceof JLabel) {
                        JLabel label = (JLabel) panelComp;
                        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        label.setForeground(ColorPalette.TEXT);
                    }
                    
                    if (panelComp instanceof JPanel) {
                        ((JPanel) panelComp).setOpaque(false);
                        ((JPanel) panelComp).setBackground(ColorPalette.BACKGROUND);
                    }
                }
            }
        }
    }
    
    // Center the dialog on screen or parent component
    private static void centerDialog(JDialog dialog, Component parent) {
        if (parent == null || !parent.isShowing()) {
            // Center on screen
            dialog.setLocationRelativeTo(null);
        } else {
            // Center on parent
            Point parentLocation = parent.getLocationOnScreen();
            Dimension parentSize = parent.getSize();
            Dimension dialogSize = dialog.getSize();
            
            int x = parentLocation.x + (parentSize.width - dialogSize.width) / 2;
            int y = parentLocation.y + (parentSize.height - dialogSize.height) / 2;
            
            dialog.setLocation(x, y);
        }
    }
}