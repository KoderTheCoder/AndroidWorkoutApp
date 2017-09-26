package com.example.koder.pockettrainer;

import java.io.Serializable;

/**
 * Created by koder on 24/09/2017.
 */

public class Exercise implements Serializable {
    private String name;
    private int repitions;
    private int time;

    Exercise(String name, int repitions, int time){
        this.name = name;
        this.repitions = repitions;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getRepitions() {
        return repitions;
    }

    public int getTime() {
        return time;
    }
}
