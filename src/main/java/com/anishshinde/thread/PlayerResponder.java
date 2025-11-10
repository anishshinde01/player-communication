package com.anishshinde.thread;

import static com.anishshinde.thread.Player.MESSAGE_DELAY_MS;
import static com.anishshinde.thread.PrintCommunicationThreads.printResponderMessage;
import static com.anishshinde.common.StopConditions.responderStopConditionFulfilled;

/**
 * Handles the messaging logic for the responder player
 *
 * Responsibilities:
 * Responsible only for the responder's behavior: waiting for the first message from initiator,
 * sending back responses, and continuing messaging loop until the stop condition is met.
 *
 * Developer Decisions:
 * Using String instead of StringBuilder for simplicity, otherwise StringBuilder is more
 * efficient for multiple concatenations
 */
public class PlayerResponder {

    private final Player player;
    private int responsesSent;

    // passing the player as a parameter here is fine, since the fields in Player class are private
    public PlayerResponder(Player player) {
        this.player = player;
        responsesSent = 0;
    }

    /**
     * Starts the responder player's messaging loop where the responder waits until it receives the
     * first message from the initiator. Then this message is concatenated with the number of
     * responsesSent until now by the responder and sent back. Then the responder waits again.
     * This process is repeated until pre-defined number of responses have been sent.
     *
     * A small delay (MESSAGE_DELAY_MS) is added to ensure the console prints messages
     * in the intended order, as thread execution can be faster than terminal output.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void startPlayerResponder() throws InterruptedException {
        // use String for simplicity, otherwise StringBuilder is more efficient for multiple concatenations
        String message;

        do {
            message = player.takeMessage();
            Thread.sleep(MESSAGE_DELAY_MS);

            // increment responsesSent before sendMessage() so that the message contains the count of this response
            message = message + " " + ++responsesSent;
            player.sendMessage(message);
            printResponderMessage(player, message);
        } while (!responderStopConditionFulfilled(responsesSent, player.getMaxMessages()));
    }

}
