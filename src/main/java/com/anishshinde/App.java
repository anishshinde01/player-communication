package com.anishshinde;

import com.anishshinde.thread.Player;

import java.io.IOException;

public class App {
    /**
     * Entry point of the 2-Player Communication program.
     *
     * Responsibilities:
     * - Initialize the players for communication, either using threads (same JVM) or separate processes (different JVMs).
     * - Configure the maximum number of messages each player will send and receive.
     * - Start and coordinate execution of players.
     * - Ensure clean termination of all player instances before exiting the program.
     *
     *  Developer Decisions:
     *  - A boolean flag 'useThreads' is used to toggle between multithreading and multiprocessing for demonstration purposes.
     *  - The maximum number of messages ('maxMessages') is defined in main() for easy configuration. It must be > 0.
     *  - Threads are joined in the main thread to guarantee proper synchronization and program termination.
     *  - ProcessBuilder uses 'inheritIO()' so that the standard output of the separate JVM processes is visible in the same console for demonstration.
     *  - System.out is used for printing messages instead of a logger for simplicity and clarity in this particular demo scenario.
     *
     * How to run:
     * Just set the two variables useThreads and maxMessages according to your needs,
     * and hit run!
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        // set to false to use multiprocessing instead of threads
        boolean useThreads = true;

        // max number of messages each player will send as well as receive before terminating
        // value passed must be greater than 0
        int maxMessages = 4;

        // accept parameters from the Terminal when running shell script
        if(args.length > 0){
            useThreads = Boolean.parseBoolean(args[0]);
        }
        if(args.length > 1){
            maxMessages = Integer.parseInt(args[1]);
        }

        // run the players (class instances) inside the same Java process but in separate threads
        if(useThreads) {
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

            System.out.println("Player communication ended successfully using Multithreading");
        }

        // run each player inside a separate Java process (instance of the Java Virtual Machine)
        else {
            ProcessBuilder processBuilder1 = new ProcessBuilder(
                    "java", "-cp", "target/classes", "com.anishshinde.process.PlayerServer", String.valueOf(maxMessages)
            );
            ProcessBuilder processBuilder2 = new ProcessBuilder(
                    "java", "-cp", "target/classes", "com.anishshinde.process.PlayerClient", String.valueOf(maxMessages)
            );

            processBuilder1.inheritIO();
            Process process1 = processBuilder1.start();
            processBuilder2.inheritIO();
            Process process2 = processBuilder2.start();

            process1.waitFor();
            process2.waitFor();
            System.out.println("Player communication ended successfully using Multiprocessing");
        }
    }

}
