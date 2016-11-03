package truestrength.fitnessplan.entity;

import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class Plan implements Serializable {
    private int id;
    private String startDate;
    private String endDate;
    private int weekCount;
    private int progress;

    public Plan() {

    }

    public Plan(int id, String startDate, String endDate, int progress) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;

        this.weekCount = (int)DateUtil.diffDays(startDate, endDate) / 7;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
