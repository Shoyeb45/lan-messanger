package main.java.com.lanmessanger.ui;



import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.ui.pages.MainPage;

/** Main UI Application frame */
public class App extends JFrame {

    /** 
     * main page of the application
     */
    private MainPage mainPage;

    public App() {
        setFavico();
        this.setTitle("LAN Messanger - A peer to peer to chatting platform");

        // To allow the resizing of the window
        this.setResizable(true);

        // The close button will close the application
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // This will maximize the size of the application at start
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // When app window is minized, then it will have the given size 
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height);

        // To prevent the resizing below this Dimension3
        this.setMinimumSize(new Dimension(AppConfig.SMALL_WIDTH + 110, 800));
    }

    /**
     * Method to set favico of the app
     */
    private void setFavico() {
        ImageIcon favico = new ImageIcon("./public/images/app/appFavico.png");
        Image image = favico.getImage().getScaledInstance(52, 52, Image.SCALE_SMOOTH);
        this.setIconImage(new ImageIcon(image).getImage());
    }

    /** Method which will intialise the UI of the application */
    public void init() throws InterruptedException {
        mainPage = new MainPage();
        mainPage.setBounds(0, 0, 100, 100);
        getContentPane().add(mainPage); 
        setVisible(true);
    }
}
