package truestrength.fitnessplan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.adapter.PlanListAdapter;
import truestrength.fitnessplan.common.MySettings;
import truestrength.fitnessplan.service.DataService;
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

        planListAdapter.reload();

        if(!hasShownCreatePlan && planListAdapter.getCount() == 0) {
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
            try {
                DataService.getInstance(this).deletePlan(curPlan.getId());
                planListAdapter.remove(curPlan);

                curPlan = null;

                planListAdapter.reload();

                Toast.makeText(this, "Delete plan successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.d(MySettings.LOG_TAG, "delete plan failed", e);
                Toast.makeText(this, "Delete plan failed, " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        return true;
    }

    private void showPlanActivity(Plan plan) {
        Intent i = new Intent(this, PlanActivity.class);
        i.putExtra("plan", plan);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.planlist_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.helpMenuItem:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.aboutMenuItem:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
