package com.anishshinde.thread;

/**
 * Utility class providing stop conditions for both initiator and responder.
 * Stop conditions define when a player's messaging loop should terminate
 */
public class StopConditions {

    private StopConditions(){}

    // Determines whether the initiator should stop its messaging loop
    public static boolean initiatorStopConditionFulfilled(int responsesReceived, int messagesSent, int maxMessages){
        return responsesReceived == maxMessages && messagesSent == maxMessages;
    }

    // Determines whether the responder should stop its messaging loop
    public static boolean responderStopConditionFulfilled(int responsesSent, int maxMessages){
        return responsesSent == maxMessages;
    }

}
