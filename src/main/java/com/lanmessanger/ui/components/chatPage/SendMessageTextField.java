package main.java.com.lanmessanger.ui.components.chatPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SendMessageTextField extends JTextField implements FocusListener {
    private String placeholder;
    private boolean showingPlaceholder;
    private Color placeholderColor = Color.GRAY;
    private Color normalColor = Color.BLACK;

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
            showingPlaceholder = true;
            super.setText(placeholder);
            setForeground(placeholderColor);
        } else {
            showingPlaceholder = false;
            super.setText(text);
            setForeground(normalColor);
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (showingPlaceholder) {
            super.setText(placeholder);
        }
    }

    public Color getPlaceholderColor() {
        return placeholderColor;
    }

    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        if (showingPlaceholder) {
            setForeground(placeholderColor);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingPlaceholder) {
            showingPlaceholder = false;
            super.setText("");
            setForeground(normalColor);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (super.getText().isEmpty()) {
            showingPlaceholder = true;
            super.setText(placeholder);
            setForeground(placeholderColor);
        }
    }
}