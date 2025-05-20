package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.com.lanmessanger.ui.utils.ColorPalette;

/**
 * Modern styled input field with rounded corners and placeholder text
 */
public class InputField extends JPanel {
    private JTextField textField;
    private JLabel label;

    public InputField(String placeholder, int height) {
        setLayout(new BorderLayout(5, 5));
        setOpaque(false);

        // Create label
        label = new JLabel(placeholder);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(ColorPalette.SECONDARY_TEXT);

        // Create text field
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, height)); // Fixed width
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(ColorPalette.TEXT);
        textField.setBackground(ColorPalette.PANEL_BACKGROUND);
        textField.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Add components
        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        
        // Fix the size of the entire input field
        setPreferredSize(new Dimension(300, height));
        setMinimumSize(new Dimension(250, height));
    }

    public JTextField getTextField() {
        return textField;
    }
}