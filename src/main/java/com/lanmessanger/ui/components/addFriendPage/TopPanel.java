package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.lanmessanger.app.AppConfig;

public class TopPanel extends JPanel {
    private JLabel textLabel;
    
    public TopPanel() {
        setLayout(new BorderLayout());
        textLabel = new JLabel("Add friend to start chatting", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(getWidth(), 100));

        
        this.add(textLabel, BorderLayout.CENTER);
        // Listen for resize events
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(getWidth());
            }
        });
    }

     // Adjust font based on width breakpoints
    private void adjustFontSize(int width) {
        int fontSize;
        if (width < AppConfig.SMALL_WIDTH) {         // sm
            fontSize = 30;
        } else if (width < AppConfig.MEDIUM_WIDTH) {  // md
            fontSize = 35;
        } else {                   // xl
            fontSize = 40;
        }

        textLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        revalidate();
        repaint();
    }
}
