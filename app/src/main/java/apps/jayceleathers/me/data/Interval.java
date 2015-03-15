package apps.jayceleathers.me.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jayce on 1/23/15.
 */
public class Interval extends SugarRecord<Interval> implements Serializable{
    private String label;
    private Long workTime;
    private Long restTime;
    private boolean work;

    public Interval() {
    }

    public Interval(String label, Long workTime, Long restTime) {
        this.label = label;
        this.workTime = workTime;
        this.restTime = restTime;
        work = true;
    }

    public boolean isWork() {
        return work;
    }

    public void flipInterval() {
        this.work = !work;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Long workTime) {
        this.workTime = workTime;
    }

    public Long getRestTime() {
        return restTime;
    }

    public void setRestTime(Long restTime) {
        this.restTime = restTime;
    }
}
