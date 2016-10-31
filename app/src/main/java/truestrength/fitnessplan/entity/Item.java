package truestrength.fitnessplan.entity;

/**
 * Created by steven on 31/10/16.
 */

public class Item {
    private int id;
    private int exerciseId;
    private String name;
    private String value;

    public Item() {

    }

    public Item(int id, int exerciseId, String name, String value) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
