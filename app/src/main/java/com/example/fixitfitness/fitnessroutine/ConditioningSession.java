package com.example.fixitfitness.fitnessroutine;

public class ConditioningSession implements FitnessSession {
    private final String name;

    private int distance;


    public ConditioningSession(int distance) {
        this.name = "Conditioning Session";
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "A conditioning session that includes a " + distance + "km run.";
    }

    public FitnessSession copy() {
        return new ConditioningSession(distance);
    }
}
