package com.anishshinde.thread;

import static com.anishshinde.thread.Player.MESSAGE_DELAY_MS;
import static com.anishshinde.thread.PrintCommunicationThreads.printInitiatorMessage;
import static com.anishshinde.common.StopConditions.initiatorStopConditionFulfilled;

/**
 * Handles the messaging logic for the initiator player
 *
 * Responsibilities:
 * Responsible only for the initiator's behavior: sending the first message,
 * waiting for responses, and sending subsequent messages until the stop condition is met.
 *
 * Developer Decisions:
 * Using String instead of StringBuilder for simplicity, otherwise StringBuilder is more
 * efficient for multiple concatenations
 */
public class PlayerInitiator {

    private final Player player;
    private int messagesSent;
    private int responsesReceived;

    // passing the player as a parameter here is fine, since the fields in Player class are private
    public PlayerInitiator(Player player) {
        this.player = player;
        messagesSent = 0;
        responsesReceived = 0;
    }

    /**
     * Starts the initiator player's messaging loop where the initiator sends the first message,
     * and then waits until it receives back a response. One receiving a response, it again concatenates
     * the received message with its own and sends back. This process is repeated until pre-defined
     * number of messages have been sent.
     *
     * A small delay (MESSAGE_DELAY_MS) is added to ensure the console prints messages
     * in the intended order, as thread execution can be faster than terminal output.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void startPlayerInitiator() throws InterruptedException {
        // use String for simplicity, otherwise StringBuilder is more efficient for multiple concatenations
        String message;

        // send initial message
        message = "message 0";
        player.sendMessage(message);
        System.out.println("Player communication started");
        printInitiatorMessage(player, message);
        messagesSent++;

        while (true) {
            message = player.takeMessage();
            Thread.sleep(MESSAGE_DELAY_MS);

            responsesReceived++;
            if (initiatorStopConditionFulfilled(responsesReceived, messagesSent, player.getMaxMessages())) break;
            message = message + " " + messagesSent;
            player.sendMessage(message);
            printInitiatorMessage(player, message);
            messagesSent++;
        }
    }

}
