# **LAN Messenger: Peer-to-Peer Chat Application**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  ![Express.js](https://img.shields.io/badge/Java%20Swing-%23007396.svg?style=for-the-badge&logoColor=white
)  ![](https://img.shields.io/badge/Socket%20Programming-%2334A853.svg?style=for-the-badge&logoColor=white
)  ![](https://img.shields.io/badge/Multithreading-%23FF6F00.svg?style=for-the-badge&logoColor=white
)  ![](https://img.shields.io/badge/Messaging-%239C27B0.svg?style=for-the-badge&logoColor=white
)  ![](https://img.shields.io/badge/Networking-%2300BCD4.svg?style=for-the-badge&logoColor=white)  ![](https://img.shields.io/badge/Peer%20to%20Peer-%23F44336.svg?style=for-the-badge&logoColor=white)  ![](https://img.shields.io/badge/GUI-%232196F3.svg?style=for-the-badge&logoColor=white
)

A **peer-to-peer Java-based chat application** for devices on the same LAN using **Socket programming** and **Swing GUI**.
<p align="center">
  <img src="./public/images/readme/LANMLogo.png">
</p>

---

## 📦 Downloads

- The application is available for common operating system, please find below:  
💻 [Windows Installer (.exe)](https://yourhost.com/lanmessanger/installer.exe)  
🍎 [macOS App (.dmg)](https://yourhost.com/lanmessanger/lanmessenger.dmg)  

- If you want for particular operating system, please mail about this to me in : [Personal mail](shoyebff45@gmail.com)

---

---

## 🚀 Feature of the LAN Messanger

<p align="center">
  <img src="./public/images/readme/demo1.png">
  <p align="center">Start Page</p>
</p>
- LAN Messagner is build using java programming langauge. This application has following features:

### 1. Single Page Application
  - This application is built on a single page like modern web application. This improves the accessibility of different features of the application and greatly improve the UX.
  - React like state management using java interface and classes.

<p align="center">
  <img src="./public/images/readme/demo2.png">
  <p align="center">Single Page Application can be navigated through Navigation Bar</p>
</p>

### 2. Discovering Friends
  - This application provides a way to discover users that are connected to same network and have opened the application.
  - I am using UDP protocol to quickly send the small data packets to check and find the users.

<div style="display: flex; gap: 4px; justify-content: center;">
  <img src="./public/images/readme/demo3.png" width=500>
  <img src="./public/images/readme/demo4.png" width=200>
</div>

### 3. Persistent Storage
  - The data of the application (Friends of the user, and conversation with the friend) are stored permanently on local computer.
  - I am storing the data in `json`, I am converting the java object into `json` using `GSON` library.
  - The data are private and only present in the users' desktop

<div style="display: flex; gap: 10px; justify-content: center;">
  <img src="./public/images/readme/data1.png" width=300>
  <img src="./public/images/readme/data2.png" height=190 width=300>
</div>

### 4. Add Friend Functionality
  - If the user have the IP Address of the friend then user can add the friend to his/her list.

<p align="center">
  <img src="./public/images/readme/demo5.png">
  <p align="center">Add Friend Page</p>
</p>

### 5. Responsive
  - The application is completely responsive. The layout of the application have been changed on change of dimension.

<p align="center">
  <img src="./public/images/readme/demo6.png">
</p>

### 6. Real Time Chatting
  - The user can chat with the user in real time if the user is connected

---

## Project Folder Structure

- I have divided the project into two categories: building GUI and the main logic.
- To increase the modularity approach, the project has been divided in different different folders. 
- Here is the folder structure:


```plaintext
📁 .
├── 📁 lib                        # Contains library jar files
├── 📁 public                     # Contains images
│   └── 📁 images
│       ├── 📁 app
│       └── 📁 readme
└── 📁 src
    └── 📁 main
        └── 📁 java
            └── 📁 com
                └── 📁 lanmessanger
                    ├── 📁 app             # Main starting point of application
                    ├── 📁 database        # Handles application data
                    ├── 📁 models          # Schema definitions of entities
                    ├── 📁 network         # Core networking logic
                    │   ├── 📁 client
                    │   ├── 📁 clientHandler
                    │   ├── 📁 discovery
                    │   └── 📁 server
                    └── 📁 ui              # Graphical User Interface components
                        ├── 📁 components
                        │   ├── 📁 addFriendPage
                        │   ├── 📁 chatPage
                        │   ├── 📁 navBar
                        │   └── 📁 scannerPage
                        ├── 📁 pages       # Different pages
                        ├── 📁 router      # Routing logic (SPA-style navigation)
                        ├── 📁 state       # Centralized application state
                        └── 📁 utils       # Helper methods
```



> High level breakdown of folders:

| Folder      | Purpose                                        |
| ----------- | ---------------------------------------------- |
| `app`       | Launch logic and global config                 |
| `network`   | Networking: sockets, scanning, and connections |
| `ui`        | GUI built with Swing                           |
| `model`     | Data classes and storage handling              |
| `public` | Static files like images or sound (optional)   |

<p  align="center"><i>Breakdown of roles</i></p>

---

## 🛠️ Technologies Used

- I have used following technologies to build my application:

### Programming langauge and libraries

1. Java 17
2. Java Swing for Graphical User Interface (`java.swing`)
3. Networking library of java for socket programming(TCP/UDP) (`java.net`)
4. Various data structure from collections framework to manage the data efficiently.
5. Gson for JSON serialization. (`com.google.gson.Gson`)
6. Ikonli Fontawesome pack for beautiful icons. (`org.kordamp.ikonli`)

### Other

1. VS Code IDE for project management.
2. Git and Github for tracking the progress


## Run project locally and contribute

- Make sure you have java 17 or above, run following command to check if it is present or not:
```bash
java --version
```

- Follow below steps to run the project locally and contribute to this project:

1. Clone the repository
    ```bash
    git clone https://github.com/Shoyeb45/lan-messanger.git  
    ```

2. Navigate to the directory
    ```bash
    cd lan-messnger
    ```

3. If you are using VS Code then install below extension:
    
    - [Extension Pack for Java by Microsoft](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
    - Then go to src\main\java\com\lanmessanger\Main.java and click on the Run(just upside of the main method)
    <p align="center">
      <img src="./public/images/readme/runMain.png" width="450px">
    </p>
    
    Or use any build tools like `Maven`.

    Since Project has many files, so the running it from terminal is not efficient.

4. After you have run the project successfully. Then if you want to add any feature, then make new branch by:
    ```bash
    git checkout -b feature/feature-name
    ```

    Implement the feature and test it locally then raise Merge Request with explanation. Include the screenshots if you can.

5. That's it, thank you for your contribution ❤️

---

## Working of the application

The working of the application can be divided into two parts:
1. Core logic (networking)
2. User Interface using Java Swing

### 1. Core logic

- To build the text based lan messanger, I have used TCP protocol to establish the connection between users.
- After establishing connection, on the LAN the instances of the user forms a peer to peer to networking architecture:
    
    <p align="center">
      <img src="./public/images/readme/P2PLANMessanger.png" width=500>
      <p align="center"><i>P2P connection of different instance of LAN Messanger </i> </p>
    </p>

- For continuosly receiving and sendint the message, I have used multiple threads to run the application. There are different threads for each of the use case. Like the UI runs on different thread and then Server logic runs on different thread. In this way the UX of the application is clean and efficient.
- Even user in the network will behave like both server and client. See the image below:
    <p align="center">
      <img src="./public/images/readme/EachInstance.png" width=450>
      <p align="center"><i>A single instance working both as a client and server </i></p>
    </p>

- In this way the core logic works seamlessly.

### 2. UI Logic

- I have used java swing for the UI. The main UI is initialised on the main application by different thread dedicated for UI.
- Entire GUI of the application reside on `App` class which inherites from `JFrame`. Then every page renders on this `App` class. See the below hierarchy:
    <p align="center">
      <img src="./public/images/readme/uilogic.png" width=550>
      <p align="center"><i>A single instance working both as a client and server </i></p>
    </p>

- Each of the component works with the core logic for the fully functional application.

---

## 💻 Supported Platforms
### Windows ✅

### Linux ✅

### macOS ✅


## 🧪 How to test the application?

To test the application, please follow the below steps:

- Open the application in two desktop. Make sure that both the desktop are connected to same wifi network
- Go to add friend page.
- Then enter the IP address of any desktop, only enter in one of them. The click add. And on the other desktop a pop up will come saying that some has requested to be friend.
- Add friend on both the desktop. Then try to send the message from one desktop to other desktop.
- If you can receive and send the message then congratulations the application is working.

---

## 📜 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.


