package truestrength.fitnessplan.entity;

import java.util.ArrayList;

/**
 * Created by steven on 31/10/16.
 */

public class Group {
    private int id;
    private int dayId;
    private String name;
    private int seq;

    private ArrayList<Exercise> exercises = new ArrayList<>();

    public Group() {

    }

    public Group(int id, int dayId, String name, int seq) {
        this.id = id;
        this.dayId = dayId;
        this.name = name;
        this.seq = seq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
}
