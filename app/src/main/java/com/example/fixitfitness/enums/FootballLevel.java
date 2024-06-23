package com.example.fixitfitness.enums;

import java.util.ArrayList;
import java.util.List;

public enum FootballLevel {
    CASUAL("Casual", 0, 4),
    AMATEUR("Amateur", 1, 4),
    SEMI_COMMITTED("Semi Committed", 2, 6),
    COMMITTED("Committed", 3, 6);

    private final String name;
    private final int level;
    private final int weeklySessions;

    FootballLevel(String name, int level, int weeklySessions) {
        this.name = name;
        this.level = level;
        this.weeklySessions = weeklySessions;

    }

    public int getWeeklySessions() {
        return weeklySessions;
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

    public static FootballLevel getFootballLevel(String name) {
        for (FootballLevel footballLevel : FootballLevel.values()) {
            if (footballLevel.getName().equals(name)) {
                return footballLevel;
            }
        }
        return null;
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