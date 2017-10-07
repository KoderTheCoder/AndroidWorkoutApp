package com.example.koder.pockettrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by koder on 6/10/2017.
 */

public class Workout extends AppCompatActivity {
    Exercises exercises;
    Button nextButton;
    TextView name;
    TextView unit;
    TextView amount;
    int currentExercise;
    long startTime;
    int time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);

        exercises = (Exercises)getIntent().getSerializableExtra("exercises");
        nextButton = (Button) findViewById(R.id.nextExercise);
        name = (TextView) findViewById(R.id.exerciseName);
        unit = (TextView) findViewById(R.id.unit);
        amount = (TextView) findViewById(R.id.exerciseAmount);
        currentExercise = getIntent().getIntExtra("currentExercise", currentExercise);
        startTime = getIntent().getLongExtra("startTime", startTime);
        name.setText(exercises.exercisesArray.get(currentExercise).getName());
        time = exercises.exercisesArray.get(currentExercise).getTime();

        if(exercises.exercisesArray.get(currentExercise).getTime() == 0){
            unit.setText("Repititions");
            amount.setText(Integer.toString(exercises.exercisesArray.get(currentExercise).getRepitions()));
        }else{
            unit.setText("Seconds");
            amount.setText(Integer.toString(time));
            long oldTime = System.currentTimeMillis()/1000;
            new Thread(new Runnable(){
                public void run(){
                    long oldTime = System.currentTimeMillis()/1000;
                    String amounts = amount.getText().toString();
                    while(Integer.parseInt(amount.getText().toString()) > 0) {
                        if(((System.currentTimeMillis()/1000) - oldTime) >= 1){
                            time = time - 1;
                            oldTime = System.currentTimeMillis()/1000;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    amount.setText(Integer.toString(time));
                                }
                            });
                        }
                    }
                }
            }).start();
            /*while(amount.getText() != "x"){
                if(((System.currentTimeMillis()/1000) - oldTime) > 1){
                    int newAmount = Integer.parseInt(amount.getText().toString()) - 1;
                    amount.setText(Integer.toString(newAmount));
                    oldTime = System.currentTimeMillis()/1000;
                }
            }*/
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentExercise < (exercises.exercisesArray.size()-1)){
                    Intent nextScreen = new Intent(Workout.this, Workout.class);
                    nextScreen.putExtra("exercises", exercises);
                    nextScreen.putExtra("startTime", startTime);
                    nextScreen.putExtra("currentExercise", currentExercise+1);
                    startActivityForResult(nextScreen, 1);
                    finish();
                }else{
                    Intent nextScreen = new Intent(Workout.this, FinishedWorkout.class);
                    nextScreen.putExtra("startTime", startTime);
                    startActivityForResult(nextScreen, 1);
                    finish();
                }

            }
        });
    }
}
