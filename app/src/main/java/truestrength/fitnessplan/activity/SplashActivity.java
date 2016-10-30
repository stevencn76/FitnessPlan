package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.util.Profile;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}

                Profile.getInstance().load(SplashActivity.this);

                SplashActivity.this.finish();

                if(Profile.getInstance().isDeclaimerAgreed()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, DeclaimerActivity.class);
                    startActivity(i);
                }
            }
        }.start();
    }
}
