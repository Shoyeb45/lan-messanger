package main.java.com.lanmessanger.app;

import java.util.ArrayList;
import java.util.Scanner;

import main.java.com.lanmessanger.network.server.Server;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Server server = new Server(AppConfig.SERVER_PORT);
        server.start();

        while (true) {
            int input = sc.nextInt();
            if (input == 0) {
                break;
            }

            if (input == 1) {
                // show all the connected users
                ArrayList<String> cu = server.getConnectedClients();
                for (int i = 0; i < cu.size(); i++) {
                    System.out.println((i + 1) + " -> " + cu.get(i));
                }
                System.out.println("Select user");
                int selected = sc.nextInt();
                
                if (selected > cu.size() || selected <= 0) {
                    System.out.println("Invalid selection");
                    break;
                }

                String ip = cu.get(selected - 1);
                
                System.out.println("Input message");
                String message = sc.nextLine();
                server.sendMessage(message, ip);
                System.out.println("Message sent successfully");
            }
        }

        sc.close();
    }
}
