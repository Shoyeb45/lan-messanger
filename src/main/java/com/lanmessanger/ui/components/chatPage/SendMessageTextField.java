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
        return showingPlaceholder ? "" : super.getText();
    }

    @Override
    public void setText(String text) {
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
    }

    /**
     * Method to show the placeholder
     */
    private void showPlaceholder() {
        // if there is nothing in placeholder
        if (super.getText().isEmpty()) {
            showingPlaceholder = true;
            super.setText(placeholder);
            setForeground(placeholderColor);
        }
    }

    /** Method to hide the placeholder */
    private void hidePlaceholder() {
        if (showingPlaceholder) {
            showingPlaceholder = false;
            super.setText("");
            setForeground(normalColor);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        boolean wasEnabled = isEnabled();
        super.setEnabled(enabled);
        
        if (!wasEnabled && enabled) {
            // Just got re-enabled
            wasDisabled = true;
            // Use SwingUtilities.invokeLater to ensure this happens after all other UI updates
            SwingUtilities.invokeLater(() -> {
                if (super.getText().isEmpty() && !hasFocus()) {
                    showPlaceholder();
                }
                wasDisabled = false;
            });
        } else if (wasEnabled && !enabled) {
            // Just got disabled - don't change placeholder state
            wasDisabled = true;
        }
    }

    /**
     * 
     * @return Get place holder text
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Set place holder text
     * @param placeholder Text to be set in placeholder
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (showingPlaceholder) {
            super.setText(placeholder);
        }
    }

    /**
     * 
     * @return Place holder color {@code Color}
     */
    public Color getPlaceholderColor() {
        return placeholderColor;
    }
    
    /**
     * 
     * @return Place holder color {@code Color}
     */
    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        if (showingPlaceholder) {
            setForeground(placeholderColor);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingPlaceholder && isEnabled()) {
            hidePlaceholder();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (super.getText().isEmpty() && isEnabled() && !wasDisabled) {
            showPlaceholder();
        }
    }

    // Helper method to check if we're showing placeholder (for debugging)
    public boolean isShowingPlaceholder() {
        return showingPlaceholder;
    }
}