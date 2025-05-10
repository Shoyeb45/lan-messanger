package main.java.com.lanmessanger.network.clientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler manages communication with a connected client
 */
public class ClientHandler extends Thread {
    /** Socket connection to the client */
    private Socket clientSocket;
    
    /** Stream for sending messages to the client */
    private PrintWriter out;
    
    /** Stream for receiving messages from the client */
    private BufferedReader in;
    
    /** Flag to indicate if handler is running */
    private boolean running = true;
    
    /** Client's remote address */
    private String clientAddress;
    
    /**
     * Constructor
     * @param socket Socket connected to the client
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.clientAddress = socket.getInetAddress().getHostAddress();
    }
    
    /**
     * Thread run method - handles communication with client
     */
    @Override
    public void run() {
        try {
            // Initialize input and output streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Send welcome message
            sendMessage("Connected to server successfully.");
            
            // Process messages from client
            String inputLine;
            while (running && (inputLine = in.readLine()) != null) {
                // Process the received message
                System.out.println("[Server] Received from " + clientAddress + ": " + inputLine);
                
                // Echo the message back (you could process it differently)
                sendMessage("Server received: " + inputLine);
                
                // Here you would typically process commands or chat messages
                // For example, checking if it's a friend request, chat message, etc.
            }
            
        } catch (IOException e) {
            if (running) {
                System.err.println("[Server] Error handling client " + clientAddress + ": " + e.getMessage());
            }
        } finally {
            close();
        }
    }
    
    /**
     * Sends a message to the client
     * @param message The message to send
     */
    public void sendMessage(String message) {
        if (out != null && !clientSocket.isClosed()) {
            out.println(message);
            System.out.println("[Server] Sent to " + clientAddress + ": " + message);
        }
    }
    
    /**
     * Closes the connection with the client
     */
    public void close() {
        running = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            
            System.out.println("[Server] Connection closed with " + clientAddress);
        } catch (IOException e) {
            System.err.println("[Server] Error closing connection: " + e.getMessage());
        }
    }
}