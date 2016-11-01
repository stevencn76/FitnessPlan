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
import truestrength.fitnessplan.entity.Group;

/**
 * Created by steven on 31/10/16.
 */

public class ExerciseExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Group> groupList = new ArrayList<>();

    public ExerciseExpandListAdapter(Context context) {
        this.context = context;

        Group g;
        g = new Group(1, 1, "Warm up:", 1);
        groupList.add(g);
        g.getExercises().add(new DayExercise(1, 1, "1a", "Foam Roll", true));
        g.getExercises().add(new DayExercise(2, 1, "1b", "Mobility/Activations Drills", true));
        g.getExercises().add(new DayExercise(3, 1, "1c", "Rotator Cuff Work", true));

        g = new Group(2, 1, "Strength:", 2);
        groupList.add(g);
        g.getExercises().add(new DayExercise(4, 2, "2", "Bench Press", true));
        g.getExercises().add(new DayExercise(5, 2, "3", "Pendlay Row", true));

        g = new Group(3, 1, "Conditioning:", 3);
        groupList.add(g);
        g.getExercises().add(new DayExercise(6, 3, "4a", "Incline DB Press*", true));
        g.getExercises().add(new DayExercise(7, 3, "4b", "Pull Ups", true));
        g.getExercises().add(new DayExercise(8, 3, "5a", "Cable Crossover", true));
        g.getExercises().add(new DayExercise(9, 3, "5b", "Chest Dips", true));
        g.getExercises().add(new DayExercise(10, 3, "6a", "Lat Pulldown", false));
        g.getExercises().add(new DayExercise(11, 3, "6b", "T-Bar Row", false));
        g.getExercises().add(new DayExercise(12, 3, "7a", "Cook-Willis Press", false));
        g.getExercises().add(new DayExercise(13, 3, "7b", "Cable Pullovers", false));

        g = new Group(4, 1, "Abs:", 4);
        groupList.add(g);
        g.getExercises().add(new DayExercise(14, 4, "", "Hanging Knee Raise", false));
        g.getExercises().add(new DayExercise(15, 4, "", "SB Crunch w. Rotation", false));
        g.getExercises().add(new DayExercise(16, 4, "", "SB Crunch", false));
        g.getExercises().add(new DayExercise(17, 4, "", "Stick Twists", false));

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
        return groupList.get(i).getId();
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
                titleView.setText(e.getName());
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
