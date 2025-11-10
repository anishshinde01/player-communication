package com.anishshinde.common;

/**
 * Utility class providing stop conditions for initiator and responder players
 * across multithreading and multiprocessing modules.
 *
 * Responsibilities:
 * - Stop conditions define when a player's messaging loop should terminate
 *
 * Developer Decisions:
 * - The methods of this class are used in common by both thread and process
 *   implementations of the 2-Player Communication Program, since the 'stop condition'
 *   is same for both
 */
public class StopConditions {

    private StopConditions(){}

    /** Determines whether the initiator should stop its messaging loop */
    public static boolean initiatorStopConditionFulfilled(int responsesReceived, int messagesSent, int maxMessages){
        return responsesReceived == maxMessages && messagesSent == maxMessages;
    }

    /** Determines whether the responder should stop its messaging loop */
    public static boolean responderStopConditionFulfilled(int responsesSent, int maxMessages){
        return responsesSent == maxMessages;
    }

}
