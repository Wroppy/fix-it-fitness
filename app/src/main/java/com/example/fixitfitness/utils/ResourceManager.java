package com.example.fixitfitness.utils;

import android.content.Context;

import com.example.fixitfitness.UserInfo;
import com.example.fixitfitness.exceptions.NotSetupException;
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
        Routine routine = userInfo.getRoutine();

        // Write the routine to a file
        writeToFile(context, "routine.txt", routine.bundleToString());

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

    public String readFile(Context context, String filename) throws IOException {
        // Read from a file
        File directory = getDirectory(context);
        File file = new File(directory, filename);
        // Read the contents of the file, and print it to the log
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().replace("\n", "");

                if (line.isEmpty()) {
                    continue;
                }
                sb.append(line).append("\n");

            }
            return sb.toString().trim();

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error reading file");
        }
    }

    public UserInfo readUserInfo(Context context) throws NotSetupException {
        try {
            String name = readFile(context, "name.txt");
            String routineString = readFile(context, "routine.txt");
            Routine routine = new Routine(routineString);

            if (name.isEmpty() || routineString.isEmpty()) {
                throw new NotSetupException();
            }
            return new UserInfo(name, routine);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotSetupException();
        }
    }

    public void deleteUserInfo(Context context) {
        File directory = getDirectory(context);
        File nameFile = new File(directory, "name.txt");
        File routineFile = new File(directory, "routine.txt");
        nameFile.delete();
        routineFile.delete();
    }

}
