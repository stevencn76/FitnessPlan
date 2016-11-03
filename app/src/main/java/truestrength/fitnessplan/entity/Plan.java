package truestrength.fitnessplan.entity;

import java.io.Serializable;
import java.util.Date;

import truestrength.fitnessplan.util.DateUtil;

/**
 * Created by steven on 31/10/16.
 */

public class Plan implements Serializable {
    private int id;
    private String startDate;
    private String sqlStartDate;
    private String endDate;
    private String sqlEndDate;
    private int weekCount;
    private int progress;

    public Plan() {

    }

    public Plan(int id, String startDate, String endDate, int progress) {
        this.id = id;
        setStartDate(startDate);
        setEndDate(endDate);
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
        Date td = DateUtil.fromDateString(startDate);
        sqlStartDate = DateUtil.toSqlDateString(td);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        Date td = DateUtil.fromDateString(endDate);
        sqlEndDate = DateUtil.toSqlDateString(td);
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

    public void setSqlStartDate(String sqlStartDate) {
        this.sqlStartDate = sqlStartDate;
        Date td = DateUtil.fromSqlDateString(sqlStartDate);
        startDate = DateUtil.toDateString(td);
    }

    public String getSqlStartDate() {
        return sqlStartDate;
    }

    public void setSqlEndDate(String sqlEndDate) {
        this.sqlEndDate = sqlEndDate;
        Date td = DateUtil.fromSqlDateString(sqlEndDate);
        endDate = DateUtil.toDateString(td);
    }

    public String getSqlEndDate() {
        return sqlEndDate;
    }
}
