package com.example.koder.pockettrainer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button chooseWorkout;
    Button createWorkout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        chooseWorkout = (Button)findViewById(R.id.chooseWorkout);
        createWorkout = (Button)findViewById(R.id.createWorkout);


        chooseWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(MainActivity.this, ChooseWorkout.class);
                startActivityForResult(nextScreen, 1);
            }
        });
        createWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(MainActivity.this, CreateWorkout.class);
                startActivityForResult(nextScreen, 1);
            }
        });
    }
}