package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.DayExercise;

/**
 * Created by steven on 1/11/16.
 */

public class DayExerciseHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "dayexercises";
    public static final String KEY_ID = "de_id";
    public static final String KEY_DAYID = "de_dayid";
    public static final String KEY_EXERCISEID = "de_exerciseid";
    public static final String KEY_DONE = "de_done";

    public DayExerciseHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DAYID + " INTEGER,"
                + KEY_EXERCISEID + " INTEGER," + KEY_DONE + " INTEGER"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createDayExercise(DayExercise de) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createDayExercise(db, de);
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

    public void createDayExercise(SQLiteDatabase db, DayExercise de) {
        de.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, de.getId());
        values.put(KEY_DAYID, de.getDayId());
        values.put(KEY_EXERCISEID, de.getExerciseId());
        values.put(KEY_DONE, de.isDone()?1:0);

        db.insert(TABLE_NAME, null, values);
    }

    public DayExercise getDayExercise(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_DAYID, KEY_EXERCISEID, KEY_DONE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DayExercise de = new DayExercise();
        de.setId(cursor.getInt(0));
        de.setDayId(cursor.getInt(1));
        de.setExerciseId(cursor.getInt(2));
        de.setDone(cursor.getInt(3) > 0);

        db.close();

        return de;
    }

    public List<DayExercise> getAllDayExercises() {
        List<DayExercise> dayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DayExercise de = new DayExercise();
                de.setId(cursor.getInt(0));
                de.setDayId(cursor.getInt(1));
                de.setExerciseId(cursor.getInt(2));
                de.setDone(cursor.getInt(3) > 0);
                // Adding to list
                dayList.add(de);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return dayList;
    }

    public List<DayExercise> getDayExercises(int dayId) {
        List<DayExercise> dayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME
                + " WHERE " + KEY_DAYID + "=" + dayId
                + " ORDER BY " + KEY_ID + " ASC";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DayExercise de = new DayExercise();
                de.setId(cursor.getInt(0));
                de.setDayId(cursor.getInt(1));
                de.setExerciseId(cursor.getInt(2));
                de.setDone(cursor.getInt(3) > 0);
                // Adding to list
                dayList.add(de);
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
