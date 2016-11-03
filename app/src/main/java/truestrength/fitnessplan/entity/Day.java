package truestrength.fitnessplan.entity;

import java.io.Serializable;
import java.util.Date;

import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class Day implements Serializable {
    private int id;
    private int weekId;
    private String date;
    private int dayWorkoutId;
    private int progress;
    private String sqlDate;

    public Day() {

    }

    public Day(int id, int weekId, String date, int dayWorkoutId, int progress) {
        this.id = id;
        this.weekId = weekId;
        setDate(date);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        Date td = DateUtil.fromDateString(date);
        sqlDate = DateUtil.toSqlDateString(td);
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

    public void setSqlDate(String sqlDate) {
        this.sqlDate = sqlDate;
        Date td = DateUtil.fromSqlDateString(sqlDate);
        date = DateUtil.toDateString(td);
    }

    public String getSqlDate() {
        return sqlDate;
    }
}
