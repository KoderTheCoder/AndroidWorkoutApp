package com.example.koder.pockettrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by koder on 24/09/2017.
 */

public class CreateExercise extends AppCompatActivity {
    Button nextButton2;
    Button finishButton;
    EditText name;
    EditText repititions;
    EditText time;
    String workoutName;
    Exercises exercises;
    TextView exerciseNumber;
    int exerciseCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise);
        final Database db = new Database(this);

        nextButton2 = (Button)findViewById(R.id.createExerciseNextButton);
        finishButton = (Button)findViewById(R.id.finishWorkoutCreateButton);
        name = (EditText)findViewById(R.id.name);
        repititions = (EditText)findViewById(R.id.repititions);
        time = (EditText)findViewById(R.id.time);
        workoutName = getIntent().getStringExtra("WORKOUT NAME");
        exercises = (Exercises) getIntent().getSerializableExtra("exercises");

        exerciseCount = getIntent().getIntExtra("count", exerciseCount);
        exerciseNumber = (TextView)findViewById(R.id.createExerciseNumber);
        exerciseNumber.setText(Integer.toString(exerciseCount));

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty() && (!repititions.getText().toString().isEmpty() || !time.getText().toString().isEmpty())){
                    if(repititions.getText().toString().isEmpty() && !time.getText().toString().isEmpty()){
                        exercises.addExercise(name.getText().toString(),0, Integer.parseInt(time.getText().toString()));
                        Intent nextScreen = new Intent(CreateExercise.this, CreateExercise.class);
                        nextScreen.putExtra("WORKOUT NAME", workoutName);
                        nextScreen.putExtra("exercises", exercises);
                        nextScreen.putExtra("count", ++exerciseCount);
                        startActivityForResult(nextScreen, 1);
                        finish();
                    }else if(time.getText().toString().isEmpty() && !repititions.getText().toString().isEmpty()){
                        exercises.addExercise(name.getText().toString(),Integer.parseInt(repititions.getText().toString()), 0);
                        Intent nextScreen = new Intent(CreateExercise.this, CreateExercise.class);
                        nextScreen.putExtra("WORKOUT NAME", workoutName);
                        nextScreen.putExtra("exercises", exercises);
                        nextScreen.putExtra("count", ++exerciseCount);
                        startActivityForResult(nextScreen, 1);
                        finish();
                    }else {
                        Toast.makeText(CreateExercise.this, "Error: Enter either the repititions or the time for this exercise", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreateExercise.this, "Error: Please Fill out all required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addWorkout(workoutName, exercises);
                Toast.makeText(CreateExercise.this, "Workout added", Toast.LENGTH_SHORT).show();
                Intent nextScreen = new Intent(CreateExercise.this, MainActivity.class);
                startActivityForResult(nextScreen, 1);
                finish();
            }
        });
    }
}
