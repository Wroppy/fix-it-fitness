package com.example.fixitfitness.exceptions;

/**
 * An exception for when the weight the user has input is invalid.
 */
public class InvalidWeightException extends SetupException {
    public InvalidWeightException() {
        super("Weight must be greater than 0.00");
    }
}
