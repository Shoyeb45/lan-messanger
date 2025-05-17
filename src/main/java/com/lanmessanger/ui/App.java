package main.java.com.lanmessanger.ui;



import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import main.java.com.lanmessanger.ui.pages.MainPage;

public class App extends JFrame {

    private MainPage mainPage;
    public App() {
        setFavico();
        this.setTitle("LAN Messanger - A peer to peer to chatting platform");

        // To allow the resizing of the windo
        this.setResizable(true);

        // The close button will close the application
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // This will maximize the size of the application at start
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // When app window is minized, then it will have the given size 
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height);

    }

    /**
     * Method to set favico of the app
     */
    private void setFavico() {
        ImageIcon favico = new ImageIcon("./public/images/app/appFavico.png");
        Image image = favico.getImage().getScaledInstance(52, 52, Image.SCALE_SMOOTH);
        this.setIconImage(new ImageIcon(image).getImage());
    }

    public void init() {
        mainPage = new MainPage();
        getContentPane().add(mainPage);
        
        setVisible(true);
    }
}
