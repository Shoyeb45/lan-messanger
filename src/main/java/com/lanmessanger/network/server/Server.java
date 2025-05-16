package main.java.com.lanmessanger.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.java.com.lanmessanger.network.clientHandler.ClientHandler;

/**
 * Server class handles incoming connections from other users
 * It manages the server socket and spawns ClientHandler threads for each connection
 */
public class Server extends Thread {
    /** The server socket instance */
    private ServerSocket serverSocket;
    
    /** Port to listen on */
    private int port;
    
    /** Flag to indicate if server is running */
    private boolean running = false;
    
    /** List of connected clients */
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    
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
                    
                    // Create a new handler thread for this client
                    ClientHandler handler = new ClientHandler(clientSocket);
                    clientHandlers.add(handler);
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
        for (ClientHandler handler : clientHandlers) {
            handler.close();
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
    
    /**
     * Sends a message to all connected clients
     * @param message the message to broadcast
     */
    public void broadcastMessage(String message) {
        for (ClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }
    
    public void sendMessage(String message, String ip) {
        for (ClientHandler handler : clientHandlers) {
            if (handler.getClientAddress().equals(ip)) {
                handler.sendMessage(message);
                break;
            }
        }
    }
    /**
     * Removes a client handler from the list
     * @param handler the handler to remove
     */
    public void removeClient(ClientHandler handler) {
        clientHandlers.remove(handler);
    }
    
    /**
     * Check if server is running
     */
    public boolean isRunning() {
        return running;
    }

    public ArrayList<String> getConnectedClients() {
        ArrayList<String> connectedClients = new ArrayList<String>();

        for (ClientHandler handler: clientHandlers) {
            connectedClients.add(handler.getClientAddress());
        }
        return connectedClients;
    }
}