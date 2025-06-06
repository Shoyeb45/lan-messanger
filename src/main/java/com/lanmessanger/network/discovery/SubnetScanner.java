package main.java.com.lanmessanger.network.discovery;

import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * SubnetScanner class that comprehensively detects all possible subnets
 * across Windows, Linux, and macOS systems
 * @author Shoyeb Ansari with the help of Claude 4 Sonnet
 */
public class SubnetScanner {
    
    /**
     * Get all possible subnets from multiple detection methods
     * @return List of subnet prefixes (e.g., "192.168.1.")
     */
    public List<String> getAllPossibleSubnets() {
        Set<String> allSubnets = new HashSet<>();
        
        try {
            // Method 1: Network interfaces (most reliable)
            allSubnets.addAll(getNetworkInterfaceSubnets());
            
            // Method 2: System routing table analysis
            allSubnets.addAll(getRoutingTableSubnets());
            
            // Method 3: ARP table analysis
            allSubnets.addAll(getArpTableSubnets());
            
            // Method 4: Common private network ranges
            allSubnets.addAll(getCommonPrivateSubnets());
            
            // Method 5: OS-specific network discovery
            allSubnets.addAll(getOSSpecificSubnets());
            
            // Method 6: WiFi-specific subnet detection
            allSubnets.addAll(getWiFiSpecificSubnets());
            
        } catch (Exception e) {
            System.err.println("[Error] While getting getAllPossibleSubnets\nMessage: " + e.getMessage());
            e.printStackTrace();
        }
        
        List<String> result = new ArrayList<>(allSubnets);
        System.out.println("[Info] Found " + result.size() + " potential subnets to scan");
        return result;
    }
    
    /**
     * Get subnets from all network interfaces, focusing on WiFi adapters
     * @return All possible subnets of wifi adapters
     */
    private List<String> getNetworkInterfaceSubnets() {
        List<String> subnets = new ArrayList<>();
        
        try {
            // Enumeration of network interfaces in operating system 
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            // Loop over all the interface 
            while (interfaces.hasMoreElements()) {
                // get network interfacce 
                NetworkInterface networkInterface = interfaces.nextElement();
                
                // Skip loopback and down interfaces
                // [NOTE ABOUT LOOPBACK] The loopback network interface is a special, virtual network interface used 
                // primarily for testing and communication within a computer or 
                // device itself. It allows a machine to send network traffic to itself.
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                
                String interfaceName = networkInterface.getName().toLowerCase();  
                String displayName = networkInterface.getDisplayName() != null ? 
                    networkInterface.getDisplayName().toLowerCase() : "";
                
                // Check if this looks like a WiFi interface
                boolean isWiFiInterface = isWiFiInterface(interfaceName, displayName);
                
                // Get all IP addresses for this interface
                List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                
                for (InterfaceAddress address : addresses) {
                    InetAddress inetAddress = address.getAddress();
                    
                    // Only process IPv4 addresses
                    if (inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        
                        // Skip link-local addresses
                        // [Link-Local Address] - Link-local addresses are IP addresses that are only valid within 
                        // a single local network segment or link. They are automatically configured on every network 
                        // interface and cannot be routed beyond the local link, meaning they cannot communicate across routers.
                        if (ip.startsWith("169.254.")) {
                            continue;
                        }
                        
                        // Calculate subnet based on network prefix length
                        int prefixLength = address.getNetworkPrefixLength();
                        String subnet = calculateSubnet(ip, prefixLength);
                        
                        if (subnet != null) {
                            subnets.add(subnet);  
                            
                            if (isWiFiInterface) {
                                System.out.println("[Info] WiFi interface found: " + interfaceName + 
                                    " (" + displayName + ") - " + ip + "/" + prefixLength);
                            }
                        }
                    }
                }
            }
        // Handle error
        } catch (SocketException e) {
            System.err.println("[Error] while getting network interfaces \nMessage: " + e.getMessage());
        }
        
        return subnets;
    }
    
