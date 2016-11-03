package truestrength.fitnessplan.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayWorkout;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.entity.Workout;

/**
 * Created by steven on 3/11/16.
 */

public class WorkoutFileParser {
    private static final int TARGET_WORKOUT = 1;
    private static final int TARGET_ACTION = 2;
    private static final int TARGET_EXERCISE = 3;
    private static final int TARGET_DAYWORKOUT = 4;
    private static final HashMap<String, Integer> TARGET_MAP = new HashMap<>();
    static {
        TARGET_MAP.put("Workouts", TARGET_WORKOUT);
        TARGET_MAP.put("Actions", TARGET_ACTION);
        TARGET_MAP.put("Exercises", TARGET_EXERCISE);
        TARGET_MAP.put("DayWorkouts", TARGET_DAYWORKOUT);
    }


    private String version = null;

    private Context context;

    private BufferedReader reader;
    private int curTarget = 0;
    private int curLineIndex = 0;
    private int nextExerciseId = 1;
    private int nextDayWorkoutId = 1;

    public WorkoutFileParser(Context context) throws Exception {
        this.context = context;

        open();
        readVersion();
    }

    private void open() throws Exception {
        InputStream is = context.getResources().openRawResource(R.raw.workouts);
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
    }

    private void readVersion() throws Exception {
        String line = reader.readLine();
        curLineIndex ++;

        if(line == null)
            throw new Exception("Error workout file format, not found Version information");

        line = line.trim();
        if(!line.startsWith("Version"))
            throw new Exception("Error workout file format, not begin with Version information");

        line = line.substring("Version".length() + 1).trim();
        if(line.length() == 0)
            throw new Exception("Error workout file format, empty Version information");

        version = line;
    }

    public Object next() throws Exception {
        String line = nextLine();

        if(line == null)
            return null;

        Object res = null;
        switch (curTarget) {
            case TARGET_WORKOUT:
                res = parseWorkout(line);
                break;
            case TARGET_ACTION:
                res = parseAction(line);
                break;
            case TARGET_EXERCISE:
                res = parseExercise(line);
                break;
            case TARGET_DAYWORKOUT:
                res = parseDayWorkout(line);
                break;
            default:
                throw new Exception("Workout file format error");
        }
        return res;
    }

    private String nextLine() throws Exception {
        String line = reader.readLine();
        curLineIndex++;
        if(line == null)
            return null;

        line = line.trim();
        if(line == null || line.length() == 0)
            return nextLine();

        if(line.startsWith("//")) {
            return nextLine();
        }

        if(line.startsWith("--")) {
            String target = line.substring(2).trim();
            Integer it = TARGET_MAP.get(target);
            if(it == null)
                throw  new Exception("Workout file error, unknown target: '" + target + "'");

            curTarget = it;
            return nextLine();
        }

        return line;
    }

    private Workout parseWorkout(String line) throws Exception {
        try {
            String[] ss = line.split("\\|");

            Workout w = new Workout();
            w.setId(Integer.parseInt(ss[0].trim()));
            w.setName(ss[1].trim());

            return w;
        } catch (Exception e) {
            throw new Exception("parse line:" + curLineIndex + " error for Workout");
        }
    }

    private Action parseAction(String line) throws Exception {
        try {
            String[] ss = line.split("\\|");

            Action a = new Action();
            a.setId(Integer.parseInt(ss[0].trim()));
            a.setName(ss[1].trim());
            a.setPicture(ss[2].trim());
            if(ss.length > 3) {
                a.setDescription(ss[3].trim());
            }

            return a;
        } catch (Exception e) {
            throw new Exception("parse line:" + curLineIndex + " error for Action");
        }
    }

    private Exercise parseExercise(String line) throws Exception {
        try {
            Exercise ex = new Exercise();
            ex.setId(nextExerciseId++);

            String[] ss = line.split("\\|");

            ex.setWorkoutId(Integer.parseInt(ss[0].trim()));
            ex.setIndex(ss[1].trim());
            ex.setGroupName(ss[2].trim());
            ex.setActionId(Integer.parseInt(ss[3].trim()));
            ex.setSets(ss[4].trim());
            ex.setReps(ss[5].trim());
            ex.setLoad(ss[6].trim());
            ex.setTempo(ss[7].trim());
            ex.setRest(ss[8].trim());
            if(ss.length > 9) {
                ex.setComments(ss[9].trim());
            }

            return ex;
        } catch (Exception e) {
            nextExerciseId--;
            throw new Exception("parse line:" + curLineIndex + " error for Exercise");
        }
    }

    private DayWorkout parseDayWorkout(String line) throws Exception {
        try {
            DayWorkout dw = new DayWorkout();
            dw.setId(nextDayWorkoutId++);

            String[] ss = line.split("\\|");

            dw.setWeek(Integer.parseInt(ss[0].trim()));
            dw.setDay(Integer.parseInt(ss[1].trim()));
            dw.setWorkoutId(Integer.parseInt(ss[2].trim()));

            return dw;
        } catch (Exception e) {
            nextDayWorkoutId--;
            throw new Exception("parse line:" + curLineIndex + " error for DayWorkout");
        }
    }

    public void close() {
        try {
            if(reader != null)
                reader.close();
        } catch (Exception e) {
            Log.d(MySettings.LOG_TAG, "close workout file", e);
        }
    }

    public String getVersion() {
        return version;
    }
}
