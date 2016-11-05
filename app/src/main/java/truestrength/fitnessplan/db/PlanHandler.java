package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.util.DateUtil;

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

        cursor.close();

        return newId;
    }

    public void createPlan(SQLiteDatabase db, Plan p) {
        p.setId(nextId(db));

        ContentValues values = new ContentValues();
        values.put(KEY_ID, p.getId());
        values.put(KEY_STARTDATE, p.getSqlStartDate());
        values.put(KEY_ENDDATE, p.getSqlEndDate());
        values.put(KEY_WEEKCOUNT, p.getWeekCount());
        values.put(KEY_PROGRESS, p.getProgress());

        db.insert(TABLE_NAME, null, values);
    }

    public boolean hasPlanOnDate(String sqlStartDate, String sqlEndDate) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE ("
                + KEY_STARTDATE + "<'" + sqlEndDate + "' OR " + KEY_STARTDATE + "='" + sqlEndDate + "') AND ("
                + KEY_ENDDATE + ">'" + sqlStartDate + "' OR " + KEY_ENDDATE + "='" + sqlStartDate + "')";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        boolean res = false;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            res = true;
        }

        cursor.close();

        db.close();

        return res;
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
        p.setSqlStartDate(cursor.getString(1));
        p.setSqlEndDate(cursor.getString(2));
        p.setWeekCount(cursor.getInt(3));
        p.setProgress(cursor.getInt(4));

        cursor.close();

        db.close();

        return p;
    }

    public List<Plan> getAllPlans() {
        List<Plan> planList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_STARTDATE + " ASC";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Plan p = new Plan();
                p.setId(cursor.getInt(0));
                p.setSqlStartDate(cursor.getString(1));
                p.setSqlEndDate(cursor.getString(2));
                p.setWeekCount(cursor.getInt(3));
                p.setProgress(cursor.getInt(4));
                // Adding to list
                planList.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        // return list
        return planList;
    }

    public void deletePlan(int planId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        //delete dayexercise
        String sql = "delete from " + DayExerciseHandler.TABLE_NAME
                + " WHERE " + DayExerciseHandler.KEY_ID + " IN (SELECT de." + DayExerciseHandler.KEY_ID
                + " FROM " + PlanHandler.TABLE_NAME + " pl," + WeekHandler.TABLE_NAME + " we,"
                + DayHandler.TABLE_NAME + " da," + DayExerciseHandler.TABLE_NAME + " de WHERE "
                + "pl." + PlanHandler.KEY_ID + "=we." + WeekHandler.KEY_PLANID
                + " AND we." + WeekHandler.KEY_ID + "=da." + DayHandler.KEY_WEEKID
                + " AND da." + DayHandler.KEY_ID + "=de." + DayExerciseHandler.KEY_DAYID
                + " AND pl." + PlanHandler.KEY_ID + "=" + planId + ")";
        db.execSQL(sql);

        //delete day
        sql = "delete from " + DayHandler.TABLE_NAME
                + " WHERE " + DayHandler.KEY_ID + " IN (SELECT da." + DayHandler.KEY_ID
                + " FROM " + PlanHandler.TABLE_NAME + " pl," + WeekHandler.TABLE_NAME + " we,"
                + DayHandler.TABLE_NAME + " da WHERE "
                + "pl." + PlanHandler.KEY_ID + "=we." + WeekHandler.KEY_PLANID
                + " AND we." + WeekHandler.KEY_ID + "=da." + DayHandler.KEY_WEEKID
                + " AND pl." + PlanHandler.KEY_ID + "=" + planId + ")";
        db.execSQL(sql);

        //delete week
        sql = "delete from " + WeekHandler.TABLE_NAME
                + " WHERE " + WeekHandler.KEY_ID + " IN (SELECT we." + WeekHandler.KEY_ID
                + " FROM " + PlanHandler.TABLE_NAME + " pl," + WeekHandler.TABLE_NAME + " we WHERE "
                + "pl." + PlanHandler.KEY_ID + "=we." + WeekHandler.KEY_PLANID
                + " AND pl." + PlanHandler.KEY_ID + "=" + planId + ")";
        db.execSQL(sql);

        //delete plan
        sql = "delete from " + TABLE_NAME + " WHERE " + KEY_ID + "=" + planId;
        db.execSQL(sql);

        db.close();
    }

    public void updatePlan(Plan plan) {
        SQLiteDatabase db = helper.getWritableDatabase();

        updatePlan(db, plan);

        db.close();
    }

    public void updatePlan(SQLiteDatabase db, Plan p) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, p.getId());
        values.put(KEY_STARTDATE, p.getSqlStartDate());
        values.put(KEY_ENDDATE, p.getSqlEndDate());
        values.put(KEY_WEEKCOUNT, p.getWeekCount());
        values.put(KEY_PROGRESS, p.getProgress());

        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(p.getId())});
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
