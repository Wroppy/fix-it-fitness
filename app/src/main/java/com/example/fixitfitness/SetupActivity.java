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
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fixitfitness.exceptions.InvalidHeightException;
import com.example.fixitfitness.exceptions.InvalidLevelException;
import com.example.fixitfitness.exceptions.InvalidWeightException;
import com.example.fixitfitness.exceptions.SetupException;
import com.example.fixitfitness.utils.Utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class SetupActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout layout;
    private RadioGroup footballLevelGroup;
    private LinearLayout injuryLocationGroup;
    private List<SwitchCompat> injuryButtons;

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
        injuryLocationGroup = findViewById(R.id.injury_location_group);
        injuryButtons = new ArrayList<>();

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
        addInjuryButtons();
    }

    public void setupUser(View view) {
        try {
            float height = getHeight();
            float weight = getWeight();
            FootballLevel level = getFootballLevel();

            Log.d("Level:", level.getName());

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

        for (int i = 0; i < footballLevels.size(); i++) {
            String level = footballLevels.get(i);
            addRadioButton(level, i);
        }

    }

    private void addRadioButton(String text, int id) {

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );

        // Sets the text of the RadioButton
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(text);
        radioButton.setTextSize(20);
        radioButton.setId(id);


        footballLevelGroup.addView(radioButton, params);
    }

    private List<String> getFootballLevels() {
        return FootballLevel.getFootballLevels();
    }

    private List<String> getInjuryTypes() {
        return InjuryLocation.getInjuryLocations();
    }

    private void addInjuryButtons() {
        // Add toggle buttons to the layout
        List<String> injuryTypes = getInjuryTypes();

        for (String type : injuryTypes) {
            addInjuryButton(type);
        }
    }

    private void addInjuryButton(String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Adds padding to the bottom of the button
        params.setMargins(0, 0, 0, 20);

        // Sets the text of the ToggleButton
        SwitchCompat toggleButton = new SwitchCompat(this);

        toggleButton.setText(text);
        toggleButton.setTextSize(20);

        injuryButtons.add(toggleButton);
        injuryLocationGroup.addView(toggleButton, params);
    }

    public FootballLevel getFootballLevel() throws SetupException {
        int selectedId = footballLevelGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            throw new InvalidLevelException();
        }

        RadioButton selectedButton = findViewById(selectedId);
        return FootballLevel.getFootballLevel(selectedButton.getText().toString());
    }
}