package truestrength.fitnessplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.dto.Group;

/**
 * Created by steven on 31/10/16.
 */

public class ExerciseExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Group> groupList = new ArrayList<>();

    public ExerciseExpandListAdapter(Context context) {
        this.context = context;

        Group g;
        g = new Group("Warm up:");
        groupList.add(g);
        g.getExercises().add(new DayExercise(1, 1, 1, true));
        g.getExercises().add(new DayExercise(2, 1, 2, true));
        g.getExercises().add(new DayExercise(3, 1, 3, true));

        g = new Group("Strength:");
        groupList.add(g);
        g.getExercises().add(new DayExercise(4, 2, 4, true));
        g.getExercises().add(new DayExercise(5, 2, 4, true));

    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupList.get(i).getExercises().size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return groupList.get(i).getExercises().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return groupList.get(i).getExercises().get(i1).getId();
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
            view = vi.inflate(R.layout.exercise_group_item, null);
        }

        Group g = (Group)getGroup(i);
        if(g != null) {
            TextView titleView = (TextView)view.findViewById(R.id.titleTextView);
            if(titleView != null) {
                titleView.setText(g.getName());
            }
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(R.layout.exercise_item, null);
        }

        DayExercise e = (DayExercise)getChild(i, i1);
        if(e != null) {
            TextView titleView = (TextView)view.findViewById(R.id.titleTextView);
            if(titleView != null) {
                titleView.setText(e.toString());
            }

            CheckBox doneCheckBox = (CheckBox)view.findViewById(R.id.doneCheckBox);
            if(doneCheckBox != null) {
                doneCheckBox.setChecked(e.isDone());
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
