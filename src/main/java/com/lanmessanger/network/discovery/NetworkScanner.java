package main.java.com.lanmessanger.network.discovery;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * NetworkScanner class handles the discovery of other users on the local network
 * by scanning IP addresses and sending UDP packets
 * @author Shoyeb Ansari
 */
public class NetworkScanner {
    public boolean scanIp(String ip) {
        try {
            DatagramSocket socket = new DatagramSocket();

            socket.setSoTimeout(1000); // 1 second timeout
            byte[] data = "Hello".getBytes();

            DatagramPacket packet = new DatagramPacket(
                    data, 
                    data.length,
                    InetAddress.getByName(ip),
                    8888
            );

            socket.send(packet);

            byte[] buffer = new byte[5];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            
            socket.receive(response); // Will timeout if no response
            String message = new String(response.getData(), 0, response.getLength());

            socket.close();
            if (message.equals("World")) {
                return true;
            }
            return false;
        } catch (SocketTimeoutException e) {
            return false;
        } catch(Exception e) {
            System.err.println("[!] Error at " + ip + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Scans for users on a given subnet by sending UDP packets to each IP
     * @param subnetPrefix the subnet to scan (e.g. "192.168.1.")
     * @throws InterruptedException if the scanning thread is interrupted
     */
    public ArrayList<String> scanForUsers(String subnetPrefix) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100); // max 100 concurrent scans
        ArrayList<String> nearbyDevices = new ArrayList<String>();


        for (int i = 1; i < 255; i++) {
            final String ip = subnetPrefix + i;

            executor.submit(() -> {
                if (scanIp(ip)) {
                    nearbyDevices.add(ip);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Scan completed for subnet " + subnetPrefix);
        return nearbyDevices;
    }

    /**
     * Main method to test the network scanner
     * @param args command line arguments (not used)
     * @throws InterruptedException if the scanning thread is interrupted
     * @throws SocketException if there is an error creating the socket
     */
    public static void main(String[] args) throws InterruptedException, SocketException {
        NetworkScanner nw = new NetworkScanner();
        // Set<String> subnets = new HashSet<String>();
        // subnets.add("10.108.169.");

        // subnets.addAll(new SubnetScanner().getAllPossibleSubnets());
        if (nw.scanIp("127.0.0.1")) {
            System.out.println("Connected");
        }
        // nw.scanIp("10.108.")
        // for (var x : subnets) {
        //     nw.scanForUsers(x);
        // }
    }

    /**
     * Method to get all the nearby devices
     * @return arraylist of all the nearby devices
     */
    public ArrayList<String> getAllActiveDevices() throws InterruptedException {
        ArrayList<String> nearbyDevice = new ArrayList<>();
        NetworkScanner networkScanner = new NetworkScanner();
        Set<String> subnets = new HashSet<String>();
        subnets.add("10.108.174.");
        subnets.add("10.108.169.");

        subnets.addAll(new SubnetScanner().getAllPossibleSubnets());
        

        for (String subnet : subnets) {
            nearbyDevice.addAll(networkScanner.scanForUsers(subnet));
        }

        return nearbyDevice;
    }
    
}
