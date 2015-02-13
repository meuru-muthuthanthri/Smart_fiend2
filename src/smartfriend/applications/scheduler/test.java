/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.scheduler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;
import smartfriend.util.general.Consts;

/**
 *
 * @author user
 */
public class test {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        fxPanel.setVisible(true);
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // This method is invoked on JavaFX thread
                    Parent parent = FXMLLoader.load(getClass().getResource("scheduler.fxml"));
                    Scene scene = new Scene(parent);
                    fxPanel.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(SchedulerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //*****************not here
        Timer timer = new Timer();
        // Check scheduling tasks every hour
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                System.out.println("every hour");
                Scheduler sh = new Scheduler();
//                sh.runScheduler();
                System.out.println("testing");
            }
        }, 0, 60 * 60 * 1000);
    }
}
