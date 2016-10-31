package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.adapter.PlanListAdapter;
import truestrength.fitnessplan.db.DatabaseHandler;
import truestrength.fitnessplan.entity.Plan;

public class PlanListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static boolean hasShownCreatePlan = false;
    private ListView planListView;

    private PlanListAdapter planListAdapter;
    private int curItemPos = -1;
    private Plan curPlan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        setTitle(R.string.title_plan_list);

        planListView = (ListView)findViewById(R.id.planListView);
        planListAdapter = new PlanListAdapter(this);
        planListView.setAdapter(planListAdapter);

        planListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                curItemPos = pos;
                curPlan = planListAdapter.getItem(pos);

                PopupMenu popupMenu = new PopupMenu(PlanListActivity.this, view);
                popupMenu.setOnMenuItemClickListener(PlanListActivity.this);
                popupMenu.inflate(R.menu.plan_list_popupmenu);
                popupMenu.show();

                return true;
            }
        });

        planListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                showPlanActivity(planListAdapter.getItem(pos));
            }
        });

        hasShownCreatePlan = false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!hasShownCreatePlan && DatabaseHandler.getInstance().getPlanCount() == 0) {
            hasShownCreatePlan = true;
            Intent i = new Intent(this, CreatePlanActivity.class);
            startActivity(i);
        }
    }

    public void onCreatePlanBtnClicked(View view) {
        Intent i = new Intent(this, CreatePlanActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(curPlan != null) {
            planListAdapter.remove(curPlan);

            curPlan = null;
        }

        return true;
    }

    private void showPlanActivity(Plan plan) {
        Intent i = new Intent(this, PlanActivity.class);
        i.putExtra("plan", plan);

        startActivity(i);
    }
}
