package com.example.fixitfitness.exceptions;

/**
 * An exception for when the user inputs an invalid name.
 */
public class InvalidNameException extends SetupException {
    public InvalidNameException() {
        super("Invalid name entered. Please enter a valid name.");
    }
}
