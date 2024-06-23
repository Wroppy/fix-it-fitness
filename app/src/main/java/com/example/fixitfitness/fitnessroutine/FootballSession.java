package com.example.fixitfitness.fitnessroutine;

public class FootballSession implements FitnessSession {
    private final String name;
    private final String description;

    public FootballSession() {
        this.name = "Football";
        this.description = "A football session that includes a warm-up, drills, and a scrimmage.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public FitnessSession copy() {
        return new FootballSession();
    }

}
