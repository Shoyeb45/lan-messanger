package main.java.com.lanmessanger.ui.router;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class, which handles the routing of the application and effectively making the modern single page application. 
 * @author Shoyeb Ansari
 */
public class RouterManager {
    /** The single object throught the application */
    private static RouterManager instance;
    /** The content panel of the app */
    private Container contentPanel;
    /** The dynamic rendering will happen by the help of this Layout Manager, it will help us to manage the dynamic rendering */
    private CardLayout cardLayout;
    /** {@code Map} data structure to map the components with name */
    private Map<String, Component> routes = new HashMap<>();
    /** Current route of the application */
    private String currentRoute;

    private RouterManager() {
        // Private constructor for singleton
    }

    /**
     * Method to get the {@code RouterManager} object
     * @return Singleton RouterManager object
     */
    public static RouterManager getInstance() {
        if (instance == null) {
            instance = new RouterManager();
        }
        return instance;
    }

    /**
     * Method to set the panel to the {@code contentPanel}
     * @param panel Panel which needs to be set
     */
    public void setContentPanel(Container panel) {
        this.contentPanel = panel;
        this.cardLayout = (CardLayout) panel.getLayout();
    }

    /**
     * Method to register the route
     * @param routeName Name of the route
     * @param component Component which needs to be mapped.
     * 
     * <p>Example usage:</p> 
     * <pre>
     *      RouterManager.getInstance().addRoute("name", component);
     * </pre>
     */
    public void addRoute(String routeName, Component component) {
        routes.put(routeName, component);
        contentPanel.add(component, routeName);
    }

    /**
     * Method to go to the specified route name
     * @param routeName Name of the route where you want to navigate
     */
    public void navigateTo(String routeName) {
        if (routes.containsKey(routeName)) {
            cardLayout.show(contentPanel, routeName);
            currentRoute = routeName;
        } else {
            System.err.println("[Error]Route not found: " + routeName);
        }
    }

    /**
     * Method to get the current route
     * @return Current route
     */
    public String getCurrentRoute() {
        return currentRoute;
    }
}