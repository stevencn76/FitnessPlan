package truestrength.fitnessplan.service;

import android.content.Context;

/**
 * Created by steven on 31/10/16.
 */

public class DataService {
    private static DataService instance;

    private Context context;

    private DataService() {

    }

    public static DataService getInstance(Context context) {
        if(instance == null) {
            instance = new DataService();
            instance.context = context;
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
