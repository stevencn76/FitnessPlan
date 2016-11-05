package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.db.MyDB;
import truestrength.fitnessplan.service.DataService;
import truestrength.fitnessplan.util.Profile;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                long t1 = System.currentTimeMillis();
                DataService.getInstance(SplashActivity.this).initService();
                long t2 = System.currentTimeMillis();

                long remain = 2000 - (t2 - t1);
                Log.i(MySettings.LOG_TAG, "remain time: " + remain);
                if(remain > 0)
                try {
                    Thread.sleep(remain);
                } catch (Exception e) {}

                SplashActivity.this.finish();

                if(Profile.getInstance(SplashActivity.this).isDeclaimerAgreed()) {
                    Intent i = new Intent(SplashActivity.this, PlanListActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, DeclaimerActivity.class);
                    startActivity(i);
                }
            }
        }.start();
    }
}
