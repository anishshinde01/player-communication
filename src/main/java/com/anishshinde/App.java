package com.anishshinde;

import com.anishshinde.thread.Player;

public class App
{
    /**
     * Main application to start two-Player instances communicating messages
     */
    public static void main(String[] args) throws InterruptedException {

        // max number of messages each player will send as well as receive before terminating
        // value passed must be greater than 0
        int maxMessages = 4; // feel free to play around with entering any number here (greater than zero)

        Player player1 = new Player("player1", true, maxMessages);
        Player player2 = new Player("player2", false, maxMessages);

        // [COMPULSORY] link players with each other for communication
        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

        Thread player1Thread = new Thread(player1);
        Thread player2Thread = new Thread(player2);

        player1Thread.start();
        player2Thread.start();

        player1Thread.join();
        player2Thread.join();

        System.out.println("Player communication ended successfully");
    }
}
