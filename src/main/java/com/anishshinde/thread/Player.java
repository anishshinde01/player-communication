package com.anishshinde.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a player in a two-Player messaging game.
 * Each player runs on a separate thread and communicates with the other player
 * using a BlockingQueue. The initiator starts the first message.
 *
 * Lombok(3rd Party Alternative not used) could generate simple getters for playerName, maxMessages, initiator
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

    public String getPlayerName(){
        return playerName;
    }

    public int getMaxMessages(){
        return maxMessages;
    }

    public boolean isInitiator(){
        return initiator;
    }

    // otherPlayer must be set exactly once before run(), further calls will throw an exception
    public void setOtherPlayer(Player otherPlayer) {
        if (this.otherPlayer != null) {
            throw new IllegalStateException("Other player already set, cannot change");
        }
        this.otherPlayer = otherPlayer;
    }

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