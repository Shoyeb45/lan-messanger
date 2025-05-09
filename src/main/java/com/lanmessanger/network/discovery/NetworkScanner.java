package main.java.com.lanmessanger.network.discovery;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class NetworkScanner {

    public void scanForUsers(String subnetPrefix) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100); // max 100 concurrent scans

        for (int i = 1; i < 255; i++) {
            final String ip = subnetPrefix + i;
            executor.submit(() -> {
                try (DatagramSocket socket = new DatagramSocket()) {
                    socket.setSoTimeout(1000);  // 1 second timeout
                    byte[] data = "Hello".getBytes();

                    DatagramPacket packet = new DatagramPacket(
                        data, data.length,
                        InetAddress.getByName(ip), 8888
                    );

                    socket.send(packet);

                    byte[] buffer = new byte[1024];
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length);

                    socket.receive(response);  // Will timeout if no response

                    String message = new String(response.getData(), 0, response.getLength());
                    System.out.println("[Response] " + ip + " responded: " + message);

                } catch (SocketTimeoutException e) {
                    // No response - ignore or log
                } catch (Exception e) {
                    System.err.println("[!] Error at " + ip + ": " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Scan complete.");
    }

    public static void main(String[] args) throws InterruptedException, SocketException {
        NetworkScanner nw = new NetworkScanner();
        String myIp;
        String subnetPrefix;;
        {List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                ArrayList<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        myIp = address.getHostAddress();
                        subnetPrefix = myIp.substring(0, myIp.lastIndexOf('.') + 1);
                        System.out.println("My IP: " + address.getHostAddress());
                        nw.scanForUsers(subnetPrefix);
                    }
                }
            }

        }
    }
}
