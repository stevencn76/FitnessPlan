package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.entity.Week;

/**
 * Created by steven on 1/11/16.
 */

public class WeekHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "weeks";
    public static final String KEY_ID = "we_id";
    public static final String KEY_PLANID = "we_planid";
    public static final String KEY_NUMBER = "we_number";
    public static final String KEY_PROGRESS = "pl_progress";

    public WeekHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLANID + " INTEGER,"
                + KEY_NUMBER + " INTEGER," + KEY_PROGRESS + " INTEGER"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createWeek(Week w) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createWeek(db, w);
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

    public void createWeek(SQLiteDatabase db, Week w) {
        w.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, w.getId());
        values.put(KEY_PLANID, w.getPlanId());
        values.put(KEY_NUMBER, w.getNumber());
        values.put(KEY_PROGRESS, w.getProgress());

        db.insert(TABLE_NAME, null, values);
    }

    public Week getWeek(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_PLANID, KEY_NUMBER, KEY_PROGRESS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Week w = new Week();
        w.setId(cursor.getInt(0));
        w.setPlanId(cursor.getInt(1));
        w.setNumber(cursor.getInt(2));
        w.setProgress(cursor.getInt(3));

        db.close();

        return w;
    }

    public List<Week> getAllWeeks() {
        List<Week> weekList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Week w = new Week();
                w.setId(cursor.getInt(0));
                w.setPlanId(cursor.getInt(1));
                w.setNumber(cursor.getInt(2));
                w.setProgress(cursor.getInt(3));
                // Adding to list
                weekList.add(w);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return weekList;
    }

    public List<Week> getWeeks(int planId) {
        List<Week> weekList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                + KEY_PLANID + "=" + planId;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Week w = new Week();
                w.setId(cursor.getInt(0));
                w.setPlanId(cursor.getInt(1));
                w.setNumber(cursor.getInt(2));
                w.setProgress(cursor.getInt(3));
                // Adding to list
                weekList.add(w);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return weekList;
    }

    public void updateWeek(Week w) {
        SQLiteDatabase db = helper.getWritableDatabase();

        updateWeek(db, w);

        db.close();
    }

    public void updateWeek(SQLiteDatabase db, Week w) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, w.getId());
        values.put(KEY_PLANID, w.getPlanId());
        values.put(KEY_NUMBER, w.getNumber());
        values.put(KEY_PROGRESS, w.getProgress());

        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(w.getId())});
    }

    public int getProgressByPlan(int planId) {
        String selectQuery = "SELECT  sum(" + KEY_PROGRESS + ")*1.0/(count(" + KEY_PROGRESS + ")*100) FROM "
                + TABLE_NAME + " WHERE " + KEY_PLANID + "=" + planId;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int res = 0;
        if (cursor.moveToFirst()) {
            res = (int)Math.ceil(cursor.getDouble(0) * 100);
        }

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
