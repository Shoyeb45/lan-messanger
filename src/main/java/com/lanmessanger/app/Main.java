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
        try {
            Thread uiThread = new Thread(new App());   

            // name the threads for better error handling
            uiThread.setName("Thread-UI");
            discoverResponser.setName("Thread-UDP-Responser");
            discoverResponser.setName("Thread-Server");

            // Start the UI in separate thread
            uiThread.start();        
            
            // start the server and UDP discover responder class in separate threads
            discoverResponser.start();
            server.start();
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to start Main class\nError Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
