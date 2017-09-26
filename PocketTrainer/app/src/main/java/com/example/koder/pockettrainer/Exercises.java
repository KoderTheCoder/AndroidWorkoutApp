package com.example.koder.pockettrainer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by koder on 24/09/2017.
 */

public class Exercises implements Serializable{
    ArrayList<Exercise> exercisesArray = new ArrayList<>();

    public void addExercise(String name, int repititions, int time){
        exercisesArray.add(new Exercise(name, repititions, time));
    }

}
