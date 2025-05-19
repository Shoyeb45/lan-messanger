package main.java.com.lanmessanger.ui.pages;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.java.com.lanmessanger.ui.router.RouterManager;

public class MainPage extends JPanel {

    private NavigationPage navigationPage;
    private RouterManager routerManager;
    private JPanel contentPanel;
    
    public MainPage() {
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
        routerManager.navigateTo("addFriend");


        this.setBackground(Color.green);
        
        // this.setBounds(0, 0, getWidth(), 100);
        // this.setBorder(BorderFactory.createLineBorder(Color.black));


        // Add components to main page
        add(navigationPage, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
   
   
    

    private void addRoutes() {
        // Add all routes here
        routerManager.addRoute("start", new StartPage());
        routerManager.addRoute("chats", new ChatPage());
        routerManager.addRoute("addFriend", new AddFriendPage());
        routerManager.addRoute("scanner", new ScannerPage());
        routerManager.addRoute("friends", new FriendsPage());

    }
}
