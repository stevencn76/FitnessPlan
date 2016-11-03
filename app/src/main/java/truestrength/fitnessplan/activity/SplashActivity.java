package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.db.MyDB;
import truestrength.fitnessplan.util.Profile;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                MyDB db = new MyDB(SplashActivity.this);
                db.reloadWorkouts();

                try {
                    Thread.sleep(1000);
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
