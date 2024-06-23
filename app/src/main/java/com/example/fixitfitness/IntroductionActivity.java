package com.example.fixitfitness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fixitfitness.exceptions.NotSetupException;
import com.example.fixitfitness.utils.ResourceManager;

public class IntroductionActivity extends AppCompatActivity {
    private boolean isSetup;
    ResourceManager resourceManager;
    UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_introduction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resourceManager = new ResourceManager();
        try {
            userInfo = resourceManager.readUserInfo(this);
            isSetup = true;
        } catch (NotSetupException e) {
            isSetup = false;
        }

        Log.d("IntroductionActivity", "Is setup: " + isSetup);

    }

    public void onContinueClick(View view) {
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }


}