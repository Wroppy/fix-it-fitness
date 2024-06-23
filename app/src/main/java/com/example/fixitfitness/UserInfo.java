package com.example.fixitfitness;

import com.example.fixitfitness.fitnessroutine.Routine;

public class UserInfo {
    private Routine routine;
    private String name;

    public UserInfo(String name, Routine routine) {
        this.name = name;
        this.routine = routine;
    }

    public String getName() {
        return name;
    }

    public Routine getRoutine() {
        return routine;
    }

}
