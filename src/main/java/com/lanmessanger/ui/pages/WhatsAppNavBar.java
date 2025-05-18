package main.java.com.lanmessanger.ui.pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WhatsAppNavBar extends JFrame {
    private JPanel navPanel;
    private boolean expanded = false;  // collapsed by default

    public WhatsAppNavBar() {
        setTitle("WhatsApp-like Navbar");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);  // Absolute positioning for overlap effect

        // --- Navbar Panel ---
        navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(34, 45, 50));
        navPanel.setBounds(0, 0, 60, getHeight());  // Start collapsed (width = 60)

        // --- Menu Toggle Button ---
        JButton menuBtn = new JButton("≡");
        menuBtn.setFocusPainted(false);
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.addActionListener(e -> toggleNav());
        navPanel.add(menuBtn);
        navPanel.add(Box.createVerticalStrut(20));

        // --- Nav Buttons ---
        navPanel.add(createNavButton("💬", "Chats"));
        navPanel.add(createNavButton("👥", "Friends"));
        navPanel.add(createNavButton("➕", "Add Friend"));
        navPanel.add(createNavButton("📷", "Scan"));

        // --- Main Content Panel ---
        JPanel mainContent = new JPanel();
        // mainContent.setBackground(Color.LIGHT_GRAY);
        mainContent.setBounds(0, 0, getWidth(), getHeight());
        mainContent.setLayout(null);

        JLabel contentLabel = new JLabel("Main App Content");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        contentLabel.setBounds(200, 100, 400, 50);
        mainContent.add(contentLabel);

        // --- Add components to JFrame ---
        add(mainContent);  // Add first so it's on bottom layer
        add(navPanel);     // Add after so it overlaps

        setVisible(true);
    }

    // Creates a nav button with icon and label (initially only icon visible)
    private JPanel createNavButton(String icon, String label) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(200, 50));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel textLabel = new JLabel(label);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        textLabel.setVisible(false);  // Hidden by default

        panel.add(iconLabel);
        panel.add(textLabel);

        // Store the text label in panel's client property so we can toggle it
        panel.putClientProperty("label", textLabel);

        return panel;
    }

    private void toggleNav() {
        expanded = !expanded;
        navPanel.setBounds(0, 0, expanded ? 180 : 60, getHeight());

        // Show/hide text labels on all nav buttons
        for (Component comp : navPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JLabel label = (JLabel) ((JPanel) comp).getClientProperty("label");
                if (label != null) {
                    label.setVisible(expanded);
                }
            }
        }

        navPanel.revalidate();
        navPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WhatsAppNavBar::new);
    }
}
