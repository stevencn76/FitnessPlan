package truestrength.fitnessplan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.service.DataService;

public class ExerciseActivity extends AppCompatActivity {
    private DayExercise dayExercise;
    private Exercise exercise;
    private Action action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        dayExercise = (DayExercise)getIntent().getSerializableExtra("dayexercise");
        load();

        setTitle(R.string.title_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load() {
        exercise = DataService.getInstance(this).getExercise(dayExercise.getExerciseId());
        action = DataService.getInstance(this).getAction(exercise.getActionId());
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
