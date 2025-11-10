package com.anishshinde.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.anishshinde.common.StopConditions.responderStopConditionFulfilled;
import static com.anishshinde.process.PrintCommunicationProcesses.printResponderMessage;

/**
 * Handles the messaging logic for the responder(server)
 *
 * Responsibilities:
 * - Reads messages from the initiator(client) over a socket connection.
 * - Sends back a response
 * - Stops after sending a predefined number of responses.
 *
 * Developer Decisions:
 * - Uses BufferedReader and PrintWriter for simple socket I/O.
 * - MESSAGE_DELAY_MS is static and final and not implemented in App.java class, as its purpose is to just make sure
 *   that the print statements are printed out in the terminal in the correct order. It in no way affects the logic
 *   of the program. This small delay is added because the execution of threads can be faster than terminal output,
 *   causing print statements to appear out of order.
 * - Uses try-with-resources to automatically close streams when done.
 */
public class ServerMessageService {

    // small delay to ensure messages are printed in the correct order in the terminal
    public static final int MESSAGE_DELAY_MS = 20;

    private final int maxMessages;
    private int responsesSent;
    private final Socket socket;

    public ServerMessageService(Socket socket, int maxMessages) {
        this.socket = socket;
        this.maxMessages = maxMessages;
        responsesSent = 0;
    }

    /** Executes the messaging loop for the server(responder) */
    public void sendMessage() throws IOException {
        String message;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ){
            while (!responderStopConditionFulfilled(responsesSent, maxMessages)) {
                message = in.readLine();
                Thread.sleep(MESSAGE_DELAY_MS);
                message += " " + ++responsesSent;
                out.println(message);
                printResponderMessage(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Server interrupted and terminating gracefully.");
        }
    }

}
