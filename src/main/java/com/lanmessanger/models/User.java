package main.java.com.lanmessanger.models;

import main.java.com.lanmessanger.ui.components.addFriendPage.BottomPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.CenterPanel;

/**
 * Class for intialising User object which will represent the user of LAN Messanger application.
 * @author Shoyeb Ansari
 */
public class User {

    private String name;
    private String ip;
    private boolean isOnline = false;

    /**
     * Empty constructor
     */
    public User() { }

    /**
     * Consturctor to initialise User object with name and ip 
     * @param name Name of the user
     * @param ip   Ip of the user
     */
    public User(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    /**
     * method to get the name of the user
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * method to get the IP adress of the user
     * @return IP adress of the user
     */
    public String getIp() {
        return ip;
    }



    /**
     * Sets name for the user
     * @param name name of the user
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Sets an IP for the user
     * @param ip IP of the user
     */
    public void setIp(String ip) {
        if (!BottomPanel.isValidIPAddress(ip)) {
            System.out.println("[Error] Not a valid IP Address");
            return;
        }
        this.ip = ip;
    }

    @Override
    public String toString() {
        String output = "Name: " + this.name + "\n" + "IP Address: " + this.ip;
        return output;
    }

    public boolean isOnline() {
        return isOnline;
    }
    
}