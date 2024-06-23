package com.example.fixitfitness.fitnessroutine;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fixitfitness.enums.BodyType;
import com.example.fixitfitness.enums.FootballLevel;
import com.example.fixitfitness.enums.InjuryType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a routine that a user can follow.
 */

public class Routine {
    private List<FitnessSession> sessions;
    private int numSessions;

    public Routine(FootballLevel level, InjuryType injuryType, BodyType bodyType) {
        this.sessions = new ArrayList<>();
        numSessions = level.getWeeklySessions();
        createRoutine(level, injuryType, bodyType);

    }

    private void createRoutine(FootballLevel level, InjuryType injuryType, BodyType bodyType) {
        FitnessSession firstGymSession, secondGymSession;
        int distance = getDistance(injuryType);

        if (injuryType == InjuryType.UPPER_BODY) {
            firstGymSession = new UpperBodyGymSession();
            secondGymSession = new LowerBodyGymSession();
        } else {
            firstGymSession = new LowerBodyGymSession();
            secondGymSession = new UpperBodyGymSession();
        }

        addFootballSession();

        switch (bodyType) {
            case LIGHT:
                sessions.add(firstGymSession.copy());
                addFootballSession();
                sessions.add(secondGymSession.copy());

                if (shouldAddExtraSession(level)) {
                    addFootballSession();
                    sessions.add(firstGymSession.copy());
                }

                repeatSessions();
                break;
            case MEDIUM:
                // Week A
                sessions.add(firstGymSession.copy());

                addFootballSession();
                sessions.add(new ConditioningSession(distance));

                // Week B
                addFootballSession();
                sessions.add(secondGymSession.copy());

                addFootballSession();
                sessions.add(new ConditioningSession(distance));

                if (shouldAddExtraSession(level)) {
                    addFootballSession();
                    sessions.add(firstGymSession.copy());

                    addFootballSession();
                    sessions.add(new ConditioningSession(distance));

                    addFootballSession();
                    sessions.add(secondGymSession.copy());

                    addFootballSession();
                    sessions.add(new ConditioningSession(distance));

                }
            case HEAVY:
                // Week A
                sessions.add(new ConditioningSession(distance));

                addFootballSession();
                sessions.add(firstGymSession.copy());

                // Week B
                addFootballSession();
                sessions.add(new ConditioningSession(distance));

                addFootballSession();
                sessions.add(secondGymSession.copy());

                if (shouldAddExtraSession(level)) {
                    addFootballSession();
                    sessions.add(new ConditioningSession(distance));

                    addFootballSession();
                    sessions.add(firstGymSession.copy());

                    addFootballSession();
                    sessions.add(new ConditioningSession(distance));

                    addFootballSession();
                    sessions.add(secondGymSession.copy());
                }

                break;
            case VERY_HEAVY:
                sessions.add(new ConditioningSession(distance));
                addFootballSession();
                sessions.add(new ConditioningSession(distance));

                if (shouldAddExtraSession(level)) {
                    addFootballSession();
                    sessions.add(new ConditioningSession(distance));
                }

                repeatSessions();
                break;
        }


    }

    private void addFootballSession() {
        sessions.add(new FootballSession());
    }

    private boolean shouldAddExtraSession(FootballLevel level) {
        return level == FootballLevel.SEMI_COMMITTED || level == FootballLevel.COMMITTED;
    }

    private void repeatSessions() {
        List<FitnessSession> repeatedSessions = new ArrayList<>();
        for (FitnessSession session : sessions) {
            if (session instanceof LowerBodyGymSession) {
                repeatedSessions.add(new UpperBodyGymSession());
            } else if (session instanceof UpperBodyGymSession) {
                repeatedSessions.add(new LowerBodyGymSession());
            } else {
                repeatedSessions.add(session.copy());
            }
        }
        sessions.addAll(repeatedSessions);
    }

    private int getDistance(InjuryType injuryType) {
        switch (injuryType) {
            case UPPER_BODY:
                return 10;
            case LOWER_BODY:
            case COMBINED:
                return 12;
            default:
                return 8;
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder routine = new StringBuilder();
        for (FitnessSession session : sessions) {
            routine.append(session.getName()).append(": ").append(session.getDescription()).append("\n");
        }
        return routine.toString();
    }

    /**
     * Bundles the routine into an intent.
     *
     * @param intent the intent to bundle the routine into
     */
    public void bundleRoutine(Intent intent) {
        intent.putExtra("fitnessSessions", sessions.size());

        for (int i = 0; i < sessions.size(); i++) {
            intent.putExtra("fitnessSession" + i, sessions.get(i).bundleToString());
        }
    }

    public Routine(Intent intent) {
        this.sessions = new ArrayList<>();
        int numSessions = intent.getIntExtra("fitnessSessions", 0);

        this.numSessions = numSessions / 2;

        for (int i = 0; i < numSessions; i++) {
            String sessionString = intent.getStringExtra("fitnessSession" + i);

            if (sessionString == null) {
                continue;
            }

            FitnessSession session = createSession(sessionString);
            sessions.add(session);
        }
    }

    private FitnessSession createSession(String sessionString) {
        String[] splitString = sessionString.split(",");
        switch (splitString[0]) {
            case "ConditioningSession":
                return new ConditioningSession(sessionString);
            case "Football":
                return new FootballSession(sessionString);
            case "LowerBodyGymSession":
                return new LowerBodyGymSession(sessionString);
            case "UpperBodyGymSession":
                return new UpperBodyGymSession(sessionString);
            default:
                return null;
        }
    }

    public String getWeekAString() {
        StringBuilder weekA = new StringBuilder();
        Log.d("NumSessions", "" + numSessions);
        for (int i = 0; i < numSessions; i++) {
            weekA.append(i + 1).append(") ").append(sessions.get(i).getName()).append("\n");
            Log.d("Routine", i + ") " + sessions.get(i).getName());
        }
        return weekA.toString();
    }

    public String getWeekBString() {
        StringBuilder weekB = new StringBuilder();
        int num = 1;
        for (int i = numSessions; i < 2 * numSessions; i++) {
            weekB.append(num++).append(") ").append(sessions.get(i).getName()).append("\n");
            Log.d("Routine", i + ") " + sessions.get(i).getName());
        }
        return weekB.toString();
    }
}