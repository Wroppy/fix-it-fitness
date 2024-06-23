package com.example.fixitfitness.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void showPopup(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
