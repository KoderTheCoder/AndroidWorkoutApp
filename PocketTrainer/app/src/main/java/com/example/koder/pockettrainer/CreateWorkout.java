package com.example.koder.pockettrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by koder on 12/09/2017.
 */

public class CreateWorkout extends AppCompatActivity{

    Button nextButton;
    EditText workoutName;
    Exercises exercises;
    int exerciseCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout);

        nextButton = (Button)findViewById(R.id.createWorkoutNext);
        workoutName = (EditText)findViewById(R.id.workoutName);
        exercises = new Exercises();
        exercises.addExercise("first", 0, 0);
        exerciseCount = 1;
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!workoutName.getText().toString().isEmpty()){
                    Intent nextScreen = new Intent(CreateWorkout.this, CreateExercise.class);
                    nextScreen.putExtra("WORKOUT NAME", workoutName.getText().toString());
                    nextScreen.putExtra("exercises", exercises);
                    nextScreen.putExtra("count", exerciseCount);
                    startActivityForResult(nextScreen, 1);
                    finish();
                }else{
                    Toast.makeText(CreateWorkout.this, "Error: Text field empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
