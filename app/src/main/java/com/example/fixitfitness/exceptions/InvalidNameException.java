package com.example.fixitfitness.exceptions;

public class InvalidNameException extends SetupException {
    public InvalidNameException() {
        super("Invalid name entered. Please enter a valid name.");
    }
}
