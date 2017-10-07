package com.example.koder.pockettrainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by koder on 7/10/2017.
 */

public class FinishedWorkout extends AppCompatActivity {
    Button doneButton;
    TextView minutes;
    TextView seconds;
    long startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finished_workout);
        doneButton = (Button) findViewById(R.id.workoutDone);
        minutes = (TextView) findViewById(R.id.workoutMinutes);
        seconds = (TextView) findViewById(R.id.workoutSeconds);
        startTime = getIntent().getLongExtra("startTime", startTime);

        long totalTime = (System.currentTimeMillis()/1000) - startTime;
        if(totalTime/60 > 0){
            minutes.setText(Long.toString(totalTime/60) + " minutes");
        }else{
            minutes.setText("0");
        }

        if((totalTime - (totalTime/60)) != 0){
            seconds.setText(Long.toString(totalTime - (totalTime/60)*60) + " seconds");
        }else{
            seconds.setText("0");
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Toast.makeText(FinishedWorkout.this, Long.toString(totalTime), Toast.LENGTH_SHORT).show();
    }
}
