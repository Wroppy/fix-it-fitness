package com.example.fixitfitness.fitnessroutine;

public interface FitnessSession {
    /**
     * Returns the name of the exercise.
     *
     * @return the name of the exercise
     */
    String getName();

    /**
     * Returns the description of the exercise.
     *
     * @return the description of the exercise
     */
    String getDescription();

    /**
     * Returns a copy of the fitness session instance
     *
     * @return a copy of the fitness session instance
     */
    FitnessSession copy();

    /**
     * Returns a string representation of the session.
     * Should have a corresponding FitnessSession(String sessionString) constructor to convert the
     * string to a session class .
     *
     * @return a string representing the current session instance
     */
    String bundleToString();
}
