package com.example.fixitfitness;

import java.util.ArrayList;
import java.util.List;

public enum FootballLevels {
    CASUAL("Casual", 0),
    AMATEUR("Amateur", 1),
    SEMI_COMMITTED("Semi Committed", 2),
    COMMITTED("Committed", 3);

    private final String name;
    private final int level;

    FootballLevels(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public static FootballLevels fromLevel(int level) {
        for (FootballLevels footballLevel : FootballLevels.values()) {
            if (footballLevel.getLevel() == level) {
                return footballLevel;
            }
        }
        return null;
    }

    public static List<String> getFootballLevels() {
        List<String> footballLevels = new ArrayList<>();
        for (FootballLevels footballLevel : FootballLevels.values()) {
            footballLevels.add(footballLevel.getName());
        }
        return footballLevels;
    }

    public static int getLevel(String name) {
        for (FootballLevels footballLevel : FootballLevels.values()) {
            if (footballLevel.getName().equals(name)) {
                return footballLevel.getLevel();
            }
        }
        return -1;
    }

    public static boolean isValidLevel(String name) {
        for (FootballLevels footballLevel : FootballLevels.values()) {
            if (footballLevel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLevel(int level) {
        for (FootballLevels footballLevel : FootballLevels.values()) {
            if (footballLevel.getLevel() == level) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLevel(FootballLevels footballLevel) {
        return footballLevel != null;
    }





}