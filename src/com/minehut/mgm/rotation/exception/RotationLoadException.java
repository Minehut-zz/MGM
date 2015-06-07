package com.minehut.mgm.rotation.exception;

/**
 * Created by luke on 5/30/15.
 */
public class RotationLoadException extends Exception {

    private final String message;

    public RotationLoadException(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }


}
