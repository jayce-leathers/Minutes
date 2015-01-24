package apps.jayceleathers.me.data;

/**
 * Created by Jayce on 1/23/15.
 */
public class Interval {
    private String label;
    private Long workTime;
    private Long restTime;

    public Interval(String label, Long workTime, Long restTime) {
        this.label = label;
        this.workTime = workTime;
        this.restTime = restTime;
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
