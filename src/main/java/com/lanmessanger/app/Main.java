package main.java.com.lanmessanger.app;

/** Imports */
import main.java.com.lanmessanger.ui.App;

/**
 * Main class from where the app will start
 */
public class Main {
    public static void main(String[] args) {
        App app = new App();    // A parent element of all the components and pages of the application
        app.init();
    }
}
