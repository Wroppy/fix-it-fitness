package com.example.fixitfitness.exceptions;

public class InvalidWeightException extends SetupException {

    public InvalidWeightException() {
        super("Weight must be greater than 0.00");
    }
}
