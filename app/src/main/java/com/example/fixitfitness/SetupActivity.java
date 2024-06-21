package com.example.fixitfitness;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fixitfitness.exceptions.InvalidHeightException;
import com.example.fixitfitness.exceptions.InvalidWeightException;
import com.example.fixitfitness.exceptions.SetupException;
import com.example.fixitfitness.utils.Utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.Arrays;
import java.util.List;

public class SetupActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout layout;
    private RadioGroup footballLevelGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scrollView = findViewById(R.id.questions_scrollview);
        layout = findViewById(R.id.questions_layout);
        footballLevelGroup = findViewById(R.id.football_level_group);

        // Add a global layout listener to detect when the layout has been drawn
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Check if the ScrollView can scroll
                boolean canScroll = scrollView.getChildAt(0).getHeight() > scrollView.getHeight();

                if (canScroll) {
                    // If the ScrollView can scroll, make the inner content take full height
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
                    params.height = scrollView.getHeight();
                    layout.setLayoutParams(params);
                }
            }
        });

        addRadioButtons();
    }

    public void setupUser(View view) {
        try {
            float height = getHeight();
            float weight = getWeight();


            // Save the user's height and weight
            // Redirect to the main activity
        } catch (SetupException e) {
            Log.e("SetupActivity", "Error setting up user", e);
            Utils.showPopup(this, e.getMessage());
        }
    }


    private float getWeight() throws InvalidWeightException {
        EditText weightEdit = findViewById(R.id.weight_edit);
        float weight;

        try {
            weight = Float.parseFloat(weightEdit.getText().toString());
        } catch (NumberFormatException e) {
            throw new InvalidWeightException();
        }

        if (weight <= 0) {
            throw new InvalidWeightException();
        }
        return weight;
    }

    private float getHeight() throws InvalidHeightException {
        EditText heightEdit = findViewById(R.id.height_edit);
        float height;

        try {
            height = Float.parseFloat(heightEdit.getText().toString());
        } catch (NumberFormatException e) {
            throw new InvalidHeightException();
        }

        if (height <= 0) {
            throw new InvalidHeightException();
        }

        return Float.parseFloat(heightEdit.getText().toString());
    }

    private void addRadioButtons() {
        // Add radio buttons to the layout
        List<String> footballLevels = getFootballLevels();

        for (String level : footballLevels) {
            addRadioButton(level);
        }

    }

    private void addRadioButton(String text) {

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );

        // Sets the text of the RadioButton
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(text);
        radioButton.setTextSize(20);


        footballLevelGroup.addView(radioButton, params);
    }

    private List<String> getFootballLevels() {
        return Arrays.asList(getResources().getStringArray(R.array.football_levels));
    }


}