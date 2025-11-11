package com.anishshinde.process;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents the responder player (server) in the 2-Player Communication Program
 * when using multiprocessing (each player runs in a separate JVM).
 *
 * Responsibilities:
 * - Listens for incoming connections from the initiator (client) via a TCP socket.
 * - Delegates sending and receiving messages to ServerMessageService.
 * - Runs inside a separate Java process (instance of the Java Virtual Machine).
 *
 * Developer Decisions:
 * - PORT is hardcoded for simplicity and demonstration purposes.
 * - Uses try-with-resources to ensure ServerSocket and Socket are automatically closed.
 */
public class PlayerServer {

    private static final int PORT = 5001;

    private final int maxMessages;

    public PlayerServer(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    /** Starts Player Server (responder) process */
    public void startPlayerResponder(){
        try( ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept()
             ) {
            ServerMessageService serverMessageService = new ServerMessageService(socket, maxMessages);
            serverMessageService.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Main entry point for the responder process.
     * Expects maxMessages as a command-line argument.
     *
     * @param args command-line arguments: args[0] = maxMessages
     */
    public static void main(String[] args){
        int maxMessages = 4;
        if(args.length > 0) maxMessages = Integer.parseInt(args[0]);
        PlayerServer playerResponder = new PlayerServer(maxMessages);
        playerResponder.startPlayerResponder();
    }
}
