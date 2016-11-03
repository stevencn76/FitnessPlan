package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Week;

/**
 * Created by steven on 1/11/16.
 */

public class DayHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "days";
    public static final String KEY_ID = "da_id";
    public static final String KEY_WEEKID = "da_weekid";
    public static final String KEY_DATE = "da_date";
    public static final String KEY_DAYWORKOUTID = "da_dayworkoutid";
    public static final String KEY_PROGRESS = "da_progress";

    public DayHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEEKID + " INTEGER,"
                + KEY_DATE + " TEXT," + KEY_DAYWORKOUTID + " INTEGER,"
                + KEY_PROGRESS + " INTEGER"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createDay(Day d) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createDay(db, d);
        db.close();
    }

    private int nextId(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select max(" + KEY_ID + ") from " + TABLE_NAME, null);
        int newId = 1;
        if (cursor != null && cursor.moveToFirst()) {
            newId = cursor.getInt(0) + 1;
        }

        return newId;
    }

    public void createDay(SQLiteDatabase db, Day d) {
        d.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(KEY_WEEKID, d.getWeekId());
        values.put(KEY_DATE, d.getDate());
        values.put(KEY_DAYWORKOUTID, d.getDayWorkoutId());
        values.put(KEY_PROGRESS, d.getProgress());

        db.insert(TABLE_NAME, null, values);
    }

    public Day getDay(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_WEEKID, KEY_DATE, KEY_DAYWORKOUTID, KEY_PROGRESS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Day d = new Day();
        d.setId(cursor.getInt(0));
        d.setWeekId(cursor.getInt(1));
        d.setDate(cursor.getString(2));
        d.setDayWorkoutId(cursor.getInt(3));
        d.setProgress(cursor.getInt(4));

        db.close();

        return d;
    }

    public List<Day> getAllDays() {
        List<Day> dayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Day d = new Day();
                d.setId(cursor.getInt(0));
                d.setWeekId(cursor.getInt(1));
                d.setDate(cursor.getString(2));
                d.setDayWorkoutId(cursor.getInt(3));
                d.setProgress(cursor.getInt(4));
                // Adding to list
                dayList.add(d);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return dayList;
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
