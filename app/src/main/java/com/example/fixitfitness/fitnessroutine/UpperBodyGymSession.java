package com.example.fixitfitness.fitnessroutine;

public class UpperBodyGymSession implements FitnessSession{

    private final String name;
    private final String description;

    public UpperBodyGymSession() {
        this.name = "Upper Body Gym Session";
        this.description = "A gym session that focuses on the upper body, including chest, back, and arms.";
    }

    public UpperBodyGymSession(String bundleString) {
        this();
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
        return new UpperBodyGymSession();
    }

    @Override
    public String bundleToString() {
        return "UpperBodyGymSession";
    }


}
