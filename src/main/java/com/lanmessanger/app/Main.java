package main.java.com.lanmessanger.app;

import main.java.com.lanmessanger.database.DatabaseOperations;
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
        App app = new App();    // A parent element of all the components and pages of the application
        app.init();

        // start the server and UDP discover responder class
        discoverResponser.start();
        server.start();
    }
}
