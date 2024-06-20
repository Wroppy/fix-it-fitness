package com.example.fixitfitness.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Utils {
    public static void showPopup(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
