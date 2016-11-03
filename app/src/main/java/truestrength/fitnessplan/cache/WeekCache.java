package truestrength.fitnessplan.cache;

import java.util.ArrayList;

import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Week;

/**
 * Created by steven on 3/11/16.
 */

public class WeekCache {
    private Week week;
    private ArrayList<Day> dayList = new ArrayList<>();

    public WeekCache() {

    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public ArrayList<Day> getDayList() {
        return dayList;
    }
}
