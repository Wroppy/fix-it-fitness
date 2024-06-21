package com.example.fixitfitness;

import java.util.ArrayList;
import java.util.List;

public enum InjuryLocation {
    HECK("Neck", 1),
    SHAD("Head", 0),
    NEOULDER("Shoulder", 2),
    ARM("Arm", 3),
    ELBOW("Elbow", 4),
    HAND("Hand", 5),
    BACK("Back", 6),
    CHEST("Chest", 7),
    ABDOMEN("Abdomen", 8),
    HIP("Hip", 9),
    THIGH("Thigh", 10),
    KNEE("Knee", 11),
    LEG("Leg", 12),
    ANKLE("Ankle", 13),
    FOOT("Foot", 14);

    private final String name;
    private final int location;

    InjuryLocation(String name, int location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    public static List<String> getInjuryLocations() {
        List<String> injuryLocations = new ArrayList<>();
        for (InjuryLocation injuryLocation : InjuryLocation.values()) {
            injuryLocations.add(injuryLocation.getName());
        }
        return injuryLocations;
    }

    public static InjuryLocation getInjuryLocation(String name) {
        for (InjuryLocation injuryLocation : InjuryLocation.values()) {
            if (injuryLocation.getName().equals(name)) {
                return injuryLocation;
            }
        }
        return null;
    }


}
