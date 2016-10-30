package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.util.Profile;

public class DeclaimerActivity extends AppCompatActivity {
    private CheckBox agreeCheckBox;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaimer);

        agreeCheckBox = (CheckBox)findViewById(R.id.agreeCheckBox);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
    }

    public void onAgreeCheckBoxClicked(View view) {
        if(agreeCheckBox.isChecked()) {
            confirmBtn.setEnabled(true);
        } else {
            confirmBtn.setEnabled(false);
        }
    }

    public void onConfirmBtnClicked(View view) {
        Profile.getInstance().setDeclaimerAgreed(true);
        Profile.getInstance().save(this);

        finish();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
