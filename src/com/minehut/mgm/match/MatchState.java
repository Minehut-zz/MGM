package com.minehut.mgm.match;

/**
 * Created by luke on 5/30/15.
 */
public enum MatchState {

    WAITING(),
    STARTING(),
    PLAYING(),
    ENDED(),
    CYCLING();

    public String toString() {
        switch (this) {
            case WAITING:
                return "Waiting";
            case STARTING:
                return "Starting";
            case PLAYING:
                return "Playing";
            case ENDED:
                return "Ended";
            case CYCLING:
                return "Cycling";
            default:
                return "";
        }
    }
}
