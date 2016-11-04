package truestrength.fitnessplan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.service.DataService;

public class ExerciseActivity extends AppCompatActivity {
    private DayExercise dayExercise;
    private Exercise exercise;
    private Action action;

    private TextView groupTextView;
    private TextView nameTextView;
    private CheckBox doneCheckBox;
    private ImageView imageView;
    private TextView setsTextView;
    private TextView repsTextView;
    private TextView loadTextView;
    private TextView tempoTextView;
    private TextView restTextView;
    private TextView commentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        groupTextView = (TextView) findViewById(R.id.groupTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        doneCheckBox = (CheckBox) findViewById(R.id.doneCheckBox);
        imageView = (ImageView) findViewById(R.id.imageView);
        setsTextView = (TextView) findViewById(R.id.setsTextView);
        repsTextView = (TextView) findViewById(R.id.repsTextView);
        loadTextView = (TextView) findViewById(R.id.loadTextView);
        tempoTextView = (TextView) findViewById(R.id.tempoTextView);
        restTextView = (TextView) findViewById(R.id.restTextView);
        commentsTextView = (TextView) findViewById(R.id.commentsTextView);

        dayExercise = (DayExercise) getIntent().getSerializableExtra("dayexercise");

        doneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DataService.getInstance(ExerciseActivity.this).setDayExerciseDone(
                            dayExercise.getId(), doneCheckBox.isChecked());

                    finish();
                } catch (Exception e) {
                    Log.d(MySettings.LOG_TAG, "update progress error", e);
                    Toast.makeText(ExerciseActivity.this,
                            "Update progress error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setTitle(R.string.title_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load() {
        exercise = DataService.getInstance(this).getExercise(dayExercise.getExerciseId());
        action = DataService.getInstance(this).getAction(exercise.getActionId());

        groupTextView.setText(exercise.getGroupName());
        nameTextView.setText(action.getName());
        doneCheckBox.setChecked(dayExercise.isDone());

        int imgId = getResources().getIdentifier(action.getPicture(), "raw", getPackageName());
        if(imgId != 0) {
            imageView.setImageResource(imgId);
        }

        setsTextView.setText(exercise.getSets());
        repsTextView.setText(exercise.getReps());
        loadTextView.setText(exercise.getLoad());
        tempoTextView.setText(exercise.getTempo());
        restTextView.setText(exercise.getRest());
        commentsTextView.setText(exercise.getComments());
    }

    @Override
    protected void onStart() {
        super.onStart();

        load();
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
}
