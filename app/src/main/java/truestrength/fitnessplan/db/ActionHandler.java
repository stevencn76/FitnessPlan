package truestrength.fitnessplan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.Workout;

/**
 * Created by steven on 1/11/16.
 */

public class ActionHandler {
    private SQLiteOpenHelper helper;

    public static final String TABLE_NAME = "actions";
    public static final String KEY_ID = "act_id";
    public static final String KEY_NAME = "act_name";
    public static final String KEY_PICTURE = "act_picture";
    public static final String KEY_DESC = "act_desc";

    public ActionHandler(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PICTURE + " TEXT," + KEY_DESC + " TEXT"
                + ")";
        db.execSQL(sql);
    }

    public void onDrop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void createAction(Action a) {
        SQLiteDatabase db = helper.getWritableDatabase();
        createAction(db, a);
        db.close();
    }

    public void createAction(SQLiteDatabase db, Action a) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, a.getId());
        values.put(KEY_NAME, a.getName());
        values.put(KEY_PICTURE, a.getPicture());
        values.put(KEY_DESC, a.getDescription());

        db.insert(TABLE_NAME, null, values);
    }

    public Action getAction(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME, KEY_PICTURE, KEY_DESC }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Action a = new Action();
        a.setId(cursor.getInt(0));
        a.setName(cursor.getString(1));
        a.setPicture(cursor.getString(2));
        a.setDescription(cursor.getString(3));

        db.close();

        return a;
    }

    public List<Action> getAllActions() {
        List<Action> actionList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Action a = new Action();
                a.setId(cursor.getInt(0));
                a.setName(cursor.getString(1));
                a.setPicture(cursor.getString(2));
                a.setDescription(cursor.getString(3));
                // Adding to list
                actionList.add(a);
            } while (cursor.moveToNext());
        }

        db.close();

        // return list
        return actionList;
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
