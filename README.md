# 2-Player Communication Game  |  Anish Shinde

> **Overview:** This README provides a high-level overview of how to run and use the Player Communication program.  
> All classes in the project are individually documented with detailed descriptions of their responsibilities, methods, usage, and decisions the developer has taken.  
> Please refer to the Java source files for complete class-level documentation.
---
### 🧩 Each player runs either in ⚙️ separate threads or 🖥️ separate processes, sending messages 🔁 back and forth according to a predefined 🛑 stop condition.

### ✨ The solution emphasizes the cleanest and clearest design possible that fulfils the defined requirements ✅ as well as the additional challenge 🎯!

---

## How to Run 💻

The project includes a shell script `start.sh` that **automatically builds the project using Maven** and then runs it prompting the User for 2 parameters.

### Steps

1. Make the script executable:
```bash
chmod +x start.sh
```

2. Run the script
```bash
./start.sh
```

3. The script will prompt you for:

- **Mode of execution** (default is Multithreading):
    - Press `Enter` to use default `[1]`
    - Enter `1` for Multithreading
    - Enter `2` for Multiprocessing

- **Max messages** (stop condition):
    - Press `Enter` to use default (`4`)
    - Or enter a number greater than 0 to set a custom stop condition

> The script validates your input: only empty input or numbers `1` or `2` are allowed for mode, and only numbers greater than 0 for max messages.

That's it! The program then starts with the selected mode and above defined number of messages.

- **Threads mode**: The players (class instances) run inside the same Java process but in separate threads.
- **Processes mode**: Each player runs inside a separate Java process (instance of the Java Virtual Machine).
---

### Maven pre-installed❔

- The script runs `mvn clean package` automatically before execution.
- Ensure **Maven is installed** on your system and available in your PATH.
---

## Notes on Structure ⚙️
Inside `player-communication/src/main/java/com/anishshinde/`:
>`App.java`: Central entry point; decides which mode (threads or processes) to run based on user input [or default settings].
- `common/`: Contains shared utility (stop conditions) that are used by both threads and processes modules.


- `process/`: Contains all relevant classes for multiprocessing execution in separate JVMs.
  - `ClientMessageService`: Handles the messaging logic for the initiator(client)
  - `ParticipantNames`: Client Name(player1) and Server Name(player2)
  - `PlayerClient`: Represents the Initiator player
  - `PlayerServer`: Represents the responder player
  - `PrintCommunicationProcesses`: Utility class for printing messages exchanged between the initiator and responder
  - `ServerMessageService`: Handles the messaging logic for the responder(server)


- `thread/`: Contains all relevant classes for multithreading execution inside a single JVM.
  - `Player`: Represents a player
  - `PlayerInitiator`: Handles the messaging logic for the initiator player
  - `PlayerResponder`: Handles the messaging logic for the responder player
  - `PrintCommunicationThreads`: Utility class for printing messages exchanged between the initiator and responder

---
## Features 💡

- **Thread-based messaging**: Two players (class instances) run inside the same Java process but in separate threads.
- **Process-based messaging**: Each player runs inside a separate Java process (instance of the Java Virtual Machine).
- **Stop conditions**: Both initiator and responder terminate communication after the defined number of messages has been sent and received.
- **Extensible design**: Classes are cleanly and clearly designed and well-documented using Javadoc comments explaining responsibilities and design decisions.
- **Pure Java**: No third-party frameworks used in main project. Only standard Java libraries are used.
- **Shell script**: `start.sh` provided to start either **thread-based** or **process-based** mode.
- **Maven project producing a `.jar`**: `pom.xml` included; `mvn clean package` builds `.jar`.
---

### Testing ✅
Test class: `TestPlayerInitiator`

Verifies that the initiator player sends the first message and waits for a response before sending the next message.

Uses `Mockito` to mock a Player instance and control responses. Confirms the back-and-forth message order by verifying the sequence of sendMessage and takeMessage calls.

---
> `Author`: Anish Shinde