    /**
     * Check if interface name suggests it's a WiFi adapter
     * @return true if the interface is wifi related
     */
    private boolean isWiFiInterface(String interfaceName, String displayName) {
        // Common wifi related interface keywords
        String[] wifiKeywords = {
            "wlan", "wifi", "wireless", "wi-fi", "wl", "ath", "ra", "rt",
            "intel", "broadcom", "realtek", "qualcomm", "mediatek",
            "802.11", "wireless lan", "wi-fi adapter", "wireless adapter"
        };
        
        String combined = (interfaceName + " " + displayName).toLowerCase();
        
        // Check for that keyword
        for (String keyword : wifiKeywords) {
            if (combined.contains(keyword)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Calculate subnet prefix from IP and prefix length
     */
    private String calculateSubnet(String ip, int prefixLength) {
        try {
            String[] octets = ip.split("\\.");
            if (octets.length != 4) return null;
            
            // For most common cases, assume /24 network
            if (prefixLength >= 24) {
                return octets[0] + "." + octets[1] + "." + octets[2] + ".";
            } else if (prefixLength >= 16) {
                // /16 network - scan multiple /24 subnets
                String baseSubnet = octets[0] + "." + octets[1] + ".";
                return baseSubnet + octets[2] + "."; // Return current /24 within the /16
            }
            
            return octets[0] + "." + octets[1] + "." + octets[2] + ".";
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Get subnets from system routing table
     * <h3>Routing Table:</h3> <p>Routing table subnets refer to the destination subnets or IP ranges 
     * listed in a router's or host's routing table. A routing table tells the system where to 
     * send network packets, depending on their destination IP address.</p>
     */
    private List<String> getRoutingTableSubnets() {
        List<String> subnets = new ArrayList<>();
        String os = System.getProperty("os.name").toLowerCase(); // get os Name
        
        try {
            Process process;
            if (os.contains("win")) {
                // windows operating system
                process = Runtime.getRuntime().exec("route print");
            } else if (os.contains("mac")) {
                // Mac Operating System
                process = Runtime.getRuntime().exec("netstat -rn");
            } else {
                // Linux
                process = Runtime.getRuntime().exec(new String[]{"bash", "-c", "ip route show || netstat -rn"});
            }

            // Read the output from the process using BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            // Extract all the IPs
            while ((line = reader.readLine()) != null) {
                Set<String> lineSubnets = extractSubnetsFromLine(line, os);
                subnets.addAll(lineSubnets);
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.err.println("[Error] While reading routing table: \nMessage: " + e.getMessage());
        }
        
        return subnets;
    }
    
    /**
     * Extract potential subnets from routing table line
     */
    private Set<String> extractSubnetsFromLine(String line, String os) {
        Set<String> subnets = new HashSet<>();
        
        // Look for IP addresses in the line
        String[] parts = line.trim().split("\\s+");
        
        for (String part : parts) {
            if (part.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(/\\d{1,2})?")) {
                String ip = part.split("/")[0];
                
                // Skip invalid IPs
                if (isValidPrivateIP(ip)) {
                    String subnet = ip.substring(0, ip.lastIndexOf('.') + 1);
                    subnets.add(subnet);
                }
            }
        }
        
        return subnets;
    }
    
    /**
     * Get subnets from ARP table
     * <h3>ARP (Address Resolution Protocol) Table</h3>
     * <ul>
     * <li>The ARP table maps IP addresses to MAC (hardware) addresses.</li>
     * <li>It’s used only for IPv4 and only within the local network segment (broadcast domain).</li>
     * </ul>
     */
    private List<String> getArpTableSubnets() {
        List<String> subnets = new ArrayList<>();
        String os = System.getProperty("os.name").toLowerCase(); // get OS Name
        
        try {
            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("arp -a");
            } else {
                process = Runtime.getRuntime().exec("arp -a");
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Extract IP addresses from ARP entries
                if (line.matches(".*\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*")) {
                    String[] parts = line.split("\\s+");
                    for (String part : parts) {
                        if (part.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                            if (isValidPrivateIP(part)) {
                                String subnet = part.substring(0, part.lastIndexOf('.') + 1);
                                subnets.add(subnet);
                            }
                        }
                    }
                }
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.err.println("[Error] While reading ARP table \nMessage: " + e.getMessage());
        }
        
        return subnets;
    }
    
    /**
     * Get common private network subnets
     */
    private List<String> getCommonPrivateSubnets() {
        return Arrays.asList(
            // Class A private (10.0.0.0/8) - common ranges
            "10.0.0.", "10.0.1.", "10.1.1.", "10.10.10.",
            
            // Class B private (172.16.0.0/12) - common ranges  
            "172.16.0.", "172.16.1.", "172.20.10.",
            
            // Class C private (192.168.0.0/16) - most common
            "192.168.0.", "192.168.1.", "192.168.2.", "192.168.10.", 
            "192.168.11.", "192.168.100.", "192.168.101.", "192.168.254.",
            
            // Common router defaults
            "192.168.0.", "192.168.1.", "10.0.0.", "172.16.1."
        );
    }
    
    /**
     * Get OS-specific network subnets
     */
    private List<String> getOSSpecificSubnets() {
        List<String> subnets = new ArrayList<>();
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            // skip
        } else if (os.contains("mac")) {
            subnets.addAll(getMacSpecificSubnets());
        } else {
            subnets.addAll(getLinuxSpecificSubnets());
        }
        
        return subnets;
    }
    
    /**
     * All mac os related subnets
     * @return {@code List} of all the subnets which is related to mac os
     */
    private List<String> getMacSpecificSubnets() {
        // macOS commonly uses these ranges for WiFi
        return Arrays.asList(
            "192.168.1.", "10.0.1.", "172.20.10.", "192.168.4."
            );
        }
        
    /**
     * All linux os related subnets
     * @return {@code List} of all the subnets which is related to linux os
     */
    private List<String> getLinuxSpecificSubnets() {
        List<String> subnets = new ArrayList<>();
        
        try {
            // Try to get info from NetworkManager or iwconfig
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", 
                "iwconfig 2>/dev/null | grep -o 'inet [0-9.]*' || ip addr show"});
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("inet ")) {
                    String[] parts = line.split("\\s+");
                    for (String part : parts) {
                        if (part.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                            if (isValidPrivateIP(part)) {
                                String subnet = part.substring(0, part.lastIndexOf('.') + 1);
                                subnets.add(subnet);
                            }
                        }
                    }
                }
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            System.err.println("[Error] While getting Linux network info\nMessage: " + e.getMessage());
        }
        
        return subnets;
    }
    
    /**
     * Get WiFi-specific subnets by analyzing wireless interfaces
     */
    private List<String> getWiFiSpecificSubnets() {
        List<String> subnets = new ArrayList<>();
        
        // Add subnets that are commonly used by mobile hotspots and WiFi routers
        subnets.addAll(Arrays.asList(
            // Mobile hotspot common ranges
            "192.168.43.", "192.168.137.", "172.20.10.", "10.0.2.",
            
            // Common router brands default ranges
            "192.168.0.", "192.168.1.", "10.0.0.", "192.168.2.",
            "192.168.10.", "192.168.100.", "10.1.1.", "172.16.1."
        ));
        
        return subnets;
    }
    
    /**
     * Check if IP is a valid private IP address
     */
    private boolean isValidPrivateIP(String ip) {
        if (ip == null || ip.isEmpty()) { 
            return false;
        }

        try {
            String[] octets = ip.split("\\.");
            if (octets.length != 4) { 
                return false;
            }
            
            int firstOctet = Integer.parseInt(octets[0]);
            int secondOctet = Integer.parseInt(octets[1]);
            
            // Check private IP ranges
            boolean isPrivate = (firstOctet == 10) || 
                                (firstOctet == 172 && secondOctet >= 16 && secondOctet <= 31) ||
                                (firstOctet == 192 && secondOctet == 168);

            return isPrivate;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Test method to verify subnet detection
     */
    public void testSubnetDetection() {
        System.out.println("=== Subnet Detection Test ===");
        List<String> subnets = getAllPossibleSubnets();
        
        System.out.println("\nDetected Subnets:");
        for (String subnet : subnets) {
            System.out.println("  " + subnet + "x");
        }
        
        System.out.println("\nTesting reachability for first few subnets...");
        int tested = 0;
        for (String subnet : subnets) {
            if (tested >= 3) break; // Test only first 3 to avoid long delays
            testReachability(subnet);
            tested++;
        }
    }
    
    /**
     * Test if any devices in the subnet respond to ping
     */
    public void testReachability(String subnet) {
        System.out.println("Testing subnet: " + subnet + "x");
        boolean anyReachable = false;
        
        for (int i = 1; i <= 5; i++) {
            String ip = subnet + i;
            try {
                if (InetAddress.getByName(ip).isReachable(2000)) {
                    System.out.println("  ✓ " + ip + " is reachable");
                    anyReachable = true;
                } else {
                    System.out.println("  ✗ " + ip + " not reachable");
                }
            } catch (Exception e) {
                System.out.println("  ✗ " + ip + " error: " + e.getMessage());
            }
        }
        
        if (!anyReachable) {
            System.out.println("  No devices found in subnet " + subnet + "x");
        }
    }
}