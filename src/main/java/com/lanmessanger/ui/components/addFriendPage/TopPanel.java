package main.java.com.lanmessanger.ui.components.addFriendPage;

/** imports */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class TopPanel extends JPanel {
    /** lable to hold the text */
    private JLabel textLabel;
    
    public TopPanel() {
        setBackground(ColorPalette.BACKGROUND);
        setLayout(new FlowLayout());
        textLabel = new JLabel("Add friend to start chatting", SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        textLabel.setForeground(ColorPalette.PRIMARY);
        
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        
        this.add(textLabel, BorderLayout.CENTER);
        // Listen for resize events
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFontSize(getWidth());
            }
        });
    }

    /**
     * Adjust font based on width breakpoints
     * @param width width of the window
     */
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
