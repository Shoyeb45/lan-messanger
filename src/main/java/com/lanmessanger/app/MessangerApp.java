package main.java.com.lanmessanger.app;

import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import main.java.com.lanmessanger.network.discovery.DiscoverResponser;

/**
 * Main application class for the LAN Messenger
 * This class serves as the entry point for the application
 * @author Shoyeb Ansari
 */
public class MessangerApp {
    /**
     * Main method that starts the messenger application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        DiscoverResponser dr = new DiscoverResponser();
        dr.start();
    }
}