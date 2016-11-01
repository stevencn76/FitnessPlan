package truestrength.fitnessplan.entity;

/**
 * Created by steven on 31/10/16.
 */

public class DayExercise {
    private int id;
    private int groupId;
    private String seq;
    private String name;
    private boolean done;

    public DayExercise() {

    }

    public DayExercise(int id, int groupId, String seq, String name, boolean done) {
        this.id = id;
        this.groupId = groupId;
        this.seq = seq;
        this.name = name;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
