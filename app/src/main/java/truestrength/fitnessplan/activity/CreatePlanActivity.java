package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.service.DataService;

public class CreatePlanActivity extends AppCompatActivity {
    private DatePicker planDatePicker;
    private Button okBtn;
    private Calendar calendar;
    private Date selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        planDatePicker = (DatePicker)findViewById(R.id.planDatePicker);
        calendar = Calendar.getInstance();
        selectDate = calendar.getTime();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        planDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                selectDate = calendar.getTime();
            }
        });

        okBtn = (Button)findViewById(R.id.okBtn);

        setTitle(R.string.title_create_plan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onOkBtnClicked(View view) {
        try {
            DataService.getInstance(this).createPlan(selectDate);
            onBackPressed();
        } catch (Exception e) {
            Log.d(MySettings.LOG_TAG, "create plan", e);
            Toast.makeText(this, "Create Plan Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void onCancelBtnClicked(View view) {
        onBackPressed();
    }
}
