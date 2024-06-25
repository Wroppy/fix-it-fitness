package com.example.fixitfitness.exceptions;

/**
 * An exception for when the user selects an invalid football level.
 */
public class InvalidLevelException extends SetupException {
    public InvalidLevelException() {
        super("Invalid level selected. Please select a level.");
    }
}
