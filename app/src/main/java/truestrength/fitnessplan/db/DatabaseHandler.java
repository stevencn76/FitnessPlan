package truestrength.fitnessplan.db;

/**
 * Created by steven on 31/10/16.
 */

public class DatabaseHandler {
    private static DatabaseHandler instance;

    private DatabaseHandler() {

    }

    public static DatabaseHandler getInstance() {
        if(instance == null) {
            instance = new DatabaseHandler();
        }

        return instance;
    }

    public int getPlanCount() {
        return 0;
    }

    public boolean isCurPlanAvailable() {
        return false;
    }

    public boolean hasNextPlan() {
        return false;
    }
}
