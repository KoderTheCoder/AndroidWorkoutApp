package com.example.koder.pockettrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by koder on 26/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "PocketTrainer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS workouts"+
                "(id integer primary key AUTOINCREMENT, name VARCHAR UNIQUE, exercisesCount integer);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS exercises"+
                "(id integer primary key AUTOINCREMENT, name VARCHAR, repititions integer, time integer, workout_id integer,"+
                " FOREIGN KEY (workout_id) REFERENCES workouts(id))");
        Log.d("Database", "Tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addWorkout(String workoutName, Exercises exercises){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", workoutName);
        contentValues.put("exercisesCount", exercises.exercisesArray.size());
        db.insert("workouts", null, contentValues);
        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+ workoutName + "'", null);
        workout_id.moveToNext();

        for (Exercise exercise:exercises.exercisesArray) {
            if(exercise.getRepitions()==0){
                contentValues.put("name", exercise.getName());
                contentValues.put("time", exercise.getTime());
                contentValues.put("workout_id", workout_id.getInt(workout_id.getColumnIndex("id")));
                db.insert("exercises", null, contentValues);
                contentValues.clear();
            }else{
                contentValues.put("name", exercise.getName());
                contentValues.put("repititions", exercise.getRepitions());
                contentValues.put("workout_id", workout_id.getInt(workout_id.getColumnIndex("id")));
                db.insert("exercises", null, contentValues);
                contentValues.clear();
            }
        }
        return true;
    }
    public ArrayList<String> getWorkouts(){
        ArrayList<String> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor workout = db.rawQuery("SELECT * FROM workouts", null);
        while(workout.moveToNext()) {
            workouts.add(workout.getString(workout.getColumnIndex("name")));
        }
        return workouts;
    }
    public Exercises getExercises(String workoutName){
        Exercises exercises = new Exercises();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+workoutName+"'", null);
        workout_id.moveToNext();
        int id = workout_id.getColumnIndex("id");
        Cursor exercise = db.rawQuery("SELECT * FROM exercises WHERE workout_id = "+workout_id.getInt(id), null);
        while(exercise.moveToNext()) {
            exercises.addExercise(exercise.getString(exercise.getColumnIndex("name")), exercise.getInt(exercise.getColumnIndex("repititions")), exercise.getInt(exercise.getColumnIndex("time")));
        }
        return exercises;
    }

    public boolean deleteWorkout(String name){
        ArrayList<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor workout_id = db.rawQuery("SELECT id FROM workouts WHERE name = '"+name+"'", null);
        workout_id.moveToNext();
        int id = workout_id.getColumnIndex("id");
        try{
            Cursor cursor1 = db.rawQuery("DELETE FROM workouts WHERE name = '"+name+"'", null);
            Cursor cursor2 = db.rawQuery("DELETE FROM exercises WHERE workout_id = "+workout_id.getInt(id), null);
            cursor2.moveToNext();
            cursor1.moveToNext();
        }catch (SQLException e){
            return false;
        }
        return true;
    }

    }
/*
try{
        workoutDB = openOrCreateDatabase("workouts.db", Context.MODE_PRIVATE, null);
        workoutDB.execSQL("CREATE TABLE IF NOT EXISTS workouts"+
        "(id integer primary key AUTOINCREMENT, name VARCHAR UNIQUE, exercisesCount integer);");
        workoutDB.execSQL("CREATE TABLE IF NOT EXISTS exercises"+
        "(id integer primary key AUTOINCREMENT, name VARCHAR, repititions integer, time integer, workout_id integer,"+
        " FOREIGN KEY (workout_id) REFERENCES workouts(id);");
        File database = getApplicationContext().getDatabasePath("MyContacts.db");

        //check if exists
        if(database.exists())
        {
        Toast.makeText(this, "DATABASE READY!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
        Toast.makeText(this, "DATABASE NOT CREATED OR FOUND!", Toast.LENGTH_SHORT).show();
        }
        }
        catch(Exception e)
        {
        Toast.makeText(this, "Error creating DB: "+e, Toast.LENGTH_LONG).show();
        }
public void addWorkout(View view, String workoutName, Exercises exercises){
        workoutDB.execSQL("INSERT INTO workouts (name, exercisesCount) VALUES ('"+workoutName+"',"+exercises.exercisesArray.size()+")");
        Cursor c = workoutDB.rawQuery("SELECT id FROM workouts WHERE name = '"+ workoutName + "'", null);
        c.moveToNext();

        for (Exercise exercise:exercises.exercisesArray) {
        if(exercise.getRepitions()==0){
        workoutDB.execSQL("INSERT INTO exercises (name, time, workout_id) VALUES ('"+exercise.getName()+"', "+exercise.getTime()+", "+c.getColumnIndex("id")+")");
        }
        }
        }
*/