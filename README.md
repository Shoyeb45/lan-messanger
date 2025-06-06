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

## ✅ Functional Requirements

### 1. **Network Scanning & Discovery**

* Automatically scan all devices connected to the **same LAN subnet**.
* Detect devices running the messenger app by connecting to a **specific port** (e.g., `5000`).
* Show online users in a GUI list with IP and optional username.

### 2. **Dual Role Architecture**

* Every instance runs both:

  * A **Server** (to accept chat requests).
  * A **Client** (to initiate connections to other users).

### 3. **Friend Request & Verification**

* Users must **send a chat/friend request** to another user.
* Recipient must **accept** the request for chatting to begin.
* On acceptance, the app stores the IP and port locally (as a **"friend"**).

### 4. **Real-Time Chat Messaging**

* After friendship is confirmed:

  * Allow **two-way communication** using separate threads for sending and receiving messages.
  * Support basic **message timestamps** and user labels.

### 5. **User Online Status**

* Show online/offline status of friends:

  * When app is running, server socket is active ⇒ user is **online**.
  * If socket is unreachable ⇒ **offline**.


### 6. **Local Storage**

* Maintain a **list of friends (IP + Port + Name)** locally.
* Use this list to initiate quick chat without re-scanning.

### 7. **Swing GUI**

* GUI (using Swing) for:

  * Scanning devices
  * Displaying users
  * Chat windows
  * Friend requests
  * Status indicators


---



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

