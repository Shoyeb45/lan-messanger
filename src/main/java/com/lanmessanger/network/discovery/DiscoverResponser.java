package main.java.com.lanmessanger.network.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * DiscoverResponser handles UDP responses for network discovery
 * It listens for "Hello" messages and responds with "World"
 * @author Shoyeb Ansari
 */
public class DiscoverResponser extends Thread {
    /**
     * Runs the UDP server that listens for discovery messages
     * Responds to "Hello" messages with "World"
     */
    public void run() {
        try {
            
            // UDP Socket to receive the data and send the response back
            DatagramSocket socket = new DatagramSocket(8888);
            System.out.println("[Info] UDP Responding Server started");
            byte[] buffer = new byte[5];  // To receive Hello
            
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            String received = new String(buffer, 0, buffer.length);

            if (received.compareTo("Hello") == 0) {
                byte[] responseBuffer = "World".getBytes();
                
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, packet.getAddress(), packet.getPort());
                System.out.println("[Info] UDP Responding Server sent the response");
                socket.send(responsePacket);
            }
            socket.close();
            System.out.println("[Info] UDP Responding Server closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the discovery responder in a new thread
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Thread ds = new Thread(new DiscoverResponser());
        ds.start();
    }
}
