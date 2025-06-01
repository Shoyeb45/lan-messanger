package main.java.com.lanmessanger.network.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import main.java.com.lanmessanger.app.AppConfig;

/**
 * DiscoverResponser handles UDP responses for network discovery
 * It listens for "Hello" messages and responds with "World"
 * @author Shoyeb Ansari
 */
public class DiscoverResponser extends Thread {
    private volatile boolean running = true;
    private DatagramSocket socket;
    
    /**
     * Runs the UDP server that listens for discovery messages
     * Responds to "Hello" messages with "World"
     */
    public void run() {
        try {
            // UDP Socket to receive the data and send the response back
            socket = new DatagramSocket(AppConfig.UDP_PORT);
            System.out.println("[Info] UDP Responding Server started on port 8888");
            
            while (running) {
                try {
                    byte[] buffer = new byte[1024];  // Increased buffer size
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    socket.receive(packet);
                    
                    String received = new String(packet.getData(), 0, packet.getLength()).trim();
                    String senderIP = packet.getAddress().getHostAddress();
                    
                    System.out.println("[Info] Received '" + received + "' from " + senderIP + ":" + packet.getPort());

                    if ("Hello".equals(received)) {
                        byte[] responseBuffer = "World".getBytes();
                        
                        DatagramPacket responsePacket = new DatagramPacket(
                            responseBuffer, 
                            responseBuffer.length, 
                            packet.getAddress(), 
                            packet.getPort()
                        );
                        
                        socket.send(responsePacket);
                        System.out.println("[Info] Sent 'World' response to " + senderIP + ":" + packet.getPort());
                    } else {
                        System.out.println("[Warning] Received unexpected message: '" + received + "'");
                    }
                    
                } catch (IOException e) {
                    if (running) {
                        System.err.println("[Error] Error in UDP communication\nMessage: " + e.getMessage());
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("[Error] Failed to start UDP server\nMessage: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("[Info] UDP Responding Server closed");
            }
        }
    }
    
    /**
     * Stop the UDP server gracefully
     */
    public void stopServer() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    
    // /**
    //  * Main method to start the discovery responder in a new thread
    //  * @param args command line arguments (not used)
    //  */
    // public static void main(String[] args) {
    //     DiscoverResponser responder = new DiscoverResponser();
    //     Thread serverThread = new Thread(responder);
    //     serverThread.start();
        
    //     // Add shutdown hook for graceful cleanup
    //     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    //         System.out.println("[Info] Shutting down UDP server...");
    //         responder.stopServer();
    //     }));
        
    //     // Keep main thread alive
    //     try {
    //         serverThread.join();
    //     } catch (InterruptedException e) {
    //         System.err.println("Main thread interrupted");
    //     }
    // }
}