package truestrength.fitnessplan.activity;

import android.content.Context;
import android.content.Intent;

import truestrength.fitnessplan.db.DatabaseHandler;
import truestrength.fitnessplan.util.Profile;

/**
 * Created by steven on 31/10/16.
 */

public class ActivitySwitcher {
    private static ActivitySwitcher instance;

    private ActivitySwitcher() {

    }

    public static ActivitySwitcher getInstance() {
        if(instance == null) {
            instance = new ActivitySwitcher();
        }

        return instance;
    }

    private boolean checkDeclaimerAgree(Context context) {
        if(Profile.getInstance(context).isDeclaimerAgreed())
            return true;

        Intent i = new Intent(context, DeclaimerActivity.class);
        context.startActivity(i);

        return true;
    }

    public void goPlanList(Context context) {
        if(!checkDeclaimerAgree(context))
            return;


        if(DatabaseHandler.getInstance().getPlanCount() == 0) {
            Intent i = new Intent(context, CreatePlanActivity.class);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, PlanListActivity.class);
            context.startActivity(i);
        }
    }
}
