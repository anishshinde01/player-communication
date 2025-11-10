package com.anishshinde.process;

import static com.anishshinde.process.ParticipantNames.CLIENT_NAME;
import static com.anishshinde.process.ParticipantNames.SERVER_NAME;

/**
 * Utility class for printing messages exchanged between the initiator(client) and responder(server)
 *
 * Responsibilities:
 * - Prints messages sent by the initiator to the responder.
 * - Prints messages sent by the responder back to the initiator.
 *
 * Developer Decisions:
 * - Class is provided with a private constructor to prevent instantiation,
 *   since it only provides static methods.
 * - Uses hardcoded participant names from ParticipantNames.java to decouple printing from socket logic,
 *   and keep things simple
 */
public class PrintCommunicationProcesses {

    private PrintCommunicationProcesses() {}

    /** Prints a message sent by the initiator(client) to the responder(server) */
    public static void printInitiatorMessage(String message) {
        System.out.println(CLIENT_NAME + ": \"" + message + "\" -> " + SERVER_NAME);
    }

    /** Prints a message(response) sent by the responder(server) back to the initiator(client) */
    public static void printResponderMessage(String message) {
        System.out.println(CLIENT_NAME + " <- " + SERVER_NAME + ": \"" + message + "\"");
    }
}
