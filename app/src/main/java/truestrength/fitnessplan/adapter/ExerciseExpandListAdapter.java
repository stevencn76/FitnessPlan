package truestrength.fitnessplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import truestrength.fitnessplan.R;
import truestrength.fitnessplan.entity.Action;
import truestrength.fitnessplan.entity.Day;
import truestrength.fitnessplan.entity.DayExercise;
import truestrength.fitnessplan.dto.Group;
import truestrength.fitnessplan.entity.Exercise;
import truestrength.fitnessplan.service.DataService;

/**
 * Created by steven on 31/10/16.
 */

public class ExerciseExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Group> groupList = new ArrayList<>();
    private Hashtable<String, Group> groupNameTable = new Hashtable<>();
    private Day day;

    public ExerciseExpandListAdapter(Context context, Day day) {
        this.context = context;
        this.day = day;
    }

    public void reload() {
        groupList.clear();
        groupNameTable.clear();

        List<DayExercise> dayExerciseList = DataService.getInstance(context).getDayExercises(day.getId());

        for(DayExercise tde : dayExerciseList) {
            int eid = tde.getExerciseId();
            Exercise te = DataService.getInstance(context).getExercise(eid);
            Group tg = groupNameTable.get(te.getGroupName());
            if(tg == null) {
                tg = new Group();
                tg.setName(te.getGroupName());
                groupNameTable.put(te.getGroupName(), tg);
                groupList.add(tg);
            }

            tg.getExercises().add(tde);
        }

        this.notifyDataSetChanged();
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
                Exercise te = DataService.getInstance(context).getExercise(e.getExerciseId());
                Action ta = DataService.getInstance(context).getAction(te.getActionId());
                titleView.setText(ta.getName());
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
