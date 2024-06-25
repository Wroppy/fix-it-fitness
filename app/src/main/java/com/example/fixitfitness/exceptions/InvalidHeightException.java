package com.example.fixitfitness.exceptions;


/**
 * An exception for when the height the user inputs is invalid.
 */
public class InvalidHeightException extends SetupException {
    public InvalidHeightException() {
        super("Height must be greater than 0.00");
    }
}
