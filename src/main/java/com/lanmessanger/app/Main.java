package main.java.com.lanmessanger.app;

import main.java.com.lanmessanger.network.discovery.DiscoverResponser;
import main.java.com.lanmessanger.network.server.Server;
/** Imports */
import main.java.com.lanmessanger.ui.App;

/**
 * Main class from where the app will start
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        App app = new App();    // A parent element of all the components and pages of the application
        app.init();
        DiscoverResponser discoverResponser = new DiscoverResponser();
        discoverResponser.start();
        Server server = new Server(AppConfig.SERVER_PORT);
        server.start();
    }
}
