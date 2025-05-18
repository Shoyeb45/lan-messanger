package main.java.com.lanmessanger.ui.components.addFriendPage;

import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {

    private RightPanel rightPanel;
    private LeftPanel leftPanel;

    public CenterPanel() {
        rightPanel = new RightPanel();
        leftPanel = new LeftPanel();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // add(label);
        add(leftPanel);
        add(rightPanel);
    }

    
}

class LeftPanel extends JPanel { 
    
    private JLabel label;
    
    public LeftPanel() {
        label = new JLabel();
        add(label);
        loadImage();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    private void loadImage() {
        ImageIcon image = new ImageIcon("public/images/app/logo.png");
        image = new ImageIcon(image.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        label.setIcon(image);
    }
}
class RightPanel extends JPanel {
    private JLabel label;
    private IPAddressPanel ipAddressPanel;

    public RightPanel() {
        label = new JLabel("Send this IP Address to your friend or say your friend to send this IP address.");
        ipAddressPanel = new IPAddressPanel();
        ipAddressPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(ipAddressPanel);
        add(label);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}

