package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.adapter.DetailExpandListAdapter;

public class PlanActivity extends AppCompatActivity {
    private ExpandableListView detailListView;
    private DetailExpandListAdapter detailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        detailListView = (ExpandableListView)findViewById(R.id.detailExListView);
        detailListAdapter = new DetailExpandListAdapter(this);
        detailListView.setAdapter(detailListAdapter);
        int pos = detailListAdapter.getCurWeekIndex();
        if(pos >= 0 && pos < detailListAdapter.getGroupCount()) {
            detailListView.expandGroup(pos);
        } else if(detailListAdapter.getGroupCount() > 0) {
            detailListView.expandGroup(0);
        }

        detailListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
                Intent i = new Intent(PlanActivity.this, ExerciseListActivity.class);
                startActivity(i);

                return true;
            }
        });

        setTitle(R.string.title_plan_detail);

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
}
