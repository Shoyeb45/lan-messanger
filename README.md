# **LAN Messenger – Peer-to-Peer Chat Application**

A **peer-to-peer Java-based chat application** for devices on the same LAN using **Socket programming** and **Swing GUI**.

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

## 🛠️ Optional / Advanced Features

* UDP Broadcast for faster discovery (`"I_AM_HERE"` packets).
* Chat history saved per friend.
* Status message (e.g., “Busy”, “Away”).
* File transfer support.
* Group chat (multi-client).
* Custom usernames and avatars.

---

## 🗺️ Development Roadmap

| Phase                        | Tasks                                                                           |
| ---------------------------- | ------------------------------------------------------------------------------- |
| **Phase 1: Setup**           | Create main application structure, start server & client threads on launch.     |
| **Phase 2: Scanning**        | Implement IP scanning over LAN and detect active devices (based on port check). |
| **Phase 3: Verification**    | Send/accept friend requests with a simple protocol. Store approved IPs.         |
| **Phase 4: Chat System**     | Enable real-time chat with multithreading for send/receive.                     |
| **Phase 5: GUI with Swing**  | Create UI for scanning, friend list, chat window, and request dialog.           |
| **Phase 6: Status Handling** | Track online/offline states by probing saved friends or via heartbeats.         |
| **Phase 7: Polish & Extras** | Add timestamps, labels, error handling, persistence, and optional features.     |

---


## Folder Structure 


```less
.
├── README.md                          # Project overview and instructions
├── bin/                               # Compiled .class files (output directory)
│   └── main/
│       └── java/
│           └── com/
│               └── lanmessanger/
│                   ├── app/
│                   │   └── MessangerApp.class           # Compiled main class
│                   ├── models/                          # Compiled data classes
│                   ├── network/                         # Compiled networking logic
│                   │   ├── client/                      # Client-side socket code
│                   │   ├── discovery/                   # Scanning LAN for peers
│                   │   │   └── NetworkScanner.class
│                   │   └── server/                      # Server-side logic
│                   └── ui/                              # Compiled UI code
├── lib/                               # External libraries (if any)
├── public/
│   └── images/                        # App icons, avatars, and other UI images
└── src/                               # Source code
    └── main/
        └── java/
            └── com/
                └── lanmessanger/
                    ├── app/
                    │   └── MessangerApp.java            # App entry point
                    ├── models/
                    │   ├── Friend.java                  # Stores friend info (IP, port, name)
                    │   ├── Message.java                 # Represents chat messages
                    │   └── User.java                    # Represents current user
                    ├── network/
                    │   ├── client/
                    │   │   └── Client.java              # Client socket to send/receive messages
                    │   ├── discovery/
                    │   │   └── NetworkScanner.java      # Scans LAN for available peers
                    │   └── server/
                    │       ├── ClientHandler.java       # Handles each incoming connection
                    │       └── Server.java              # Server socket setup and listener
                    └── ui/
                        └── MainWindow.java              # Main Swing-based user interface
```

| Folder      | Purpose                                        |
| ----------- | ---------------------------------------------- |
| `app`       | Launch logic and global config                 |
| `network`   | Networking: sockets, scanning, and connections |
| `ui`        | GUI built with Swing                           |
| `model`     | Data classes and storage handling              |
| `public` | Static files like images or sound (optional)   |

<p  align="center"><i>Breakdown of roles</i></p>
