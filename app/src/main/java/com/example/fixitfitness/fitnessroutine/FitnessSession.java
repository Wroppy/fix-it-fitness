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

    FitnessSession copy();

    String bundleToString();
}
