package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.adapter.ExerciseExpandListAdapter;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.DayExercise;

public class ExerciseListActivity extends AppCompatActivity {
    private ExpandableListView exerciseExListView;
    private ExerciseExpandListAdapter exerciseExAdapter;
    private Day day;

    private boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        day = (Day)getIntent().getSerializableExtra("day");

        exerciseExListView = (ExpandableListView)findViewById(R.id.exerciseExListView);
        exerciseExAdapter = new ExerciseExpandListAdapter(this, day);
        exerciseExListView.setAdapter(exerciseExAdapter);
        for(int i=0; i<exerciseExAdapter.getGroupCount(); i++) {
            exerciseExListView.expandGroup(i);
        }

        exerciseExListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
                DayExercise tde = (DayExercise)exerciseExAdapter.getChild(groupPos, childPos);
                Intent i = new Intent(ExerciseListActivity.this, ExerciseActivity.class);
                i.putExtra("dayexercise", tde);
                startActivity(i);

                return true;
            }
        });

        setTitle(R.string.title_exercise_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        exerciseExAdapter.reload();

        if(firstStart) {
            for(int i=0; i<exerciseExAdapter.getGroupCount(); i++) {
                exerciseExListView.expandGroup(i);
            }
            firstStart = false;
        }
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
