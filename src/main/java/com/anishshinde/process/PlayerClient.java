package com.anishshinde.process;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents the Initiator player (client) in the 2-Player Communication Program
 * when using multiprocessing (each player runs in a separate JVM).
 *
 * Responsibilities:
 * - Connects to the responder (server) via a TCP socket.
 * - Delegates sending and receiving messages to ClientMessageService.
 * - Runs inside a separate Java process (instance of the Java Virtual Machine).
 *
 * Developer Decisions:
 * - HOST and PORT are hardcoded for simplicity and demonstration purposes.
 * - Uses try-with-resources to ensure the socket is automatically closed.
 */
public class PlayerClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5001;

    private final int maxMessages;

    public PlayerClient(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    /** Starts Player Client (initiator) process */
    public void startPlayerInitiator(){
        try (Socket socket = new Socket(HOST, PORT)
        ){
            ClientMessageService clientMessageService = new ClientMessageService(socket, maxMessages);
            clientMessageService.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Main entry point for the initiator process.
     * Expects maxMessages as a command-line argument.
     *
     * @param args command-line arguments: args[0] = maxMessages
     */
    public static void main(String[] args){
        int maxMessages = 4;
        if(args.length > 0) maxMessages = Integer.parseInt(args[0]);
        PlayerClient playerInitiator = new PlayerClient(maxMessages);
        playerInitiator.startPlayerInitiator();
    }

}
