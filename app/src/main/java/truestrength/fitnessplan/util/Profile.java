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

    private static final String DATAVERSION_KEY = "DATAVERSION";
    private String dataVersion;

    private Profile() {

    }

    public static Profile getInstance(Context context) {
        if(instance == null) {
            instance = new Profile();
            instance.load(context);
        }

        return instance;
    }

    public void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FPP_PREFERENCES, Context.MODE_PRIVATE);

        declaimerAgreed = sp.getBoolean(DECLAIMERAGREED_KEY, false);
        dataVersion = sp.getString(DATAVERSION_KEY, "");
    }

    public void save(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FPP_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(DECLAIMERAGREED_KEY, declaimerAgreed);
        editor.putString(DATAVERSION_KEY, dataVersion);

        editor.commit();

    }

    public boolean isDeclaimerAgreed() {
        return declaimerAgreed;
    }

    public void setDeclaimerAgreed(boolean declaimerAgreed) {
        this.declaimerAgreed = declaimerAgreed;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }
}
