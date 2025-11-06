package com.anishshinde;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a player in a two-Player messaging game.
 * Each player runs on a separate thread and communicates with the other player
 * using a BlockingQueue. The initiator starts the first message.
 */
public class Player implements Runnable {

    // small delay to ensure messages are printed in the correct order in the terminal
    private static final int MESSAGE_DELAY_MS = 20;

    private final String playerName;
    private final boolean initiator;
    private final int maxMessages;
    private final BlockingQueue<String> queue;
    private Player otherPlayer;

    private int messagesSent;
    private int responsesReceived;
    private int responsesSent;

    public Player(String playerName, boolean initiator, int maxMessages) {
        // Ensure maxMessages is greater than zero to avoid unexpected behavior
        if (maxMessages <= 0) throw new IllegalArgumentException("maxMessages must be greater than 0");

        this.playerName = playerName;
        this.initiator = initiator;
        this.maxMessages = maxMessages;
        queue = new LinkedBlockingQueue<>();
        messagesSent = 0;
        responsesReceived = 0;
        responsesSent = 0;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    // return this player's BlockingQueue
    private BlockingQueue<String> getQueue(){
        return queue;
    }

    public String getOtherPlayerName(){
        return otherPlayer.playerName;
    }

    private void sendMessage(String message) throws InterruptedException {
        otherPlayer.getQueue().put(message);
    }

    /**
     * [ player1 sends {message} to player2 ]
     * [ player1 receives from player2 {message} ]
     * example - where player 1 is initiator, following is printed in the terminal:
     *           player1: "message 0" -> player2
     *           player1 <- player2: "message 0 1"
     * @param playerName name of the sender
     * @param message message which has been sent
     */
    private void printCommunication(String playerName, String message) {
        if (initiator) {
            System.out.println(playerName + ": \"" + message + "\" -> " + getOtherPlayerName());
        } else {
            System.out.println(getOtherPlayerName() + " <- " + playerName + ": \"" + message + "\"");
        }
    }

    @Override
    public void run() {
        try {
            // use String for simplicity, otherwise StringBuilder is more efficient for multiple concatenations
            String message;

            if (initiator) {
                message = "message 0";
                sendMessage(message);
                System.out.println("Player communication started");
                printCommunication(playerName, message);
                messagesSent++;
            }

            while (true) {
                message = queue.take();
                Thread.sleep(MESSAGE_DELAY_MS);

                if (initiator) {
                    responsesReceived++;
                    if (responsesReceived == maxMessages && messagesSent == maxMessages) break;
                    message = message + " " + messagesSent;
                    sendMessage(message);
                    printCommunication(playerName, message);
                    messagesSent++;
                } else {
                    // increment responsesSent before sendMessage() so that the message contains the count of this response
                    message = message + " " + ++responsesSent;
                    sendMessage(message);
                    printCommunication(playerName, message);
                    if (responsesSent == maxMessages) break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(playerName + "Thread interrupted and terminating gracefully.");
        }
    }

}
