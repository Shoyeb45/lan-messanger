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

## Feature of the LAN Messanger

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

<div style="display: flex; gap: 4px;">
  <img src="./public/images/readme/demo3.png" width=500>
  <img src="./public/images/readme/demo4.png" width=200>
</div>

### 3. Persistent Storage
  - The data of the application (Friends of the user, and conversation with the friend) are stored permanently on local computer.
  - I am storing the data in `json`, I am converting the java object into `json` using `GSON` library.
  - The data are private and only present in the users' desktop

<div style="display: flex; gap: 10px;">
  <img src="./public/images/readme/data1.png" width=300>
  <img src="./public/images/readme/data2.png" height=190 width=300>
</div>

### 4. Add Friend Functionality
  - If the user have the IP Address of the friend then user can add the friend to his/her list.

### 5. Responsive
  - The application is completely responsive. The layout of the application have been changes on change of dimension.

### 6. Real Time Chatting
  - The user can chat with the user in real time if the user is connected


| Folder      | Purpose                                        |
| ----------- | ---------------------------------------------- |
| `app`       | Launch logic and global config                 |
| `network`   | Networking: sockets, scanning, and connections |
| `ui`        | GUI built with Swing                           |
| `model`     | Data classes and storage handling              |
| `public` | Static files like images or sound (optional)   |

<p  align="center"><i>Breakdown of roles</i></p>


## Architecture

<p>
  <img src="./public/images/readme/P2PLANMessanger.png">
  <p align="center">P2P connection of different instance of LAN Messanger </p>
</p>

<p>
  <img src="./public/images/readme/EachInstance.png">
  <p align="center">A single instance working both as a client and server </p>
</p>

