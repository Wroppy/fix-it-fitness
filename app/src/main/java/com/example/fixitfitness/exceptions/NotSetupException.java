package com.example.fixitfitness.exceptions;

/**
 * An exception for when the user has not been setup set and has no routine
 */
public class NotSetupException extends Exception {
    public NotSetupException() {
        super("User has not completed setup");
    }
}
