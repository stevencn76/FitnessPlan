package truestrength.fitnessplan.entity;

import java.io.Serializable;

/**
 * Created by steven on 31/10/16.
 */

public class DayExercise implements Serializable {
    private int id;
    private int dayId;
    private int exerciseId;
    private boolean done;

    public DayExercise() {

    }

    public DayExercise(int id, int dayId, int exerciseId, boolean done) {
        this.id = id;
        this.dayId = dayId;
        this.exerciseId = exerciseId;
        this.done = done;
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

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
