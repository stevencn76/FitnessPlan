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
    private Date startDate;
    private Date endDate;
    private int weekCount;
    private int progress;

    public Plan() {

    }

    public Plan(int id, Date startDate, Date endDate, int progress) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
