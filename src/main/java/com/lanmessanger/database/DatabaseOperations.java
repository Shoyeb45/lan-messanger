package main.java.com.lanmessanger.database;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.java.com.lanmessanger.models.Message;
import main.java.com.lanmessanger.models.User;

/**
 * Class to handle the data of the user. Converting the objects into json files
 * @author Shoyeb Ansari
 */
public class DatabaseOperations {
    /** Path were the data of the user will be stored */
    private static final String path = getAppDataDirectory("LAN-MESSANGER");
    /** The file which saves MessageHistory object into this file */
    private static File messageHistoryFile = new File(path + File.separator + "MessageHistory.json");
    /** The file which saves Friend object into this file */
    private static File friendFile = new File(path + File.separator + "Friend.json");
    /** Gson object which will convert the object to json and vice-versa */
    private static Gson gson = GsonProvider.getGson();

    
    /**
     * @return {@code MessageHistory} object which contains all conversations
     */
    public static Map<String, List<Message>> readMessageHistory() {
        Scanner reader = null;
        try {
            if (!messageHistoryFile.exists()) {
                System.out.println("[WARNING] MessageHistory.json not found");
                return null;
            }

            reader = new Scanner(messageHistoryFile);
            StringBuilder data = new StringBuilder();

            while (reader.hasNextLine()) {
                data.append(reader.nextLine());
            }

            // Check if file is empty
            String jsonData = data.toString().trim();
            if (jsonData.isEmpty()) {
                System.out.println("[INFO] MessageHistory.json is empty");
                return null;
            }

            Type messageHistoryType = new TypeToken<Map<String, List<Message>>>(){}.getType();
            Map<String, List<Message>> history = gson.fromJson(jsonData, messageHistoryType);
            
            System.out.println("[INFO] MessageHistory loaded successfully");
            return history;
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] MessageHistory.json not found, will create new one");
            return null;
        } catch (JsonSyntaxException e) {
            System.out.println("[ERROR] Invalid JSON in MessageHistory.json\nError Message: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("[ERROR] Error reading MessageHistory.json\nError Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 
     * @return {@code Friend} object which contains list of friends 
     */
    public static HashSet<User> readFriend() {
        Scanner reader = null;
        try {
            if (!friendFile.exists()) {
                System.out.println("[INFO] Friend.json not found");
                return null;
            }

            reader = new Scanner(friendFile);
            StringBuilder data = new StringBuilder();

            while (reader.hasNextLine()) {
                data.append(reader.nextLine());
            }

            // Check if file is empty
            String jsonData = data.toString().trim();
            if (jsonData.isEmpty()) {
                System.out.println("[INFO] Friend.json is empty");
                return null;
            }

            Type friendType = new TypeToken<HashSet<User>>(){}.getType();
            HashSet<User> friendObject = gson.fromJson(jsonData, friendType);
            
            System.out.println("[INFO] Friend list loaded successfully");
            return friendObject;
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] Friend.json not found, will create new one");
            return null;
        } catch (JsonSyntaxException e) {
            System.out.println("[ERROR] Invalid JSON in Friend.json: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("[ERROR] Error reading Friend.json: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    /**
     * Method to write to {@code Friend.json} file and update the database
     * @param friendObject {@code Friend} object that needs to be converted in json and then write to {@code Friend.json}
     */
    public static void writeToFriend(HashSet<User> friendObject) {
        if (friendObject == null) {
            System.out.println("[WARNING] Cannot write null Friend object to file");
            return;
        }

        FileWriter writer = null;
        try {
            // Ensure directory exists
            File directory = new File(path);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("[INFO] Created directory: " + path);
                }
            }
            
            writer = new FileWriter(friendFile);
            String friendObjectInJson = gson.toJson(friendObject);
            writer.write(friendObjectInJson);
            writer.flush(); // Ensure data is written to disk
            
            System.out.println("[INFO] Friend data written to file successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Error writing to Friend.json: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] Error closing FileWriter: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Method to write to {@code MessageHistory.json} file and update the database
     * @param messageHistory {@code MessageHistory} object that needs to be converted in json and then write to {@code MessageHistory.json}
     */
    public static void writeToMessageHistory(Map<String, List<Message>> userMessages) {
        if (userMessages == null) {
            System.out.println("[WARNING] Cannot write null MessageHistory object to file");
            return;
        }

        FileWriter writer = null;
        try {
            // Ensure directory exists
            File directory = new File(path);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("[INFO] Created directory: " + path);
                }
            }
            
            writer = new FileWriter(messageHistoryFile);
            String messageHistoryInJson = gson.toJson(userMessages);
            writer.write(messageHistoryInJson);
            writer.flush(); // Ensure data is written to disk
            
            System.out.println("[INFO] MessageHistory data written to file successfully");
        } catch (IOException e) {
            System.out.println("[ERROR] Error writing to MessageHistory.json: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] Error closing FileWriter: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Method to create files if it doesn't exist. </br>
     * {@code MessageHistory.json} and {@code Friend.json}
     */
    public static void createFiles() {
        try {
            // Ensure directory exists first
            File directory = new File(path);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("[INFO] Created database directory: " + path);
                }
            }
            
            if (messageHistoryFile.createNewFile()) {
                System.out.println("[INFO] MessageHistory.json file created at: " + messageHistoryFile.getAbsolutePath());
            } 

            if (friendFile.createNewFile()) {
                System.out.println("[INFO] Friend.json file created at: " + friendFile.getAbsolutePath());
            } 
            
            System.out.println("[INFO] Database files ready");
        } catch (Exception e) {
            System.out.println("[ERROR] While creating files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to find the correct path where we can save the data
     * @param appName Name of the app
     * @return Path where data needs to be saved
     */
    public static String getAppDataDirectory(String appName) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String userHome = System.getProperty("user.home");
            String path = "";
    
            if (os.contains("win")) {
                String appData = System.getenv("APPDATA"); // typically C:\Users\User\AppData\Roaming
                path = appData + File.separator + appName;
            } else if (os.contains("mac")) {
                path = userHome + File.separator + "Library" + File.separator + "Application Support" + File.separator + appName;
            } else {
                // Linux/Unix
                path = userHome + File.separator + ".config" + File.separator + appName;
            }
    
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return path;
        } catch (Exception e) {
            System.out.println("[ERROR] Failed to get the system specific directory for saving the data\nError Message: " + e.getMessage());
            e.printStackTrace();
            return "db";
        }
    }
}