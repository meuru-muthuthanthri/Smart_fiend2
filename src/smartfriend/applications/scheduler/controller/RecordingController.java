package smartfriend.applications.scheduler.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import smartfriend.applications.scheduler.TaskRecorder;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Isuri
 */
public class RecordingController implements Initializable {

    @FXML
    private Button startRecordingButton;
    @FXML
    private Button stopRecordingButton;
    @FXML
    private Button listenButton;
    @FXML
    private Button saveButton;
    
    int line_No;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        line_No = 0;
        int l = getLineNumber() + 1;
        TaskRecorder recorder = new TaskRecorder(l);

        startRecordingButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                System.out.println("start");
                recorder.captureAudio();
            }
        });

        stopRecordingButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                recorder.stopAudio();
            }
        });

        listenButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("recordingFilePath") + l + ".wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
            }
        });
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    //get the recording no
    public int getLineNumber() {
        
        BufferedReader bufferedReader = null;
        try {
            
            bufferedReader = new BufferedReader(new FileReader(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("schedulerFilePath")));

            while ((bufferedReader.readLine()) != null) {
                line_No++;
            }
            System.out.println("**line" + line_No);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return line_No;
    }
}
