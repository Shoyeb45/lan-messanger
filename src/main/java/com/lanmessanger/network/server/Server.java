package main.java.com.lanmessanger.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import main.java.com.lanmessanger.models.User;
import main.java.com.lanmessanger.network.clientHandler.ClientHandler;
import main.java.com.lanmessanger.ui.utils.Dialog;

/**
 * Server class handles incoming connections from other users
 * It manages the server socket and spawns ClientHandler threads for each
 * connection
 */
public class Server extends Thread {
    /** The server socket instance */
    private ServerSocket serverSocket;

    /** Port to listen on */
    private int port;

    /** Flag to indicate if server is running */
    private boolean running = false;

    /** List of connected clients */
    private Map<String, ClientHandler> clientHandlers = new HashMap<>();

    /**
     * Constructor to initialize the server
     * @param port The port to listen on
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts the server thread to listen for incoming connections
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("[Server] Started on port " + port);

            // Continuous loop to accept new connections
            while (running) {
                try {
                    // This will block until a connection is received
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[Server] New connection from " +
                            clientSocket.getInetAddress().getHostAddress());

                    
                    // Add newely connected user to our state variable
                    // But check if the user is our friend or not
                    if (!main.java.com.lanmessanger.ui.state.State.friendsList.hasFriendWithIp(clientSocket.getInetAddress().getHostAddress())) {
                        addUser(clientSocket.getInetAddress().getHostAddress());
                    }

                    // Add them to cliend handler list 
                    ClientHandler handler = new ClientHandler(clientSocket);
                    clientHandlers.put(handler.getClientAddress(), handler);
                    handler.start();

                } catch (IOException e) {
                    if (running) {
                        System.err.println("[Server] Error accepting connection: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("[Server] Could not start server on port " + port + ": " + e.getMessage());
        }
    }

    /**
     * Stops the server and all client handler threads
     */
    public void stopServer() {
        running = false;

        // Close all client connections
        for (String ip : clientHandlers.keySet()) {
            clientHandlers.get(ip).close();
        }

        clientHandlers.clear();

        // Close the server socket
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("[Server] Server stopped");
        } catch (IOException e) {
            System.err.println("[Server] Error closing server socket: " + e.getMessage());
        }
    }

    public void addClient(Socket clientSocket) {
        ClientHandler handler = new ClientHandler(clientSocket);
        clientHandlers.put(handler.getClientAddress(), handler);
        handler.start();
    }

    public void sendMessage(String message, String ip) {
        System.out.println("[INFO] Sent message to " + ip + " , Message: " + message);
        clientHandlers.get(ip).sendMessage(message);
    }

    /**
     * Removes a client handler from the list
     * 
     * @param handler the handler to remove
     */
    public void removeClient(String ip) {
        clientHandlers.remove(ip);
    }

    /**
     * Check if server is running
     */
    public boolean isRunning() {
        return running;
    }

    public String[] getConnectedClients() {
        return (String[]) clientHandlers.keySet().toArray();
    }

    public void addUser(String ip) {
        // Create a new user and add to friend list
        String name = Dialog.showInputDialog(null, "Your friend with ID requested to be friend with you, please enter the name of your friend", "New friend request",  Dialog.QUESTION_MESSAGE);
        
        
        while (name == null || name.isBlank()) {
            name = Dialog.showInputDialog(null, "Your friend with IP " + ip + " requested to be friend with you, please enter the name of your friend", "New friend request",  Dialog.QUESTION_MESSAGE);
        
        }

        User user = new User(name, ip);
        main.java.com.lanmessanger.ui.state.State.friendsList.addFriend(user);
    }

    public boolean checkUserIsOnline(String ipAddress) {
        ClientHandler handler = clientHandlers.get(ipAddress);

        if (handler == null) {
            return false;
        }
        return handler.isConnected();
    }
}