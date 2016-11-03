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

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class PlanListAdapter extends ArrayAdapter<Plan> {
    public PlanListAdapter(Context context) {
        super(context, R.layout.plan_list_item);

        load();
    }

    private void load() {
        this.add(new Plan(1, "03/10/2016", "23/10/2016", 100));
        this.add(new Plan(2, "24/10/2016", "13/11/2016", 40));
        this.add(new Plan(3, "14/11/2016", "04/12/2016", 0));
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

            Date today = new Date();
/*
            if(today.after(p.getEndDate())) {
                tt1.setTextColor(Color.LTGRAY);
                tt1.setTextColor(Color.LTGRAY);
            } else if(today.before(p.getStartDate())) {
                tt1.setTextColor(Color.BLACK);
                tt1.setTextColor(Color.BLACK);
            } else {
*/                tt1.setTextColor(Color.BLUE);
                tt1.setTextColor(Color.BLUE);
//            }
        }

        return v;
    }
}
