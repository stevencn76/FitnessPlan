package truestrength.fitnessplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Week;
import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class DetailExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Week> weekList = new ArrayList<>();
    private ArrayList<ArrayList<Day>> weekDayList = new ArrayList<>();
    private int curWeekIndex = -1;

    public DetailExpandListAdapter(Context context) {
        this.context = context;

        weekList.add(new Week(1, 1, 1, 100));
        weekDayList.add(new ArrayList<Day>());
        weekDayList.get(0).add(new Day(1, 1, "24/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(2, 1, "25/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(3, 1, "26/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(4, 1, "27/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(5, 1, "28/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(6, 1, "29/10/2016", 1, 100));
        weekDayList.get(0).add(new Day(7, 1, "30/10/2016", 1, 100));

        curWeekIndex = 0;
    }

    public int getCurWeekIndex() {
        return curWeekIndex;
    }

    @Override
    public int getGroupCount() {
        return weekList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return weekDayList.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return weekList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return weekDayList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return weekList.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return weekDayList.get(i).get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(R.layout.week_group_item, null);
        }

        Week week = (Week)getGroup(i);
        if(week != null) {
            TextView titleView = (TextView)view.findViewById(R.id.titleTextView);
            if(titleView != null) {
                titleView.setText("Week " + week.getNumber());
            }

            TextView progressTextView = (TextView)view.findViewById(R.id.progressTextView);
            if(progressTextView != null) {
                progressTextView.setText(week.getProgress() + "%");
            }
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(R.layout.day_groupchild_item, null);
        }

        Day day = (Day)getChild(i, i1);
        if(day != null) {
            TextView titleView = (TextView)view.findViewById(R.id.titleTextView);
            if(titleView != null) {
                titleView.setText(day.getDate());
            }

            TextView progressTextView = (TextView)view.findViewById(R.id.progressTextView);
            if(progressTextView != null) {
                progressTextView.setText(day.getProgress() + "%");
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
