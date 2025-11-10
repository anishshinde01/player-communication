package com.anishshinde.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a player in a 2-Player messaging game.
 * Responsibilities:
 * - Each Player instance runs on a separate thread and communicates with the other Player via a BlockingQueue.
 * - The initiator sends the first message
 * - Handles sending and receiving messages through the queue in a thread-safe manner.
 *
 * Developer Decisions:
 * - The queue is private and only accessible through sendMessage() and takeMessage(), preventing misuse from other classes.
 * - otherPlayer must be set exactly once before the run() method; attempting to change it later throws an exception.
 * - MESSAGE_DELAY_MS is static and final and not implemented in App.java class, as its purpose is to just make sure
 *   that the print statements are printed out in the terminal in the correct order. It in no way affects the logic
 *   of the program. This small delay is added because the execution of threads can be faster than terminal output,
 *   causing print statements to appear out of order.
 * - Lombok is not used to avoid introducing 3rd-party dependencies (as per project requirements),
 *   so simple getters are manually provided.
 */
public class Player implements Runnable {

    // small delay to ensure messages are printed in the correct order in the terminal
    public static final int MESSAGE_DELAY_MS = 20;

    private final String playerName;
    private final boolean initiator;
    private final int maxMessages;
    private final BlockingQueue<String> queue;
    private Player otherPlayer;

    public Player(String playerName, boolean initiator, int maxMessages) {
        // Ensure maxMessages is greater than zero to avoid unexpected behavior
        if (maxMessages <= 0) throw new IllegalArgumentException("maxMessages must be greater than 0");

        this.playerName = playerName;
        this.initiator = initiator;
        this.maxMessages = maxMessages;
        queue = new LinkedBlockingQueue<>();
    }

    /** @return name of this player*/
    public String getPlayerName(){
        return playerName;
    }

    /** @return maximum number of messages*/
    public int getMaxMessages(){
        return maxMessages;
    }

    /** @return true if this player is an initiator*/
    public boolean isInitiator(){
        return initiator;
    }

    /**
     * Sets the other player instance for communication.
     * otherPlayer must be set exactly once before run(), further calls will throw an exception
     *
     * @param otherPlayer otherPlayer
     */
    public void setOtherPlayer(Player otherPlayer) {
        if (this.otherPlayer != null) {
            throw new IllegalStateException("Other player already set, cannot change");
        }
        this.otherPlayer = otherPlayer;
    }

    /** @return name of otherPlayer*/
    public String getOtherPlayerName(){
        return otherPlayer.playerName;
    }

    /**
     * Sends a message to the queue of otherPlayer. Blocks if the queue is full.
     *
     * @param message the message to be sent
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void sendMessage(String message) throws InterruptedException {
        otherPlayer.queue.put(message);
    }

    /**
     * Retrieves and removes the next message from this player's queue, blocking
     * if necessary until a message is available
     * @return the message received from otherPlayer
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public String takeMessage() throws InterruptedException {
        return queue.take();
    }

    /**
     * Starts the player thread. If Player is initiator, PlayerInitiator is started,
     * otherwise PlayerResponder is started.
     */
    @Override
    public void run() {
        if (otherPlayer == null) throw new IllegalStateException(
                "otherPlayer not set for " + playerName + " before starting thread! Call setOtherPlayer() first");
        try {
            if(initiator){
                PlayerInitiator playerInitiator = new PlayerInitiator(this);
                playerInitiator.startPlayerInitiator();
            } else {
                PlayerResponder playerResponder = new PlayerResponder(this);
                playerResponder.startPlayerResponder();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(playerName + "Thread interrupted and terminating gracefully.");
        }
    }

}