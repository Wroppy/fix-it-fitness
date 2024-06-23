package com.example.fixitfitness.fitnessroutine;

public class LowerBodyGymSession implements FitnessSession{
    private final String name;
    private final String description;

    public LowerBodyGymSession() {
        this.name = "Lower Body Gym Session";
        this.description = "A lower body gym session that includes squats, lunges, and leg presses.";
    }

    public LowerBodyGymSession(String bundleString) {
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
        return new LowerBodyGymSession();
    }

    @Override
    public String bundleToString() {
        return "LowerBodyGymSession";
    }

}
