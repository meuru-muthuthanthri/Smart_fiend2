package smartfriend.applications.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.lang.time.DateUtils;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Nilaksha
 */
public class Scheduler implements Runnable {

    private ArrayList<TaskDetails> taskDetailList;
    private final SimpleDateFormat dateFormat;

    public Scheduler() {
        taskDetailList = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
    }

    @Override
    public void run() {

        while (true) {
            //read the scheduler details file
            readSchedulerDetailFile();

            if (!taskDetailList.isEmpty()) {
                System.out.println("not empty");
                executeScheduler();
            } else {
                try {
                    System.out.println("sleeping");
                    Thread.sleep(1 * 60 * 60 * 1000);

                } catch (InterruptedException ex) {
                    Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void executeScheduler() {

        Date currentTime = new Date();
        TaskDetails taskDetails = taskDetailList.remove(0);

        System.out.println("inside scheduler");
        System.out.println(DateUtils.truncatedEquals(currentTime, taskDetails.getTaskDate(), Calendar.MINUTE));
        if (DateUtils.truncatedEquals(currentTime, taskDetails.getTaskDate(), Calendar.MINUTE)) {

            System.out.println("have a task in this minitue");
            playSound(taskDetails);
            if (taskDetails.getRepeatCount() > 0) {
                taskDetails.setRepeatCount(taskDetails.getRepeatCount() - 1);
                System.out.println("repeat------"+taskDetails.getRepeatCount());
                taskDetails.setTaskDate(DateUtils.addMinutes(taskDetails.getTaskDate(), 15));
                taskDetailList.add(taskDetails);
                Collections.sort(taskDetailList);
            }
        } else {
            long millisTillNextTask = taskDetails.getTaskDate().getTime() - currentTime.getTime();
            taskDetailList.add(taskDetails);
            Collections.sort(taskDetailList);
            System.out.println("sleeping for" + millisTillNextTask);
            try {
                Thread.sleep(millisTillNextTask);
            } catch (InterruptedException ex) {
                Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //play the recorded task
    public void playSound(TaskDetails taskDetails) {
        
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("recordingFilePath") + taskDetails.getWaveNumber() + ".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            System.out.println("playing the sound clip");

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {

            System.out.println("Error while playing the sound clip.");
        }
    }

    //read the scheduler details file
    public void readSchedulerDetailFile() {

        int waveNumber;
        int year;
        int month;
        int day;
        int hours;
        int minutes;
        int repeatCount;
        String ampm;

        BufferedReader bufferedReader;

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new FileReader(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("schedulerFilePath")));

            while ((currentLine = bufferedReader.readLine()) != null) {

                String words[] = currentLine.split(" ");

                waveNumber = Integer.parseInt(words[0].trim());
                year = Integer.parseInt(words[1].trim());
                month = Integer.parseInt(words[2].trim());
                day = Integer.parseInt(words[3].trim());
                hours = Integer.parseInt(words[4].trim());
                minutes = Integer.parseInt(words[5].trim());
                repeatCount = Integer.parseInt(words[6].trim());
                ampm = words[7].trim();

                String dateStr = day + "-" + month + "-" + year + " " + hours + ":" + minutes + " " + ampm;

                //get the scheduled time of the task
                Date taskDate = dateFormat.parse(dateStr);

                //get current time
                Date currentDate = new Date();
                currentDate = DateUtils.truncate(currentDate, Calendar.MINUTE);
                Date afterOneHourDate = DateUtils.addHours(currentDate, 1);

                //tasks which are scheduled within the next hour are put into the taskMap
                if (taskDate.equals(currentDate) || taskDate.after(currentDate) && taskDate.before(afterOneHourDate)) {

                    if (!taskDetailList.isEmpty()) {
                        boolean hasWaveNumber = false;
                        for (TaskDetails td : taskDetailList) {
                            if (td.getWaveNumber() == waveNumber) {
                                hasWaveNumber = true;
                            }
                        }
                        if (!hasWaveNumber) {
                            TaskDetails taskDetails = new TaskDetails();
                            taskDetails.setTaskDate(taskDate);
                            taskDetails.setTaskDateStr(dateStr);
                            taskDetails.setRepeatCount(repeatCount);
                            taskDetails.setWaveNumber(waveNumber);
                            taskDetailList.add(taskDetails);
                            Collections.sort(taskDetailList);
                        }
                    } else {
                        TaskDetails taskDetails = new TaskDetails();
                        taskDetails.setTaskDate(taskDate);
                        taskDetails.setTaskDateStr(dateStr);
                        taskDetails.setRepeatCount(repeatCount);
                        taskDetails.setWaveNumber(waveNumber);
                        taskDetailList.add(taskDetails);
                        Collections.sort(taskDetailList);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        Scheduler scheduler = new Scheduler();
        new Thread(scheduler).start();
    }
}
