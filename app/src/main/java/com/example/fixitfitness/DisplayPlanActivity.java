package com.example.fixitfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fixitfitness.fitnessroutine.Routine;

public class DisplayPlanActivity extends AppCompatActivity {
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_plan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        Routine routine = new Routine(intent);

        Log.d("DisplayPlanActivity", "Name: " + name);
        Log.d("DisplayPlanActivity", "Routine: " + routine);

        TextView nameView = findViewById(R.id.hello_user_text_view);
        nameView.setText("Hello, " + name + "!");

        TextView weekA = findViewById(R.id.week_a_plan);
        String weekAString = routine.getWeekAString();
        Log.d("DisplayPlanActivity", "Week A: " + weekAString);
        weekA.setText(weekAString);

        TextView weekB = findViewById(R.id.week_b_plan);
        weekB.setText(routine.getWeekBString());

        this.userInfo = new UserInfo(name, routine);
    }
}