package truestrength.fitnessplan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.cache.WeekCache;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.Plan;
import truestrength.fitnessplan.entity.Week;
import truestrength.fitnessplan.service.DataService;
import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class DetailExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<WeekCache> weekCacheList = new ArrayList<>();
    private int curWeekIndex = -1;
    private Plan plan;
    private String today;

    public DetailExpandListAdapter(Context context, Plan plan) {
        this.context = context;
        this.plan = plan;

        curWeekIndex = 0;
    }

    public void reload() {
        weekCacheList.clear();

        today = DateUtil.toSqlDateString(new Date());

        List<Week> weekList = DataService.getInstance(context).getWeeks(plan.getId());
        int index = 0;
        for(Week tw : weekList) {
            WeekCache wc = new WeekCache();
            wc.setWeek(tw);

            List<Day> dayList = DataService.getInstance(context).getDays(tw.getId());
            for(Day td : dayList) {
                wc.getDayList().add(td);

                if(td.getSqlDate().equals(today)) {
                    curWeekIndex = index;
                }
            }

            weekCacheList.add(wc);
            index ++;
        }

        this.notifyDataSetChanged();
    }

    public int getCurWeekIndex() {
        return curWeekIndex;
    }

    @Override
    public int getGroupCount() {
        return weekCacheList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return weekCacheList.get(i).getDayList().size();
    }

    @Override
    public Object getGroup(int i) {
        return weekCacheList.get(i).getWeek();
    }

    @Override
    public Object getChild(int i, int i1) {
        return weekCacheList.get(i).getDayList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return weekCacheList.get(i).getWeek().getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return weekCacheList.get(i).getDayList().get(i1).getId();
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
                if(day.getDayWorkoutId() != 0) {
                    progressTextView.setText(day.getProgress() + "%");
                } else {
                    progressTextView.setText("REST");
                }
            }

            int c = day.getSqlDate().compareTo(today);
            if(c < 0) {
                titleView.setTextColor(Color.DKGRAY);
                progressTextView.setTextColor(Color.DKGRAY);
            } else if(c == 0) {
                titleView.setTextColor(Color.RED);
                progressTextView.setTextColor(Color.RED);
            } else {
                titleView.setTextColor(Color.BLUE);
                progressTextView.setTextColor(Color.BLUE);
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
