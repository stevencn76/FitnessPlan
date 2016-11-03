package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.Plan;

/**
 * Created by steven on 1/11/16.
 */

public class PlanHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "plans";
    public static final String KEY_ID = "pl_id";
    public static final String KEY_STARTDATE = "pl_startdate";
    public static final String KEY_ENDDATE = "pl_enddate";
    public static final String KEY_WEEKCOUNT = "pl_weekcount";
    public static final String KEY_PROGRESS = "pl_progress";

    public PlanHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STARTDATE + " TEXT,"
                + KEY_ENDDATE + " TEXT," + KEY_WEEKCOUNT + " INTEGER,"
                + KEY_PROGRESS + " INTEGER"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createPlan(Plan p) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createPlan(db, p);
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

    public void createPlan(SQLiteDatabase db, Plan p) {
        p.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, p.getId());
        values.put(KEY_STARTDATE, p.getStartDate());
        values.put(KEY_ENDDATE, p.getEndDate());
        values.put(KEY_WEEKCOUNT, p.getWeekCount());
        values.put(KEY_PROGRESS, p.getProgress());

        db.insert(TABLE_NAME, null, values);
    }

    public Plan getPlan(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_STARTDATE, KEY_ENDDATE, KEY_WEEKCOUNT, KEY_PROGRESS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Plan p = new Plan();
        p.setId(cursor.getInt(0));
        p.setStartDate(cursor.getString(1));
        p.setEndDate(cursor.getString(2));
        p.setWeekCount(cursor.getInt(3));
        p.setProgress(cursor.getInt(4));

        db.close();

        return p;
    }

    public List<Plan> getAllPlans() {
        List<Plan> planList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Plan p = new Plan();
                p.setId(cursor.getInt(0));
                p.setStartDate(cursor.getString(1));
                p.setEndDate(cursor.getString(2));
                p.setWeekCount(cursor.getInt(3));
                p.setProgress(cursor.getInt(4));
                // Adding to list
                planList.add(p);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return planList;
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