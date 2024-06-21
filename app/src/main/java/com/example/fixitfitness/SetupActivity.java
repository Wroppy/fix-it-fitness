package com.example.fixitfitness;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fixitfitness.exceptions.InvalidHeightException;
import com.example.fixitfitness.exceptions.InvalidWeightException;
import com.example.fixitfitness.exceptions.SetupException;
import com.example.fixitfitness.utils.Utils;

import android.widget.ScrollView;

public class SetupActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout layout;

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


}