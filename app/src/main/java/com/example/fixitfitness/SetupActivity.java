package com.example.fixitfitness;

import android.content.Intent;
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

import com.example.fixitfitness.enums.BodyType;
import com.example.fixitfitness.enums.FootballLevel;
import com.example.fixitfitness.enums.InjuryLocation;
import com.example.fixitfitness.enums.InjuryType;
import com.example.fixitfitness.exceptions.InvalidHeightException;
import com.example.fixitfitness.exceptions.InvalidLevelException;
import com.example.fixitfitness.exceptions.InvalidNameException;
import com.example.fixitfitness.exceptions.InvalidWeightException;
import com.example.fixitfitness.exceptions.SetupException;
import com.example.fixitfitness.fitnessroutine.Routine;
import com.example.fixitfitness.utils.ResourceManager;
import com.example.fixitfitness.utils.Utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetupActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private LinearLayout layout;
    private RadioGroup footballLevelGroup;
    private LinearLayout injuryLocationGroup;
    private List<SwitchCompat> injuryButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Sets up and displays the activity
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupVariables();

        // Sets up the layout to take full height if the ScrollView can scroll
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

        // Adds radio buttons and toggle buttons to the layout
        addRadioButtons();
        addInjuryButtons();
    }

    /**
     * Sets up and assignments the appropriate classes to each attribute.
     */
    private void setupVariables() {
        // Sets up the variables for future use
        scrollView = findViewById(R.id.questions_scrollview);
        layout = findViewById(R.id.questions_layout);
        footballLevelGroup = findViewById(R.id.football_level_group);
        injuryLocationGroup = findViewById(R.id.injury_location_group);
        injuryButtons = new ArrayList<>();

    }

    public void setupUser(View view) {
        try {
            // Gets the user's setup information
            String name = getName();
            float height = getHeight();
            float weight = getWeight();
            FootballLevel level = getFootballLevel();
            InjuryType injuryType = getInjuryType();

            Log.d("Name:", name);
            Log.d("Height:", String.valueOf(height));
            Log.d("Weight:", String.valueOf(weight));
            Log.d("Level:", level.getName());
            Log.d("Injury Type:", injuryType.getName());

            // Creates the associated routine
            Routine routine = createRoutine(level, injuryType, weight, height);

            // Saves it to the phone's storage
            UserInfo userInfo = new UserInfo(name, routine);
            ResourceManager resourceManager = new ResourceManager();
            resourceManager.writeUserInfo(this, userInfo);

            // Displays the routine to the user in a different activity
            Intent intent = new Intent(this, DisplayPlanActivity.class);
            routine.bundleRoutine(intent);
            intent.putExtra("name", name);
            startActivity(intent);

        } catch (SetupException e) {
            Log.e("SetupActivity", "Error setting up user", e);
            Utils.showPopup(this, e.getMessage());
        }
    }

    /**
     * Gets the weight of the user from the EditText
     *
     * @return the weight of the user
     * @throws InvalidWeightException if the weight is empty or less than or equal to 0
     */
    private float getWeight() throws InvalidWeightException {
        EditText weightEdit = findViewById(R.id.weight_edit);
        float weight;

        try {
            // Checks for a valid float value
            weight = Float.parseFloat(weightEdit.getText().toString());
        } catch (NumberFormatException e) {
            throw new InvalidWeightException();
        }

        // Checks if the weight is less than or equal to 0
        if (weight <= 0) {
            throw new InvalidWeightException();
        }
        return weight;
    }

    /**
     * Gets the height of the user from the EditText
     *
     * @return the height of the user
     * @throws InvalidHeightException if the height is empty or less than or equal to 0
     */
    private float getHeight() throws InvalidHeightException {
        EditText heightEdit = findViewById(R.id.height_edit);
        float height;

        try {
            // Checks for a valid float value
            height = Float.parseFloat(heightEdit.getText().toString());
        } catch (NumberFormatException e) {
            throw new InvalidHeightException();
        }

        // Checks if the height is less than or equal to 0
        if (height <= 0) {
            throw new InvalidHeightException();
        }

        return Float.parseFloat(heightEdit.getText().toString());
    }

    /**
     * Adds radio buttons to the layout with the given text
     */
    private void addRadioButtons() {
        // Add radio buttons to the layout
        List<String> footballLevels = getFootballLevels();

        for (int i = 0; i < footballLevels.size(); i++) {
            String level = footballLevels.get(i);
            addRadioButton(level, i);
        }

    }

    /**
     * Adds a RadioButton to the layout with the given text and id
     *
     * @param text the text to display on the RadioButton
     * @param id   the id of the RadioButton
     */
    private void addRadioButton(String text, int id) {

        // Sets the layout parameters for the RadioButton
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        // Sets the text of the RadioButton
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(text);
        radioButton.setTextSize(20);
        radioButton.setId(id);


        footballLevelGroup.addView(radioButton, params);
    }

    /**
     * Gets the football levels from the FootballLevel enum
     *
     * @return the football levels
     */
    private List<String> getFootballLevels() {
        return FootballLevel.getFootballLevels();
    }

    /**
     * Gets the injury locations from the InjuryLocation enum
     *
     * @return the injury locations
     */
    private List<String> getInjuryTypes() {
        return InjuryLocation.getInjuryLocations();
    }

    /**
     * Adds toggle buttons to the layout with the given text
     */
    private void addInjuryButtons() {
        // Add toggle buttons to the layout
        List<String> injuryTypes = getInjuryTypes();

        for (String type : injuryTypes) {
            addInjuryButton(type);
        }
    }

    /**
     * Adds a toggle button to the layout with the given text
     *
     * @param text the text to display on the button
     */
    private void addInjuryButton(String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Adds padding to the bottom of the button
        params.setMargins(0, 0, 0, 20);

        // Sets the text of the ToggleButton
        SwitchCompat toggleButton = new SwitchCompat(this);

        toggleButton.setText(text);
        toggleButton.setTextSize(20);

        // Adds the button to the layout and the list of buttons
        injuryButtons.add(toggleButton);
        injuryLocationGroup.addView(toggleButton, params);
    }

    /**
     * Gets the football level of the user based on the selected radio button
     *
     * @return the football level of the user
     * @throws SetupException if no radio button is selected
     */
    private FootballLevel getFootballLevel() throws SetupException {
        int selectedId = footballLevelGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            throw new InvalidLevelException();
        }

        RadioButton selectedButton = findViewById(selectedId);
        return FootballLevel.getFootballLevel(selectedButton.getText().toString());
    }

    /**
     * Gets the injury locations of the user based on the selected injury buttons
     *
     * @return the injury locations of the user
     */
    private List<InjuryLocation> getInjuryLocations() {
        List<InjuryLocation> locations = new ArrayList<>();
        for (SwitchCompat button : injuryButtons) {
            if (button.isChecked()) {
                locations.add(InjuryLocation.getInjuryLocation(button.getText().toString()));
            }
        }
        return locations;
    }

    /**
     * Gets the injury type of the user based on the selected injury locations
     *
     * @return the injury type of the user
     */
    private InjuryType getInjuryType() {
        List<InjuryLocation> locations = getInjuryLocations();
        if (locations.isEmpty()) {
            return InjuryType.HEALTHY;
        }

        // Gets the number of unique injury types
        Set<InjuryType> injuryTypes = new HashSet<>();
        for (InjuryLocation location : locations) {
            injuryTypes.add(InjuryType.getInjuryType(location));
        }

        // If there is more than one injury type, return COMBINED
        if (injuryTypes.size() > 1) {
            return InjuryType.COMBINED;
        }

        // Otherwise, return the single injury type
        return injuryTypes.iterator().next();
    }

    /**
     * Gets the name of the user from the EditText
     *
     * @return the name of the user
     * @throws InvalidNameException if the name is empty
     */
    private String getName() throws InvalidNameException {
        EditText nameEdit = findViewById(R.id.name_edit);
        // Gets the text from the EditText and removes any leading or trailing whitespace
        String name = nameEdit.getText().toString().trim();

        if (name.isEmpty()) {
            throw new InvalidNameException();
        }

        // Checks for spaces or special characters in the name
        if (!name.matches("[a-zA-Z]+")) {
            throw new InvalidNameException();
        }

        return Utils.capitalize(name);
    }


    /**
     * Returns a routine based on the user's input params
     *
     * @param level      the football level the user selected
     * @param injuryType the injury type the user has
     * @param weight     the weight of the user
     * @param height     the height of the user
     * @return a routine associated with the user
     */
    private Routine createRoutine(FootballLevel level, InjuryType injuryType, float weight, float height) {
        BodyType bodyType = getBodyType(weight, height);
        Log.d("Body Type:", bodyType.toString());
        return new Routine(level, injuryType, bodyType);
    }


    /**
     * Given the weight (kg) and height (cm), returns the body tyope associated with their bmi.
     *
     * @param weight the user's inputted weight
     * @param height the user's inputted height
     * @return the body type of the
     */
    private BodyType getBodyType(float weight, float height) {
        float bmi = 10000 * weight / (height * height);
        Log.d("BMI: ", String.valueOf(bmi));
        if (bmi < 18.5) {
            return BodyType.LIGHT;
        } else if (bmi < 24.9) {
            return BodyType.MEDIUM;
        } else if (bmi < 29.9) {
            return BodyType.HEAVY;
        } else {
            return BodyType.VERY_HEAVY;
        }
    }

}