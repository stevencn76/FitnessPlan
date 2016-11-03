package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.entity.Workout;

/**
 * Created by steven on 1/11/16.
 */

public class ExerciseHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "exercises";
    public static final String KEY_ID = "ex_id";
    public static final String KEY_WORKOUTID = "ex_workoutid";
    public static final String KEY_INDEX = "ex_index";
    public static final String KEY_GROUPNAME = "ex_groupname";
    public static final String KEY_ACTIONID = "ex_actionid";
    public static final String KEY_SETS = "ex_sets";
    public static final String KEY_REPS = "ex_reps";
    public static final String KEY_LOAD = "ex_load";
    public static final String KEY_TEMPO = "ex_tempo";
    public static final String KEY_REST = "ex_rest";
    public static final String KEY_COMMENTS = "ex_comments";

    public ExerciseHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORKOUTID + " INTEGER,"
                + KEY_INDEX + " TEXT," + KEY_GROUPNAME + " TEXT,"
                + KEY_ACTIONID + " INTEGER," + KEY_SETS + " TEXT,"
                + KEY_REPS + " TEXT," + KEY_LOAD + " TEXT,"
                + KEY_TEMPO + " TEXT," + KEY_REST + " TEXT,"
                + KEY_COMMENTS + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createExercise(Exercise e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createExercise(db, e);
        db.close();
    }

    public void createExercise(SQLiteDatabase db, Exercise e) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, e.getId());
        values.put(KEY_WORKOUTID, e.getWorkoutId());
        values.put(KEY_INDEX, e.getIndex());
        values.put(KEY_GROUPNAME, e.getGroupName());
        values.put(KEY_ACTIONID, e.getActionId());
        values.put(KEY_SETS, e.getSets());
        values.put(KEY_REPS, e.getReps());
        values.put(KEY_LOAD, e.getLoad());
        values.put(KEY_TEMPO, e.getTempo());
        values.put(KEY_REST, e.getRest());
        values.put(KEY_COMMENTS, e.getComments());

        db.insert(TABLE_NAME, null, values);
    }

    public Exercise getExercise(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_WORKOUTID, KEY_INDEX, KEY_GROUPNAME, KEY_ACTIONID,
                        KEY_SETS, KEY_REPS, KEY_LOAD, KEY_TEMPO, KEY_REST,
                        KEY_COMMENTS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Exercise e = new Exercise();
        e.setId(cursor.getInt(0));
        e.setWorkoutId(cursor.getInt(1));
        e.setIndex(cursor.getString(2));
        e.setGroupName(cursor.getString(3));
        e.setActionId(cursor.getInt(4));
        e.setSets(cursor.getString(5));
        e.setReps(cursor.getString(6));
        e.setLoad(cursor.getString(7));
        e.setTempo(cursor.getString(8));
        e.setRest(cursor.getString(9));
        e.setComments(cursor.getString(10));

        db.close();

        return e;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "
                + KEY_WORKOUTID + " ASC, " + KEY_INDEX + " ASC";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercise e = new Exercise();
                e.setId(cursor.getInt(0));
                e.setWorkoutId(cursor.getInt(1));
                e.setIndex(cursor.getString(2));
                e.setGroupName(cursor.getString(3));
                e.setActionId(cursor.getInt(4));
                e.setSets(cursor.getString(5));
                e.setReps(cursor.getString(6));
                e.setLoad(cursor.getString(7));
                e.setTempo(cursor.getString(8));
                e.setRest(cursor.getString(9));
                e.setComments(cursor.getString(10));
                // Adding to list
                exerciseList.add(e);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return exerciseList;
    }

    public List<Exercise> getExercises(int workoutId) {
        List<Exercise> exerciseList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME
                + " WHERE " + KEY_WORKOUTID + "=" + workoutId
                + " ORDER BY "
                + KEY_INDEX + " ASC";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercise e = new Exercise();
                e.setId(cursor.getInt(0));
                e.setWorkoutId(cursor.getInt(1));
                e.setIndex(cursor.getString(2));
                e.setGroupName(cursor.getString(3));
                e.setActionId(cursor.getInt(4));
                e.setSets(cursor.getString(5));
                e.setReps(cursor.getString(6));
                e.setLoad(cursor.getString(7));
                e.setTempo(cursor.getString(8));
                e.setRest(cursor.getString(9));
                e.setComments(cursor.getString(10));
                // Adding to list
                exerciseList.add(e);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return exerciseList;
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();

        deleteAll(db);

        db.close();
    }

    public void deleteAll(SQLiteDatabase db) {
        String sql = "delete from " + TABLE_NAME;
        db.execSQL(sql);
    }
}
