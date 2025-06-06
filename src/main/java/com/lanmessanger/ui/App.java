package main.java.com.lanmessanger.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.database.DatabaseOperations;
import main.java.com.lanmessanger.ui.pages.MainPage;
import main.java.com.lanmessanger.ui.state.State;

/** Main UI Application frame */
public class App extends JFrame implements Runnable {

    /**
     * main page of the application
     */
    private MainPage mainPage;
    public static DatabaseOperations dbOperations = new DatabaseOperations();

    public App() {
        setFavico();
        this.setTitle("LAN Messanger - A peer to peer to chatting platform");

        // To allow the resizing of the window
        this.setResizable(true);

        // The close button will close the application
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // This will maximize the size of the application at start
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // When app window is minized, then it will have the given size
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 3,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        // To prevent the resizing below this Dimension3
        this.setMinimumSize(new Dimension(AppConfig.SMALL_WIDTH + 110, 800));

        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call your cleanup or logout function here
                onAppClose();
                // Then exit the app if needed
                System.exit(0);
            }
        });

        // Add shutdown hook as backup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[INFO] Shutdown hook triggered, saving data...");
            saveDataToFiles();
        }));
    }

    /** It will save the memory data into json files on closing the application */
    private void onAppClose() {
        System.out.println("[INFO] App closing, attempting to save data...");
        saveDataToFiles();
    }

    /** Separate method to handle data saving */
    private void saveDataToFiles() {
        try {
            // Debug: Check if State objects are null
            if (State.friendsList == null) {
                System.out.println("[WARNING] State.friendsList is null, cannot save friends data");
            } else {
                System.out.println("[INFO] Saving friends list...");
                DatabaseOperations.writeToFriend(State.friendsList.getAllFriends());
                System.out.println("[INFO] Friends list saved successfully");
            }

            if (State.messageHistory == null) {
                System.out.println("[WARNING] State.messageHistory is null, cannot save message history");
            } else {
                System.out.println("[INFO] Saving message history...");
                DatabaseOperations.writeToMessageHistory(State.messageHistory.getAllConversations());
                System.out.println("[INFO] Message history saved successfully");
            }

            System.out.println("[INFO] All write operations completed successfully");
        } catch (Exception e) {
            System.out.println("[ERROR] Write operation on json file unsuccessful");
            System.out.println("[ERROR] Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
  
    /**
     * Method to set favico of the app
     */
    private void setFavico() {
        try {
            ImageIcon favico = new ImageIcon("./public/images/app/appFavico.png");
            Image image = favico.getImage().getScaledInstance(52, 52, Image.SCALE_SMOOTH);
            this.setIconImage(new ImageIcon(image).getImage());
        } catch (Exception e) {
            System.out.println("[WARNING] Could not load app icon: " + e.getMessage());
        }
    }


    @Override
    /** Method which will intialise the UI of the application */
    public void run() {
        try {
            mainPage = new MainPage();
        } catch (InterruptedException e) {
            System.out.println("[ERROR] Failed to start the UI\nMessage: " + e.getMessage());
        }
        mainPage.setBounds(0, 0, 100, 100);
        getContentPane().add(mainPage);
        setVisible(true);
    }

   
}