package com.example.fixitfitness.exceptions;

public class InvalidLevelException extends SetupException {
    public InvalidLevelException() {
        super("Invalid level selected. Please select a level.");
    }
}
