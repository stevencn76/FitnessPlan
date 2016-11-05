package truestrength.fitnessplan.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.db.MyDB;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.entity.DayWorkout;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.entity.Week;
import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class DataService {
    private static DataService instance;

    private Context context;
    private MyDB db;
    private Hashtable<Integer, List<Exercise>> workoutExerciseCacheTable;

    private DataService() {

    }

    public static DataService getInstance(Context context) {
        if(instance == null) {
            instance = new DataService();
            instance.context = context;
        }

        return instance;
    }

    private MyDB getDb() {
        if(db == null) {
            db = new MyDB(context);
        }

        return db;
    }

    public void initService() {
        getDb().reloadWorkouts();
    }

    private Hashtable<Integer, List<Exercise>> getWorkoutExerciseCacheTable() {
        if(workoutExerciseCacheTable == null) {
            workoutExerciseCacheTable = new Hashtable<>();

            List<Exercise> exerciseList = getDb().getExerciseHandler().getAllExercises();

            for(Exercise te : exerciseList) {
                List<Exercise> exlist = workoutExerciseCacheTable.get(te.getWorkoutId());
                if(exlist == null) {
                    exlist = new ArrayList<Exercise>();
                    workoutExerciseCacheTable.put(te.getWorkoutId(), exlist);
                }
                exlist.add(te);
            }
        }

        return workoutExerciseCacheTable;
    }

    public void createPlan(Date startDate) throws Exception {
        long t1 = System.currentTimeMillis();

        int weekCount = getDb().getDayWorkoutHandler().getWeekCount();

        if(weekCount <= 0)
            throw new Exception("Plan template does not exists");


        Plan plan = generateNewPlan(startDate, weekCount);

        generateNewWeeks(startDate, plan);

        long t2 = System.currentTimeMillis();
        Log.i(MySettings.LOG_TAG, "Creating a plan cost: " + (t2-t1));
    }

    private Plan generateNewPlan(Date startDate, int weekCount) throws Exception {
        //Create plan
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, weekCount * 7 - 1);

        Plan plan = new Plan();
        plan.setStartDate(DateUtil.toDateString(startDate));
        plan.setProgress(0);
        plan.setWeekCount(weekCount);
        plan.setEndDate(DateUtil.toDateString(c.getTime()));

        if(getDb().getPlanHandler().hasPlanOnDate(plan.getSqlStartDate(), plan.getSqlEndDate())) {
           throw new Exception("Intercrossing plan already exists");
        }

        getDb().getPlanHandler().createPlan(plan);

        return plan;
    }

    private void generateNewWeeks(Date startDate, Plan plan) {
        List<DayWorkout> dayWorkoutList = getDb().getDayWorkoutHandler().getAllDayWorkouts();

        Hashtable<Integer, Week> weekTable = new Hashtable<>();
        for(DayWorkout tdw : dayWorkoutList) {
            Week tweek = weekTable.get(tdw.getWeek());
            if(tweek == null) {
                tweek = new Week();
                tweek.setNumber(tdw.getWeek());
                tweek.setPlanId(plan.getId());
                tweek.setProgress(0);

                getDb().getWeekHandler().createWeek(tweek);
                weekTable.put(tweek.getNumber(), tweek);
            }

            Day tday = generateNewDay(startDate, tdw, tweek);
            generateNewDayExercises(tday);
        }

        weekTable.clear();
    }

    private Day generateNewDay(Date startDate, DayWorkout dw, Week week) {
        Day tday = new Day();
        tday.setDayWorkoutId(dw.getWorkoutId());
        tday.setProgress(0);
        tday.setWeekId(week.getId());
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, (dw.getWeek() - 1)*7 + dw.getDay() - 1);
        tday.setDate(DateUtil.toDateString(c.getTime()));

        getDb().getDayHandler().createDay(tday);

        return tday;
    }

    private void generateNewDayExercises(Day day) {
//        List<Exercise> exerciseList = getDb().getExerciseHandler().getExercises(day.getDayWorkoutId());
        List<Exercise> exerciseList = getWorkoutExerciseCacheTable().get(day.getDayWorkoutId());
        if(exerciseList == null)
            return;

        for(Exercise te : exerciseList) {
            DayExercise tde = new DayExercise();
            tde.setExerciseId(te.getId());
            tde.setDayId(day.getId());
            tde.setDone(false);

            getDb().getDayExerciseHandler().createDayExercise(tde);
        }
    }

    public void deletePlan(int planId) {
        getDb().getPlanHandler().deletePlan(planId);
    }

    public List<Plan> getAllPlans() {
        return getDb().getPlanHandler().getAllPlans();
    }

    public List<Week> getWeeks(int planId) {
        return getDb().getWeekHandler().getWeeks(planId);
    }

    public List<Day> getDays(int weekId) {
        return getDb().getDayHandler().getDays(weekId);
    }

    public List<DayExercise> getDayExercises(int dayId) {
        return getDb().getDayExerciseHandler().getDayExercises(dayId);
    }

    public Exercise getExercise(int exerciseId) {
        return getDb().getExerciseHandler().getExercise(exerciseId);
    }

    public Action getAction(int actionId) {
        return getDb().getActionHandler().getAction(actionId);
    }

    public void setDayExerciseDone(int dayExerciseId, boolean isDone) {
        DayExercise de = getDb().getDayExerciseHandler().getDayExercise(dayExerciseId);

        if(de.isDone() == isDone)
            return;

        de.setDone(isDone);
        getDb().getDayExerciseHandler().updateDayExercise(de);

        Day day = getDb().getDayHandler().getDay(de.getDayId());
        day.setProgress(getDb().getDayExerciseHandler().getProgressByDay(day.getId()));
        getDb().getDayHandler().updateDay(day);

        Week week = getDb().getWeekHandler().getWeek(day.getWeekId());
        week.setProgress(getDb().getDayHandler().getProgressByWeek(week.getId()));
        getDb().getWeekHandler().updateWeek(week);

        Plan plan = getDb().getPlanHandler().getPlan(week.getPlanId());
        plan.setProgress(getDb().getWeekHandler().getProgressByPlan(plan.getId()));
        getDb().getPlanHandler().updatePlan(plan);
    }

    public List<Day> getDaysByPlan(int planId) {
        return getDb().getDayHandler().getDaysByPlan(planId);
    }
}
