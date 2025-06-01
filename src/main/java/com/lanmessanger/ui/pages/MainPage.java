package main.java.com.lanmessanger.ui.pages;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.router.RouterManager;

/**
 * Main page of the application, this contains the navbar at left and the card layout at right(Different rendering will happend here)
 */
public class MainPage extends JPanel {

    /** navigation page of the application */
    private NavigationPage navigationPage;
    /** Route Mananger, which initialises the route and can be used to switch between different pages */
    private RouterManager routerManager;
    /** This will hold the main content of the page */
    private JPanel contentPanel;
    
    public MainPage() throws InterruptedException {
        setLayout(new BorderLayout());

        // create navigation page
        navigationPage = new NavigationPage();

        // Create content panel with CardLayout for routing
        contentPanel = new JPanel(new CardLayout());

        // set up the router manager
        routerManager = RouterManager.getInstance();
        routerManager.setContentPanel(contentPanel);

        addRoutes();

        // set default route
        routerManager.navigateTo("chats");


        this.setBackground(Color.green);
        
        // this.setBounds(0, 0, getWidth(), 100);
        // this.setBorder(BorderFactory.createLineBorder(Color.black));


        // Add components to main page
        add(navigationPage, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
   
   
    

    /** 
     * Method to add the routes
     * @see RouterManager
     */
    private void addRoutes() throws InterruptedException {
        // Add all routes here
        routerManager.addRoute("start", new StartPage());
        routerManager.addRoute("chats", new ChatPage());
        routerManager.addRoute("addFriend", new AddFriendPage());
        routerManager.addRoute("scanner", new ScannerPage());
    }
}
