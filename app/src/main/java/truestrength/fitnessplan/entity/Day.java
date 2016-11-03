package truestrength.fitnessplan.entity;

import java.util.Date;

/**
 * Created by steven on 31/10/16.
 */

public class Day {
    private int id;
    private int weekId;
    private Date date;
    private int dayWorkoutId;
    private int progress;

    public Day() {

    }

    public Day(int id, int weekId, Date date, int dayWorkoutId, int progress) {
        this.id = id;
        this.weekId = weekId;
        this.date = date;
        this.dayWorkoutId = dayWorkoutId;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDayWorkoutId() {
        return dayWorkoutId;
    }

    public void setDayWorkoutId(int dayWorkoutId) {
        this.dayWorkoutId = dayWorkoutId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
