package main.java.com.lanmessanger.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import main.java.com.lanmessanger.network.client.Client;
import main.java.com.lanmessanger.network.server.Server;

/**
 * Demo class to demonstrate both client and server functionality running simultaneously
 */
public class MessengerDemo {
    
    private static final int SERVER_PORT = 9000;
    private static Server server;
    private static Client client;
    private static boolean running = true;
    
    public static void main(String[] args) {
        // Start the server in a separate thread
        startServer();
        
        // Main thread handles console input for commands
        processUserCommands();
    }
    
    /**
     * Start the server thread
     */
    private static void startServer() {
        server = new Server(SERVER_PORT);
        server.start();
        System.out.println("Server started on port " + SERVER_PORT);
    }
    
    /**
     * Process user commands from console
     */
    private static void processUserCommands() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=== LAN Messenger Demo ===");
        System.out.println("Available commands:");
        System.out.println("  connect <ip> - Connect to a server at the specified IP");
        System.out.println("  send <message> - Send a message to the connected server");
        System.out.println("  disconnect - Disconnect from the current server");
        System.out.println("  exit - Exit the application");
        System.out.println("==============================\n");
        
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.startsWith("connect")) {
                handleConnectCommand(input);
            } else if (input.startsWith("send")) {
                handleSendCommand(input);
            } else if (input.equals("disconnect")) {
                handleDisconnectCommand();
            } else if (input.equals("exit")) {
                handleExitCommand();
            } else {
                System.out.println("Unknown command. Try 'connect', 'send', 'disconnect', or 'exit'.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Handle the connect command
     */
    private static void handleConnectCommand(String input) {
        // Parse the IP from the command
        String[] parts = input.split(" ", 2);
        if (parts.length != 2) {
            System.out.println("Usage: connect <ip>");
            return;
        }
        
        String ip = parts[1].trim();
        
        // Disconnect existing connection if any
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
        
        // Create and connect the client
        System.out.println("Connecting to " + ip + ":" + SERVER_PORT + "...");
        client = new Client(ip, SERVER_PORT);
        boolean success = client.connect();
        
        if (success) {
            System.out.println("Connected successfully! You can now send messages.");
        } else {
            System.out.println("Connection failed.");
            client = null;
        }
    }
    
    /**
     * Handle the send command
     */
    private static void handleSendCommand(String input) {
        // Parse the message from the command
        String[] parts = input.split(" ", 2);
        if (parts.length != 2) {
            System.out.println("Usage: send <message>");
            return;
        }
        
        String message = parts[1].trim();
        
        // Check if client is connected
        if (client == null || !client.isConnected()) {
            System.out.println("Not connected to any server. Use 'connect <ip>' first.");
            return;
        }
        
        // Send the message
        boolean sent = client.sendMessage(message);
        if (!sent) {
            System.out.println("Failed to send message.");
        }
    }
    
    /**
     * Handle the disconnect command
     */
    private static void handleDisconnectCommand() {
        if (client != null && client.isConnected()) {
            client.disconnect();
            System.out.println("Disconnected from server.");
            client = null;
        } else {
            System.out.println("Not connected to any server.");
        }
    }
    
    /**
     * Handle the exit command
     */
    private static void handleExitCommand() {
        running = false;
        
        // Clean up resources
        if (client != null) {
            client.disconnect();
        }
        
        if (server != null) {
            server.stopServer();
        }
        
        System.out.println("Exiting application...");
    }
}