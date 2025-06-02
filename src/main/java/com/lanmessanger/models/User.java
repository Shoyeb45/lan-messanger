package main.java.com.lanmessanger.models;

/**
 * Class for intialising User object which will represent the user of LAN Messanger application.
 * @author Shoyeb Ansari
 */
public class User {

    private int id;
    private String name;
    private String ip;

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
     * method to get the ip of the user
     * @return ip of the user
     */
    public String getIp() {
        return ip;
    }

    /**
     * method to get the ID of the user
     * @return ID of the user
     */
    public int getId() {
        return id;
    }


    /**
     * Sets an id for the user
     * @param id id of the user
     */
    public void setId(int id) {
        if (id < 0) {
            System.err.println("[Error] Negative id is not valid");
            return;
        }

        this.id = id;   
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
        this.ip = ip;
    }

    @Override
    public String toString() {
        String output = "Name: " + this.name + "\n" + "IP Address: " + this.ip;
        return output;
    }
}