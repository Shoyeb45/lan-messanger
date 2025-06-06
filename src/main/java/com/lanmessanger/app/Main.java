package main.java.com.lanmessanger.app;

import main.java.com.lanmessanger.network.discovery.DiscoverResponser;
import main.java.com.lanmessanger.network.server.Server;
/** Imports */
import main.java.com.lanmessanger.ui.App;

/**
 * Main class from where the app will start
 */
public class Main {
    public static Server server = new Server(AppConfig.SERVER_PORT);
    public static DiscoverResponser discoverResponser = new DiscoverResponser();

    public static void main(String[] args) throws InterruptedException {
        Thread uiThread = new Thread(new App());    // A parent element of all the components and pages of the application
        uiThread.start();        
        // start the server and UDP discover responder class
        discoverResponser.start();
        server.start();

        

    }
}
