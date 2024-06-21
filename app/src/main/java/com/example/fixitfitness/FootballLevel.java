package com.example.fixitfitness;

import java.util.ArrayList;
import java.util.List;

public enum FootballLevel {
    CASUAL("Casual", 0),
    AMATEUR("Amateur", 1),
    SEMI_COMMITTED("Semi Committed", 2),
    COMMITTED("Committed", 3);

    private final String name;
    private final int level;

    FootballLevel(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public static FootballLevel fromLevel(int level) {
        for (FootballLevel footballLevel : FootballLevel.values()) {
            if (footballLevel.getLevel() == level) {
                return footballLevel;
            }
        }
        return null;
    }

    public static List<String> getFootballLevels() {
        List<String> footballLevels = new ArrayList<>();
        for (FootballLevel footballLevel : FootballLevel.values()) {
            footballLevels.add(footballLevel.getName());
        }
        return footballLevels;
    }

    public static int getLevel(String name) {
        for (FootballLevel footballLevel : FootballLevel.values()) {
            if (footballLevel.getName().equals(name)) {
                return footballLevel.getLevel();
            }
        }
        return -1;
    }

    public static boolean isValidLevel(String name) {
        for (FootballLevel footballLevel : FootballLevel.values()) {
            if (footballLevel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLevel(int level) {
        for (FootballLevel footballLevel : FootballLevel.values()) {
            if (footballLevel.getLevel() == level) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLevel(FootballLevel footballLevel) {
        return footballLevel != null;
    }





}