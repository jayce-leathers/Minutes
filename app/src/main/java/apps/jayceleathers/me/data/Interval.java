package apps.jayceleathers.me.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Jayce on 1/23/15.
 * POJO representing an interval
 */
public class Interval extends SugarRecord<Interval> implements Serializable{
    private String label;
    private Long workTime;
    private Long restTime;
    private boolean work;
    private int reps;

    public Interval() {
    }



    public Interval(String label, Long workTime, Long restTime, int reps) {
        this.label = label;
        this.workTime = workTime;
        this.restTime = restTime;
        work = true;
        this.reps = reps;

    }

    public int getReps() {
        return reps;
    }

    public boolean isWork() {
        return work;
    }

    public void flipInterval() {
        this.work = !work;
    }

    public void setWork() { this.work = true;}

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

    public static String formatIntervalTimer(long milliseconds) {
        int secs = (int) (milliseconds / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        StringBuilder sb = new StringBuilder();

        if (mins == 0){
           sb.append("00");
        }
        else if (mins < 10){
            sb.append("0" + mins);
        }
        else {
            sb.append(mins);
        }
        sb.append(":");
        if(secs == 0) {
            sb.append("00");
        }
        else {
            sb.append(secs);
        }
        return sb.toString();
    }
}
