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
        weekDayList.get(0).add(new Day(1, 1, DateUtil.toDate("24/10/2016"), 100));
        weekDayList.get(0).add(new Day(2, 1, DateUtil.toDate("25/10/2016"), 100));
        weekDayList.get(0).add(new Day(3, 1, DateUtil.toDate("26/10/2016"), 100));
        weekDayList.get(0).add(new Day(4, 1, DateUtil.toDate("27/10/2016"), 100));
        weekDayList.get(0).add(new Day(5, 1, DateUtil.toDate("28/10/2016"), 100));
        weekDayList.get(0).add(new Day(6, 1, DateUtil.toDate("29/10/2016"), 100));
        weekDayList.get(0).add(new Day(7, 1, DateUtil.toDate("30/10/2016"), 100));

        weekList.add(new Week(2, 1, 2, 40));
        weekDayList.add(new ArrayList<Day>());
        weekDayList.get(1).add(new Day(8, 1, DateUtil.toDate("31/10/2016"), 100));
        weekDayList.get(1).add(new Day(9, 1, DateUtil.toDate("01/11/2016"), 0));
        weekDayList.get(1).add(new Day(10, 1, DateUtil.toDate("02/11/2016"), 0));
        weekDayList.get(1).add(new Day(11, 1, DateUtil.toDate("03/11/2016"), 0));
        weekDayList.get(1).add(new Day(12, 1, DateUtil.toDate("04/11/2016"), 0));
        weekDayList.get(1).add(new Day(13, 1, DateUtil.toDate("05/11/2016"), 0));
        weekDayList.get(1).add(new Day(14, 1, DateUtil.toDate("06/11/2016"), 0));

        weekList.add(new Week(3, 1, 3, 0));
        weekDayList.add(new ArrayList<Day>());
        weekDayList.get(2).add(new Day(15, 1, DateUtil.toDate("07/11/2016"), 0));
        weekDayList.get(2).add(new Day(16, 1, DateUtil.toDate("08/11/2016"), 0));
        weekDayList.get(2).add(new Day(17, 1, DateUtil.toDate("09/11/2016"), 0));
        weekDayList.get(2).add(new Day(18, 1, DateUtil.toDate("10/11/2016"), 0));
        weekDayList.get(2).add(new Day(19, 1, DateUtil.toDate("11/11/2016"), 0));
        weekDayList.get(2).add(new Day(20, 1, DateUtil.toDate("12/11/2016"), 0));
        weekDayList.get(2).add(new Day(21, 1, DateUtil.toDate("13/11/2016"), 0));

        curWeekIndex = 1;
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
                titleView.setText(DateUtil.toDateAndWeekdayString(day.getDate()));
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
