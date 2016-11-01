package truestrength.fitnessplan.entity;

import java.io.Serializable;

/**
 * Created by steven on 1/11/16.
 */

public class Exercise implements Serializable {
    private int id;
    private int workoutId;
    private String index;
    private String groupName;
    private int actionId;
    private String sets;
    private String reps;
    private String load;
    private String tempo;
    private String rest;
    private String comments;

    public Exercise() {

    }

    public Exercise(int id, int workoutId, String index, String groupName, int actionId, String sets, String reps, String load, String tempo, String rest, String comments) {
        this.id = id;
        this.workoutId = workoutId;
        this.index = index;
        this.groupName = groupName;
        this.actionId = actionId;
        this.sets = sets;
        this.reps = reps;
        this.load = load;
        this.tempo = tempo;
        this.rest = rest;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
