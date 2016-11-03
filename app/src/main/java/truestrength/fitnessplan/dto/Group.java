package truestrength.fitnessplan.dto;

import java.util.ArrayList;

import truestrength.fitnessplan.entity.DayExercise;

/**
 * Created by steven on 31/10/16.
 */

public class Group {
    private String name;

    private ArrayList<DayExercise> exercises = new ArrayList<>();

    public Group() {

    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DayExercise> getExercises() {
        return exercises;
    }
}
