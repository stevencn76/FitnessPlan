package truestrength.fitnessplan.entity;

/**
 * Created by steven on 1/11/16.
 */

public class Action {
    private int id;
    private String name;
    private String picture;
    private String description;

    public Action() {

    }

    public Action(int id, String name, String picture, String desc) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.description = desc;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
