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
 * Created by Baofeng Chen on 1/11/16.
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
        cursor.close();

        return newId;
    }

    public void createDay(SQLiteDatabase db, Day d) {
        d.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(KEY_WEEKID, d.getWeekId());
        values.put(KEY_DATE, d.getSqlDate());
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
        d.setSqlDate(cursor.getString(2));
        d.setDayWorkoutId(cursor.getInt(3));
        d.setProgress(cursor.getInt(4));

        cursor.close();

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
                d.setSqlDate(cursor.getString(2));
                d.setDayWorkoutId(cursor.getInt(3));
                d.setProgress(cursor.getInt(4));
                // Adding to list
                dayList.add(d);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        // return list
        return dayList;
    }

    public List<Day> getDays(int weekId) {
        List<Day> dayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME
                + " WHERE " + KEY_WEEKID + "=" + weekId;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Day d = new Day();
                d.setId(cursor.getInt(0));
                d.setWeekId(cursor.getInt(1));
                d.setSqlDate(cursor.getString(2));
                d.setDayWorkoutId(cursor.getInt(3));
                d.setProgress(cursor.getInt(4));
                // Adding to list
                dayList.add(d);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        // return list
        return dayList;
    }

    public List<Day> getDaysByPlan(int planId) {
        List<Day> dayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT td." + KEY_ID + ", td." + KEY_WEEKID + ", td." + KEY_DATE
                + ", td." + KEY_DAYWORKOUTID + ", td." + KEY_PROGRESS
                + " FROM " + TABLE_NAME + " td," + WeekHandler.TABLE_NAME
                + " tw WHERE td." + KEY_WEEKID + "=tw." + WeekHandler.KEY_ID
                + " AND tw." + WeekHandler.KEY_PLANID + "=" + planId
                + " ORDER BY " + KEY_ID + " ASC";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Day d = new Day();
                d.setId(cursor.getInt(0));
                d.setWeekId(cursor.getInt(1));
                d.setSqlDate(cursor.getString(2));
                d.setDayWorkoutId(cursor.getInt(3));
                d.setProgress(cursor.getInt(4));
                // Adding to list
                dayList.add(d);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        // return list
        return dayList;
    }

    public void updateDay(Day day) {
        SQLiteDatabase db = helper.getWritableDatabase();
        updateDay(db, day);
        db.close();
    }

    public void updateDay(SQLiteDatabase db, Day day) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, day.getId());
        values.put(KEY_WEEKID, day.getWeekId());
        values.put(KEY_DATE, day.getSqlDate());
        values.put(KEY_DAYWORKOUTID, day.getDayWorkoutId());
        values.put(KEY_PROGRESS, day.getProgress());

        db.update(TABLE_NAME, values, KEY_ID+"=?", new String[]{String.valueOf(day.getId())});
    }

    public int getProgressByWeek(int weekId) {
        String selectQuery = "SELECT  sum(" + KEY_PROGRESS + ")*1.0/(count(" + KEY_PROGRESS + ")*100) FROM "
                + TABLE_NAME + " WHERE " + KEY_WEEKID + "=" + weekId
                + " AND " + KEY_DAYWORKOUTID + "<>0";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int res = 0;
        if (cursor.moveToFirst()) {
            res = (int)Math.ceil(cursor.getDouble(0) * 100);
        }

        cursor.close();

        db.close();

        return res;
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
