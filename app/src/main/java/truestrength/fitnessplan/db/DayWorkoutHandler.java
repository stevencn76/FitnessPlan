package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayWorkout;

/**
 * Created by steven on 1/11/16.
 */

public class DayWorkoutHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "dayworkouts";
    public static final String KEY_ID = "dw_id";
    public static final String KEY_WEEK = "dw_week";
    public static final String KEY_DAY = "dw_day";
    public static final String KEY_WORKOUTID = "dw_workoutid";

    public DayWorkoutHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEEK + " INTEGER,"
                + KEY_DAY + " INTEGER," + KEY_WORKOUTID + " INTEGER"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createDayWorkout(DayWorkout dw) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createDayWorkout(db, dw);
        db.close();
    }

    public void createDayWorkout(SQLiteDatabase db, DayWorkout dw) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dw.getId());
        values.put(KEY_WEEK, dw.getWeek());
        values.put(KEY_DAY, dw.getDay());
        values.put(KEY_WORKOUTID, dw.getWorkoutId());

        db.insert(TABLE_NAME, null, values);
    }

    public DayWorkout getDayWorkout(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_WEEK, KEY_DAY, KEY_WORKOUTID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DayWorkout dw = new DayWorkout();
        dw.setId(cursor.getInt(0));
        dw.setWeek(cursor.getInt(1));
        dw.setDay(cursor.getInt(2));
        dw.setWorkoutId(cursor.getInt(3));

        db.close();

        return dw;
    }

    public List<DayWorkout> getAllDayWorkouts() {
        List<DayWorkout> dayWorkoutList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DayWorkout dw = new DayWorkout();
                dw.setId(cursor.getInt(0));
                dw.setWeek(cursor.getInt(1));
                dw.setDay(cursor.getInt(2));
                dw.setWorkoutId(cursor.getInt(3));
                // Adding to list
                dayWorkoutList.add(dw);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return dayWorkoutList;
    }

    public int getWeekCount() {
        int count = 0;
        String selectQuery = "SELECT  count(distinct " + KEY_WEEK + ") FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        db.close();

        return count;
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
