package main.java.com.lanmessanger.ui.router;

/**
 * RouterManager.java - Simplified for high-level navigation only
 */

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

public class RouterManager {
    private static RouterManager instance;
    private Container contentPanel;
    private CardLayout cardLayout;
    private Map<String, Component> routes = new HashMap<>();
    private String currentRoute;

    private RouterManager() {
        // Private constructor for singleton
    }

    public static RouterManager getInstance() {
        if (instance == null) {
            instance = new RouterManager();
        }
        return instance;
    }

    public void setContentPanel(Container panel) {
        this.contentPanel = panel;
        this.cardLayout = (CardLayout) panel.getLayout();
    }

    public void addRoute(String routeName, Component component) {
        routes.put(routeName, component);
        contentPanel.add(component, routeName);
    }

    public void navigateTo(String routeName) {
        if (routes.containsKey(routeName)) {
            cardLayout.show(contentPanel, routeName);
            currentRoute = routeName;
        } else {
            System.err.println("Route not found: " + routeName);
        }
    }

    public String getCurrentRoute() {
        return currentRoute;
    }
}