package main.java.com.lanmessanger.app.swingSPA;

import javax.swing.*;
import java.awt.*;

// Main class with main method
public class SwingSPA {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

// The main frame (like root of a React app)
class MainFrame extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    public MainFrame() {
        setTitle("Swing SPA Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Add pages to mainPanel with unique names (like routes)
        mainPanel.add(new HomePage(this), "home");
        mainPanel.add(new ProfilePage(this), "profile");
        mainPanel.add(new SettingsPage(this), "settings");

        add(mainPanel);
        showPage("home");
    }

    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }
}

// Home Page
class HomePage extends JPanel {
    public HomePage(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("🏠 Home Page", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttons = new JPanel();
        JButton profileBtn = new JButton("Go to Profile");
        JButton settingsBtn = new JButton("Go to Settings");

        profileBtn.addActionListener(e -> frame.showPage("profile"));
        settingsBtn.addActionListener(e -> frame.showPage("settings"));

        buttons.add(profileBtn);
        buttons.add(settingsBtn);

        add(label, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}

// Profile Page
class ProfilePage extends JPanel {
    public ProfilePage(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("👤 Profile Page", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttons = new JPanel();
        JButton homeBtn = new JButton("Back to Home");
        JButton settingsBtn = new JButton("Go to Settings");

        homeBtn.addActionListener(e -> frame.showPage("home"));
        settingsBtn.addActionListener(e -> frame.showPage("settings"));

        buttons.add(homeBtn);
        buttons.add(settingsBtn);

        add(label, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}

// Settings Page
class SettingsPage extends JPanel {
    public SettingsPage(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("⚙️ Settings Page", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttons = new JPanel();
        JButton homeBtn = new JButton("Back to Home");
        JButton profileBtn = new JButton("Go to Profile");

        homeBtn.addActionListener(e -> frame.showPage("home"));
        profileBtn.addActionListener(e -> frame.showPage("profile"));

        buttons.add(homeBtn);
        buttons.add(profileBtn);

        add(label, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
