package truestrength.fitnessplan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by steven on 1/11/16.
 */

public class MyDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "texts.db";

    private Context context;

    private WorkoutHandler workoutHandler;
    private ActionHandler actionHandler;
    private ExerciseHandler exerciseHandler;
    private DayWorkoutHandler dayWorkoutHandler;

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;

        workoutHandler = new WorkoutHandler(this);
        actionHandler = new ActionHandler(this);
        exerciseHandler = new ExerciseHandler(this);
        dayWorkoutHandler = new DayWorkoutHandler(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        workoutHandler.onCreate(db);
        actionHandler.onCreate(db);
        exerciseHandler.onCreate(db);
        dayWorkoutHandler.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dayWorkoutHandler.onDrop(db);
        exerciseHandler.onDrop(db);
        actionHandler.onDrop(db);
        workoutHandler.onDrop(db);

        onCreate(db);
    }

    public WorkoutHandler getWorkoutHandler() {
        return workoutHandler;
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public DayWorkoutHandler getDayWorkoutHandler() {
        return dayWorkoutHandler;
    }
/*
    public void reloadBook() {
        BufferedReader in = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.book);
            InputStreamReader isr = new InputStreamReader(is);
            in = new BufferedReader(isr);

            String line = nextLine(in);
            if(line == null) {
                Toast.makeText(context, "not found version in book data", Toast.LENGTH_LONG).show();
                return;
            }

            int version = getBookVersion(line);
            if(version < 0) {
                Toast.makeText(context, "book version not correct", Toast.LENGTH_LONG).show();
                return;
            }

            if(version < ProfileManager.getInstance().getDataVersion()) {
                return;
            }

            ProfileManager.getInstance().setDataVersion(version);
            ProfileManager.getInstance().save(context);

            getBookmarkHandler().deleteAll();
            getItemHandler().deleteAll();
            getSectionHandler().deleteAll();
            getChapterHandler().deleteAll();

            Hashtable<String, Section> sectionTable = new Hashtable<>();

            line = null;
            Chapter curChapter = null;
            while((line=nextLine(in)) != null) {
                Chapter tc = readChapter(line);
                if(tc != null) {
                    curChapter = tc;
                    getChapterHandler().createChapter(tc);
                } else {
                    Item ti = new Item();
                    Section ts = readItem(line, ti);
                    if(ts != null && curChapter != null) {
                        String key = curChapter.getId() + "-" + ts.getCode();
                        Section tmps = sectionTable.get(key);
                        if(tmps == null) {
                            tmps = getSectionHandler().createSection(curChapter.getId(), ts.getCode(), ts.getTitle());
                            sectionTable.put(key, tmps);
                        }

                        getItemHandler().createItem(tmps.getId(), ti.getCode(), ti.getContent());
                    }
                }
            }
        } catch (Exception e) {
            Log.d("MyDB", "reload book failed", e);
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
            } catch (Exception e){}
        }
    }

    private Chapter readChapter(String s) throws Exception {
        if(s.startsWith("chapter:")) {
            s = s.substring("chapter:".length());
            s = s.trim();
            int spacePos = s.indexOf(" ");
            Chapter c = new Chapter();
            c.setId(Integer.parseInt(s.substring(0, spacePos)));
            c.setTitle(s.substring(spacePos+1).trim());

            return c;
        }

        return null;
    }

    private Section readItem(String source, Item item) throws Exception {
        int spacePos = source.indexOf(" ");
        String prefix = source.substring(0, spacePos);
        String [] ss1 = prefix.split("\\.");
        Section sec = new Section();
        sec.setCode(Integer.parseInt(ss1[0]));
        sec.setTitle("Section" + sec.getCode());

        item.setCode(Integer.parseInt(ss1[1]));
        item.setContent(source.substring(spacePos + 1).trim());

        return sec;
    }

    private int getBookVersion(String s) {
        if(s == null)
            return -1;

        if(s.startsWith("version:")) {
            s = s.substring("version:".length());

            s = s.trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return -1;
            }
        }

        return -1;
    }

    private String nextLine(BufferedReader reader) throws Exception {
        String line = null;

        while(true) {
            line = reader.readLine();
            if (line == null)
                break;

            line = trimString(line);
            if (line.length() != 0)
                break;
        }

        return line;
    }

    private String trimString(String s) {
        if(s == null)
            return null;

        s = s.trim();
        return s;
    }
    */
}
