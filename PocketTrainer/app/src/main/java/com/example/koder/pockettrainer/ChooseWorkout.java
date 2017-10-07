package com.example.koder.pockettrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by koder on 19/09/2017.
 */

public class ChooseWorkout extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_exercise);

        MyAdapter adapter;
        ListView list;
        final Database db = new Database(this);
        ArrayList<String> workouts = db.getWorkouts();

        //build adapter, give it items
        adapter = new MyAdapter(this, workouts);

        //get list from layout and give it the adapter
        list = (ListView)findViewById(R.id.workoutList);
        list.setAdapter(adapter);
        //let layout know it should re-render the list
        adapter.notifyDataSetChanged();

        //click and hold, deletes item from list
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get selected item
                String selectedItem = (String)adapterView.getItemAtPosition(i);
                //get adapter this list is using (cast it to appropriate type)
                MyAdapter itemAdapter = (MyAdapter) adapterView.getAdapter();
                //remove item from adapters item list
                if(db.deleteWorkout(selectedItem)) {
                    Toast.makeText(ChooseWorkout.this, selectedItem + " deleted", Toast.LENGTH_SHORT).show();
                    itemAdapter.remove(selectedItem);
                }else{
                    Toast.makeText(ChooseWorkout.this,"error deleting " + selectedItem, Toast.LENGTH_SHORT).show();
                }
                //make adapter notify layout needs re-rendering
                itemAdapter.notifyDataSetChanged();

                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String)adapterView.getItemAtPosition(i);
                //Toast.makeText(ChooseWorkout.this, selectedItem, Toast.LENGTH_SHORT).show();
                Exercises exercises = db.getExercises(selectedItem);

                Intent nextScreen = new Intent(ChooseWorkout.this, Workout.class);
                nextScreen.putExtra("exercises", exercises);
                nextScreen.putExtra("startTime", System.currentTimeMillis()/1000);
                nextScreen.putExtra("currentExercise", 0);
                startActivityForResult(nextScreen, 1);
                finish();

            }
        });
    }
}
