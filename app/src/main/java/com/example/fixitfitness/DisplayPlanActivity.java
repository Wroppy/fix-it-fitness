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
import com.example.fixitfitness.utils.ResourceManager;

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


        displayUserRoutine();
    }

    /**
     * Displays the user's routine and name when the activity is opened up.
     */
    private void displayUserRoutine() {
        Intent intent = getIntent();

        // Gets the routine and name associated passed in from other activities
        String name = intent.getStringExtra("name");
        Routine routine = new Routine(intent);

        // Logging for debugging reasons
        Log.d("DisplayPlanActivity", "Name: " + name);
        Log.d("DisplayPlanActivity", "Routine: " + routine);

        // Changes the view to say hello to the user
        TextView nameView = findViewById(R.id.hello_user_text_view);
        nameView.setText(getString(R.string.user_welcome, name));

        // Displays each week's plan
        TextView weekA = findViewById(R.id.week_a_plan);
        weekA.setText(routine.getWeekAString());

        TextView weekB = findViewById(R.id.week_b_plan);
        weekB.setText(routine.getWeekBString());

        this.userInfo = new UserInfo(name, routine);
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.writeUserInfo(this, this.userInfo);
    }
}