package truestrength.fitnessplan.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayWorkout;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.entity.Workout;

/**
 * Created by steven on 3/11/16.
 */

public class DataCache {
    private static DataCache instance;

    private Hashtable<Integer, Workout> workoutTable = new Hashtable<>();
    private Hashtable<Integer, Action> actionTable = new Hashtable<>();
    private Hashtable<Integer, Exercise> exerciseTable = new Hashtable<>();
    private Hashtable<Integer, DayWorkout> dayWorkoutTable = new Hashtable<>();
    private ArrayList<DayWorkout> dayWorkouts = new ArrayList<>();

    private DataCache() {

    }

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache();
        }

        return instance;
    }
}
