package truestrength.fitnessplan.entity;

import java.io.Serializable;

/**
 * Created by steven on 31/10/16.
 */

public class Week implements Serializable {
    private int id;
    private int planId;
    private int number;
    private int progress;

    public Week() {

    }

    public Week(int id, int planId, int number, int progress) {
        this.id = id;
        this.planId = planId;
        this.number = number;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
