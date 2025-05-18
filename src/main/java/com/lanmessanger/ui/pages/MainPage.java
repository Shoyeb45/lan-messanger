package main.java.com.lanmessanger.ui.pages;


import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainPage extends JPanel {
    private NavigationPage navigationPage;
    private JPanel mainContentPage;

    public MainPage() {
        // creating app in 3 parts
        // mainContentPage = new AddFriendPage();

        this.setBackground(Color.green);
        this.setLayout(new BorderLayout());
        // this.setBounds(0, 0, getWidth(), 100);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        navigationPage = new NavigationPage(mainContentPage);

        this.add(navigationPage, BorderLayout.WEST);   
    }
   
    public void setMainContentPage() {
        if (mainContentPage != null) {
            System.out.println("Am I here");
            this.add(mainContentPage);
        }
    }
}
