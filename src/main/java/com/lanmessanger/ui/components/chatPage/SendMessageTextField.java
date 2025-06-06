package main.java.com.lanmessanger.ui.components.chatPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/** 
 * A custom class to create input field which have placeholder
 */
public class SendMessageTextField extends JTextField implements FocusListener {
    /** Text of placeholder */
    private String placeholder;
    /** Flag indicating if the currently placeholder is showing or not */
    private boolean showingPlaceholder;
    /** Color of the placeholder */
    private Color placeholderColor = Color.GRAY;
    private Color normalColor = Color.BLACK;
    /** To know if the placeholder was disable or not */
    private boolean wasDisabled = false;

    public SendMessageTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        this.showingPlaceholder = true;

        // Set initial placeholder text and styling
        super.setText(placeholder);
        setForeground(placeholderColor);

        // Add focus listener to handle placeholder behavior
        addFocusListener(this);
    }

    public SendMessageTextField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        this.showingPlaceholder = true;

        // Set initial placeholder text and styling
        super.setText(placeholder);
        setForeground(placeholderColor);

        // Add focus listener to handle placeholder behavior
        addFocusListener(this);
    }

    @Override
    public String getText() {
        try {
            return showingPlaceholder ? "" : super.getText();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get text\nError Message: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void setText(String text) {
        try {
            if (text == null || text.isEmpty()) {
                // Clear the actual text first
                super.setText("");
                showingPlaceholder = false;
                setForeground(normalColor);
                
                // If we're not focused and enabled, show placeholder
                if (!hasFocus() && isEnabled()) {
                    showPlaceholder();
                }
            } else {
                showingPlaceholder = false;
                super.setText(text);
                setForeground(normalColor);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set text\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to show the placeholder
     */
    private void showPlaceholder() {
        try {
            // if there is nothing in placeholder
            if (super.getText().isEmpty()) {
                showingPlaceholder = true;
                super.setText(placeholder);
                setForeground(placeholderColor);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to show placeholder\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Method to hide the placeholder */
    private void hidePlaceholder() {
        try {
            if (showingPlaceholder) {
                showingPlaceholder = false;
                super.setText("");
                setForeground(normalColor);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to hide placeholder\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        try {
            boolean wasEnabled = isEnabled();
            super.setEnabled(enabled);
            
            if (!wasEnabled && enabled) {
                // Just got re-enabled
                wasDisabled = true;
                // Use SwingUtilities.invokeLater to ensure this happens after all other UI updates
                SwingUtilities.invokeLater(() -> {
                    try {
                        if (super.getText().isEmpty() && !hasFocus()) {
                            showPlaceholder();
                        }
                        wasDisabled = false;
                    } catch (Exception ex) {
                        System.out.println("[ERROR] Failed during re-enable placeholder handling\nError Message: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                });
            } else if (wasEnabled && !enabled) {
                // Just got disabled - don't change placeholder state
                wasDisabled = true;
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set enabled state\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return Get place holder text
     */
    public String getPlaceholder() {
        try {
            return placeholder;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get placeholder\nError Message: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Set place holder text
     * @param placeholder Text to be set in placeholder
     */
    public void setPlaceholder(String placeholder) {
        try {
            this.placeholder = placeholder;
            if (showingPlaceholder) {
                super.setText(placeholder);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set placeholder\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return Place holder color {@code Color}
     */
    public Color getPlaceholderColor() {
        try {
            return placeholderColor;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get placeholder color\nError Message: " + e.getMessage());
            e.printStackTrace();
            return Color.GRAY;
        }
    }
    
    /**
     * 
     * @return Place holder color {@code Color}
     */
    public void setPlaceholderColor(Color placeholderColor) {
        try {
            this.placeholderColor = placeholderColor;
            if (showingPlaceholder) {
                setForeground(placeholderColor);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to set placeholder color\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        try {
            if (showingPlaceholder && isEnabled()) {
                hidePlaceholder();
            }
        } catch (Exception ex) {
            System.out.println("[ERROR] Failed to handle focus gained\nError Message: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        try {
            if (super.getText().isEmpty() && isEnabled() && !wasDisabled) {
                showPlaceholder();
            }
        } catch (Exception ex) {
            System.out.println("[ERROR] Failed to handle focus lost\nError Message: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Helper method to check if we're showing placeholder (for debugging)
    public boolean isShowingPlaceholder() {
        try {
            return showingPlaceholder;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to check if showing placeholder\nError Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}