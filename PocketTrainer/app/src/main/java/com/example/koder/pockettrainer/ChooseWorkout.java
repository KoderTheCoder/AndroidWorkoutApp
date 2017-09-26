package com.example.koder.pockettrainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by koder on 19/09/2017.
 */

public class ChooseWorkout extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_exercise);
        Toast.makeText(this, getIntent().getStringExtra("WORKOUT NAME"), Toast.LENGTH_LONG).show();
    }
}
