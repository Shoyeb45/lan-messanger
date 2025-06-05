package main.java.com.lanmessanger.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.java.com.lanmessanger.ui.state.StateManager;

/**
 * Friend class represents a collection of users that are friends with the current user
 * 
 * @author Shoyeb Ansari
 */
public class Friend {
    /** List to store friend users */
    private HashSet<User> friends;
    private List<StateManager> subscribedComponents; 

    /**
     * Constructor to initialize Friend class with an empty ArrayList
     */
    public Friend() {
        this.friends = new HashSet<>();
        this.subscribedComponents = new ArrayList<StateManager>();
    }

    public void addSubscribedComponent(StateManager stateManager) {
        subscribedComponents.add(stateManager);
    }
    public void removeSubscribedComponent(StateManager stateManager) {
        subscribedComponents.remove(stateManager);
    }

    private void updateState() {
        for (StateManager subscribedComponent : subscribedComponents) {
            subscribedComponent.onStateChange();
        }
    }

    /**
     * Adds a new friend to the friends list. If a user is already present then it won't add and it will return {@code false} 
     * @param user the User object to add as a friend
     * @return true if user added successfully
     */
    public boolean addFriend(User user) {
        boolean added = friends.add(user);
        if (!added) {
            System.out.println("[Info] Friend with IP " + user.getIp() + " already exists.");
        }
        updateState();
        return added;
    }

    /**
     * Remove friend from the list
     * @param user the User object to be removed
     */
    public void removeFriend(User user) {
        this.friends.remove(user);
        updateState();
    }

    /**
     * Will check if the IP is present in the list or not
     * @param ip IP Address to check
     * @return true if the IP is present else false
     */
    public boolean hasFriendWithIp(String ip) {
        return friends.contains(new User(null, ip)); // only IP matters in equals()
    }

    /**
     * Gets an array of all friends
     * @return array of User objects representing all friends
     */
    public HashSet<User> getAllFriends() {
        return friends;
    }

    /**
     * 
     * @param friends HashSet of friends to set new friends list
     */
    public void setFriends(HashSet<User> friends) {
        if (friends == null) {
            return;
        }
        this.friends = friends;
    }
}