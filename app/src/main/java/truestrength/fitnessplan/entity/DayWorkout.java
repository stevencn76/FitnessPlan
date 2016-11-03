package truestrength.fitnessplan.entity;

import java.io.Serializable;

/**
 * Created by steven on 1/11/16.
 */

public class DayWorkout implements Serializable {
    private int id;
    private int week;
    private int day;
    private int workoutId;

    public DayWorkout() {

    }

    public DayWorkout(int id, int week, int day, int workoutId) {
        this.id = id;
        this.week = week;
        this.day = day;
        this.workoutId = workoutId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }
}
