package truestrength.fitnessplan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.service.DataService;
import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class PlanListAdapter extends ArrayAdapter<Plan> {
    private String today;

    public PlanListAdapter(Context context) {
        super(context, R.layout.plan_list_item);
    }

    public void reload() {
        today = DateUtil.toSqlDateString(new Date());

        this.clear();

        List<Plan> planList = DataService.getInstance(getContext()).getAllPlans();
        for(Plan tp : planList) {
            this.add(tp);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.plan_list_item, null);
        }

        Plan p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.titleTextView);
            TextView tt2 = (TextView) v.findViewById(R.id.progressTextView);

            if (tt1 != null) {
                String title = MessageFormat.format("{0} [ {1} Weeks ]", p.getStartDate(), p.getWeekCount());
                tt1.setText(title);
            }

            if (tt2 != null) {
                tt2.setText(p.getProgress() + "%");
            }

            if(today.compareTo(p.getSqlEndDate()) > 0) {
                tt1.setTextColor(Color.DKGRAY);
                tt1.setTextColor(Color.DKGRAY);
            } else if(today.compareTo(p.getSqlStartDate()) < 0) {
                tt1.setTextColor(Color.BLUE);
                tt1.setTextColor(Color.BLUE);
            } else {
                tt1.setTextColor(Color.RED);
                tt1.setTextColor(Color.RED);
            }
        }

        return v;
    }
}
