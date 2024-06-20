package com.example.fixitfitness.exceptions;

public class InvalidHeightException extends SetupException {

    public InvalidHeightException() {
        super("Height must be greater than 0.00");
    }
}
