package smartfriend.applications.scheduler;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nilaksha
 */
public class TaskDetails implements Comparable<TaskDetails> {

    private Date taskDate;
    private String taskDateStr;
    private int repeatCount;
    private int waveNumber;

    TaskDetails() {
        
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskDateStr() {
        return taskDateStr;
    }

    public void setTaskDateStr(String taskDateStr) {
        this.taskDateStr = taskDateStr;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }
    
    @Override
    public int compareTo(TaskDetails other) {

        if (taskDate.equals(other.getTaskDate())) {
            return 0;
        } else if (taskDate.after(other.getTaskDate())) {
            return 1;
        } else {
            return -1;
        }
    }
}