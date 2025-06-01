package main.java.com.lanmessanger.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import main.java.com.lanmessanger.app.AppConfig;
import main.java.com.lanmessanger.ui.components.addFriendPage.BottomPanel;

/**
 * Client handles outgoing connections to other users
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String remoteIP;
    private int remotePort = AppConfig.SERVER_PORT;
    private boolean isConnected = false;
    private ClientListener listener;
    
    public Client() {}
    /**
     * Constructor that stores the remote host details but doesn't connect yet
     */
    public Client(String ip, int port) {
        this.remoteIP = ip;
        this.remotePort = port;
    }

    /**
     * Establishes connection to the remote server
     * @return true if connection successful, false otherwise
     */
    public boolean connect() {
        try {
            clientSocket = new Socket(remoteIP, remotePort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            isConnected = true;
            
            // Start a listener thread to receive messages
            // startListening();
            
            System.out.println("Connected to " + remoteIP + ":" + remotePort);
            return true;
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + remoteIP);
            return false;
        } catch (IOException e) {
            System.err.println("Error connecting to " + remoteIP + ":" + remotePort + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Starts a thread to listen for incoming messages from the server
     */
    private void startListening() {
        listener = new ClientListener();
        listener.start();
    }
    
    /**
     * Inner class to continuously listen for messages from server
     */
    private class ClientListener extends Thread {
        private boolean running = true;
        
        @Override
        public void run() {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    System.out.println("Received from server: " + message);
                    // Here you would typically notify the UI or message handler
                    // This could be done via a callback or observer pattern
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Connection error: " + e.getMessage());
                }
            }
        }
        
        public void stopRunning() {
            running = false;
        }
    }

    /**
     * Sends a message to the server
     * @param message the message to send
     * @return true if sent successfully, false otherwise
     */
    public boolean sendMessage(String message) {
        if (!isConnected) {
            System.err.println("Not connected to any server");
            return false;
        }
        
        try {
            out.println(message);
            System.out.println("Sent to server: " + message);
            return true;
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Closes the connection
     */
    public void disconnect() {
        try {
            isConnected = false;
            
            if (listener != null) {
                listener.stopRunning();
            }
            
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            
            System.out.println("Disconnected from server");
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }
    
    /**
     * Check if client is connected
     */
    public boolean isConnected() {
        return isConnected;
    }
    
    public void setRemoteIp(String remoteIp) {
        if (!BottomPanel.isValidIPAddress(remoteIp)) {
            System.out.println("[Error]" + remoteIp + " is not a valid IP address");
            return;
        }
        
        if (isConnected()) {
            disconnect();
        }
        this.remoteIP = remoteIp;
    }
}