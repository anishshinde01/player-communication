package com.anishshinde.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.anishshinde.common.StopConditions.initiatorStopConditionFulfilled;
import static com.anishshinde.process.ServerMessageService.MESSAGE_DELAY_MS;
import static com.anishshinde.process.PrintCommunicationProcesses.printInitiatorMessage;

/**
 * Handles the messaging logic for the initiator(client)
 *
 * Responsibilities:
 * - Sends initial message to responder(server)
 * - Reads messages(responses) from the responder(server) over a socket connection.
 * - Sends further messages and stops after sending a predefined number of messages.
 *
 * Developer Decisions:
 * - Uses BufferedReader and PrintWriter for simple socket I/O.
 * - The value of MESSAGE_DELAY_MS is decided by the server and is just used by the client and not implemented in
 *   App.java class, as its purpose is to just make sure that the print statements are printed out in the terminal
 *   in the correct order. It in no way affects the logic of the program. This small delay is added because the
 *   execution of threads can be faster than terminal output, causing print statements to appear out of order.
 * - Uses try-with-resources to automatically close streams when done.
 */
public class ClientMessageService {

    private int messagesSent;
    private int responsesReceived;
    private final int maxMessages;
    private final Socket socket;

    public ClientMessageService(Socket socket, int maxMessages) {
        this.socket = socket;
        this.maxMessages = maxMessages;
        messagesSent = 0;
        responsesReceived = 0;
    }

    /** Executes the messaging loop for the client(initiator) */
    public void sendMessage() throws IOException {
        String message = "message";
        try(PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            while (!initiatorStopConditionFulfilled(responsesReceived, messagesSent, maxMessages)) {
                message += " " + messagesSent;
                out.println(message);
                printInitiatorMessage(message);
                messagesSent++;
                message = in.readLine();
                responsesReceived++;
                Thread.sleep(MESSAGE_DELAY_MS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Client interrupted and terminating gracefully.");
        }
    }

}
