package com.example.fixitfitness.utils;

import android.content.Context;

import com.example.fixitfitness.UserInfo;
import com.example.fixitfitness.fitnessroutine.FitnessSession;
import com.example.fixitfitness.fitnessroutine.Routine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ResourceManager {
    private File getDirectory(Context context) {
        File directory = new File(context.getFilesDir(), "user_info");
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public void writeUserInfo(Context context, UserInfo userInfo) {
        StringBuilder sb = new StringBuilder();
        Routine routine = userInfo.getRoutine();
        List<FitnessSession> sessions = routine.getSessions();
        sb.append(sessions.size()).append("\n");

        for (int i = 0; i < sessions.size(); i++) {
            FitnessSession session = sessions.get(i);
            sb.append(session.bundleToString()).append("\n");
        }
        // Write the routine to a file
        writeToFile(context, "routine.txt", sb.toString());

        String name = userInfo.getName();
        writeToFile(context, "name.txt", name);
    }


    private void writeToFile(Context context, String filename, String text) {
        try {
            File directory = getDirectory(context);

            File file = new File(directory, filename);
            FileWriter writer = new FileWriter(file);
            writer.append(text);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testWriteToFile(Context context) {
        writeToFile(context, "test.txt", "Hello, world!");
    }

    public String readFile(Context context, String filename) {
        // Read from a file
        File directory = getDirectory(context);
        File file = new File(directory, filename);
        // Read the contents of the file, and print it to the log
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public UserInfo readUserInfo(Context context) {
        String name = readFile(context, "name.txt");
        String routineString = readFile(context, "routine.txt");
        Routine routine = new Routine(routineString);
        return new UserInfo(name, routine);
    }
}
