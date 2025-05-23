package main.java.com.lanmessanger.network.discovery;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

public class SubnetScanner {
    // This class focuses specifically on finding all possible subnets
    // that might contain devices, regardless of OS


    public List<String> getAllPossibleSubnets() {
        List<String> subnets = new ArrayList<>();
        
        try {
            // Method 1: Get subnets from local network interfaces
            subnets.addAll(getLocalInterfaceSubnets());
            
            // Method 2: Get common subnets used in private networks
            subnets.addAll(getCommonPrivateSubnets());
            
            // Method 3: Analyze routing table
            subnets.addAll(getRoutingTableSubnets());
            
            // Remove duplicates
            return new ArrayList<>(new HashSet<>(subnets));
            
        } catch (Exception e) {
            System.err.println("Error getting subnets: " + e.getMessage());
            e.printStackTrace();
            return getCommonPrivateSubnets(); // Fallback to common subnets
        }
    }
    
    private List<String> getLocalInterfaceSubnets() throws SocketException {
        List<String> subnets = new ArrayList<>();
        
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            
            // Skip loopback, virtual or disabled interfaces
            if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.isVirtual()) {
                continue;
            }
            
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress address : interfaceAddresses) {
                InetAddress inetAddress = address.getAddress();
                
                // Only interested in IPv4 addresses
                if (inetAddress instanceof Inet4Address) {
                    String hostAddress = inetAddress.getHostAddress();
                    
                    // Calculate subnet
                    String subnet = hostAddress.substring(0, hostAddress.lastIndexOf('.') + 1);
                    
                
                    subnets.add(subnet);
                }
            }
        }
        
        return subnets;
    }
    
    private List<String> getCommonPrivateSubnets() {
        // Common private network subnets
        List<String> commonSubnets = new ArrayList<>();
    
        // Common home router subnets
        commonSubnets.add("192.168.0.");
        commonSubnets.add("192.168.1.");
        commonSubnets.add("192.168.2.");
        commonSubnets.add("192.168.10.");
        commonSubnets.add("192.168.100.");

        return commonSubnets;
    }
    
    @SuppressWarnings("deprecation")
    private List<String> getRoutingTableSubnets() {
        // This method tries to analyze the system's routing table
        List<String> subnets = new ArrayList<>();
        
        try {
            Process process;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Windows
                process = Runtime.getRuntime().exec("route print");
            } else {
                // Linux/macOS
                process = Runtime.getRuntime().exec("netstat -nr");
            }
            
            // Parse the output
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                // Look for IP address patterns in routing table
                if (line.matches(".*\\d+\\.\\d+\\.\\d+\\.\\d+.*")) {
                    // Extract potential subnet prefixes
                    String[] parts = line.trim().split("\\s+");
                    for (String part : parts) {
                        if (part.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                            // Get the first three octets for a /24 subnet
                            String[] octets = part.split("\\.");
                            if (octets.length == 4) {
                                String subnet = octets[0] + "." + octets[1] + "." + octets[2] + ".";
                                // Add if it looks like a valid subnet
                                if (!subnet.equals("0.0.0.") && !subnet.equals("127.0.0.") && 
                                    !subnet.startsWith("255.") && !subnet.endsWith(".255.")) {
                                    subnets.add(subnet);
                                }
                            }
                        }
                    }
                }
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.err.println("Error analyzing routing table: " + e.getMessage());
        }
        
        return subnets;
    }
    
    public void testReachability(String subnet) {
        // Test if the first few IPs in this subnet are reachable
        boolean anyReachable = false;
        
        for (int i = 1; i <= 5; i++) {
            String ip = subnet + i;
            try {
                if (InetAddress.getByName(ip).isReachable(1000)) {
                    System.out.println("Subnet " + subnet + " is valid - " + ip + " is reachable");
                    anyReachable = true;
                    break;
                }
            } catch (Exception e) {
                // Ignore errors
            }
        }
        
        if (!anyReachable) {
            // System.out.println("Subnet " + subnet + " appears unreachable");
        }
    }
}