package truestrength.fitnessplan.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by steven on 31/10/16.
 */

public class Profile {
    private static Profile instance;
    public static final String FPP_PREFERENCES = "FitnessPlanPreferences";

    private static final String DECLAIMERAGREED_KEY = "DECLAIMERAGREED";
    private boolean declaimerAgreed;

    private Profile() {

    }

    public static Profile getInstance() {
        if(instance == null) {
            instance = new Profile();
        }

        return instance;
    }

    public void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FPP_PREFERENCES, Context.MODE_PRIVATE);

        declaimerAgreed = sp.getBoolean(DECLAIMERAGREED_KEY, false);
    }

    public void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FPP_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(DECLAIMERAGREED_KEY, declaimerAgreed);

        editor.commit();

    }

    public boolean isDeclaimerAgreed() {
        return declaimerAgreed;
    }

    public void setDeclaimerAgreed(boolean declaimerAgreed) {
        this.declaimerAgreed = declaimerAgreed;
    }
}
