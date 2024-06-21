package com.example.fixitfitness;

public enum InjuryType {
    UPPER_BODY("Upper Body", 0),
    LOWER_BODY("Lower Body", 1);

    private final String name;
    private final int type;

    InjuryType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static InjuryType getInjuryType(InjuryLocation location) {
        if (location.getLocation() <= 6) {
            return UPPER_BODY;
        } else {
            return LOWER_BODY;
        }
    }

}
