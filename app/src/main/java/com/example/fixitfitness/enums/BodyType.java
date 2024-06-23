package com.example.fixitfitness.enums;

/**
 * Enum for 4 body types.
 */
public enum BodyType {
    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy"),
    VERY_HEAVY("Very Heavy");

    private final String bodyType;

    BodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return bodyType;
    }

}
