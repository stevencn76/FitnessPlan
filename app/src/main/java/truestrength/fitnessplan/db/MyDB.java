package truestrength.fitnessplan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayWorkout;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.entity.Workout;
import truestrength.fitnessplan.util.Profile;
import truestrength.fitnessplan.util.WorkoutFileParser;

/**
 * Created by steven on 1/11/16.
 */

public class MyDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workouts.db";

    private Context context;

    private WorkoutHandler workoutHandler;
    private ActionHandler actionHandler;
    private ExerciseHandler exerciseHandler;
    private DayWorkoutHandler dayWorkoutHandler;

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;

        workoutHandler = new WorkoutHandler(this);
        actionHandler = new ActionHandler(this);
        exerciseHandler = new ExerciseHandler(this);
        dayWorkoutHandler = new DayWorkoutHandler(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        workoutHandler.onCreate(db);
        actionHandler.onCreate(db);
        exerciseHandler.onCreate(db);
        dayWorkoutHandler.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dayWorkoutHandler.onDrop(db);
        exerciseHandler.onDrop(db);
        actionHandler.onDrop(db);
        workoutHandler.onDrop(db);

        onCreate(db);
    }

    public WorkoutHandler getWorkoutHandler() {
        return workoutHandler;
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public DayWorkoutHandler getDayWorkoutHandler() {
        return dayWorkoutHandler;
    }

    private void clearData(SQLiteDatabase db) {
        dayWorkoutHandler.deleteAll(db);
        exerciseHandler.deleteAll(db);
        actionHandler.deleteAll(db);
        workoutHandler.deleteAll(db);
    }

    public void reloadWorkouts() {
        SQLiteDatabase db = null;
        WorkoutFileParser wfp = null;
        try {
            wfp = new WorkoutFileParser(context);

            if(wfp.getVersion().compareTo(Profile.getInstance(context).getDataVersion()) <= 0) {
                return;
            }

            db = this.getWritableDatabase();
            clearData(db);

            Object obj = null;
            while((obj = wfp.next()) != null) {
                if(obj instanceof Workout) {
                    workoutHandler.createWorkout(db, (Workout)obj);
                } else if(obj instanceof Action) {
                    actionHandler.createAction(db, (Action)obj);
                } else if(obj instanceof Exercise) {
                    exerciseHandler.createExercise(db, (Exercise)obj);
                } else if(obj instanceof DayWorkout) {
                    dayWorkoutHandler.createDayWorkout(db, (DayWorkout)obj);
                }
            }
        } catch (Exception e) {
            Log.d(MySettings.LOG_TAG, "reload workouts", e);
        } finally {
            if(wfp != null) {
                wfp.close();
            }
            if(db != null) {
                db.close();
            }
        }
    }
}
