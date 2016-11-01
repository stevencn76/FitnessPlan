package truestrength.fitnessplan.entity;

import java.io.Serializable;

/**
 * Created by steven on 1/11/16.
 */

public class Workout implements Serializable {
    private int id;
    private String name;

    public Workout() {

    }

    public Workout(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
