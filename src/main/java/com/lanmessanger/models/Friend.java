package main.java.com.lanmessanger.models;

import java.util.ArrayList;

/**
 * Friend class represents a collection of users that are friends with the current user
 * 
 * @author Shoyeb Ansari
 */
public class Friend {
    /** List to store friend users */
    private ArrayList<User> friends;

    /**
     * Constructor to initialize Friend class with an empty ArrayList
     */
    public Friend() {
        this.friends = new ArrayList<User>();
    }

    /**
     * Gets the total number of friends (used as user ID)
     * 
     * @return the number of friends as an integer
     */
    public int getUserId() {
        return friends.size();
    }

    /**
     * Adds a new friend to the friends list
     * 
     * @param user the User object to add as a friend
     */
    public void addFriend(User user) {
        this.friends.add(user);
    }

    /**
     * Gets an array of all friends
     * 
     * @return array of User objects representing all friends
     */
    public User[] getAllFriends() {
        return friends.toArray(new User[0]);
    }
}