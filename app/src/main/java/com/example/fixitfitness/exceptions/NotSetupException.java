package com.example.fixitfitness.exceptions;

public class NotSetupException extends Exception {
    public NotSetupException() {
        super("User has not completed setup");
    }
}
