package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Workout;

/**
 * Created by steven on 1/11/16.
 */

public class WorkoutHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "workouts";
    public static final String KEY_ID = "wo_id";
    public static final String KEY_NAME = "wo_name";

    public WorkoutHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createWorkout(Workout w) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, w.getId());
        values.put(KEY_NAME, w.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Workout getWorkout(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Workout c = new Workout();
        c.setId(Integer.parseInt(cursor.getString(0)));
        c.setName(cursor.getString(1));

        db.close();

        return c;
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workoutList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Workout c = new Workout();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));
                // Adding to list
                workoutList.add(c);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return workoutList;
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from " + TABLE_NAME;
        db.execSQL(sql);

        db.close();
    }
}
