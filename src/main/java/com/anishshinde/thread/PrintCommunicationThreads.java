package com.anishshinde.thread;

/**
 * Utility class for printing messages exchanged between the initiator and responder in the terminal
 *
 * Responsibilities:
 * - print messages sent from initiator/responder to responder/initiator
 * - validate, that only the player having the correct role(initiator or not),
 *   calls their respective print method, logs an error if not
 *
 * Developer Decisions:
 * - Class is provided with a private constructor to prevent instantiation,
 *   since it only provides static methods.
 *
 * Example:
 * [ player1 sends {message} to player2 ]
 * [ player1 receives from player2 {message} ]
 * example - where player 1 is initiator, following is printed in the terminal:
 *           player1: "message 0" -> player2
 *           player1 <- player2: "message 0 1"
 */
public class PrintCommunicationThreads {

    private PrintCommunicationThreads(){}

    /** Prints a message sent by the initiator to the responder */
    public static void printInitiatorMessage(Player player, String message) {
        if(!(player.isInitiator())) System.err.println(
                "[ERROR] Invalid player passed to printInitiatorMessage(): Expected initiator, got responder");
        System.out.println(player.getPlayerName() + ": \"" + message + "\" -> " + player.getOtherPlayerName());
    }

    /** Prints a message(response) sent by the responder back to the initiator */
    public static void printResponderMessage(Player player, String message) {
        if(player.isInitiator()) System.err.println(
                "[ERROR] Invalid player passed to printResponderMessage(): Expected responder, got initiator");
        System.out.println(player.getOtherPlayerName() + " <- " + player.getPlayerName() + ": \"" + message + "\"");
    }

}
