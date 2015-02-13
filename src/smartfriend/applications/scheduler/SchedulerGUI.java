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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Isuri
 */
public class SchedulerGUI {

    JPanel schedulerPanel;
    
    public JPanel getShchedulerPanel(){
        
       SchedulerGUI st = new SchedulerGUI();
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                st.initAndShowGUI();
            }
        });
        return schedulerPanel;
    }
    
    private void initAndShowGUI() {
        // This method is invoked on Swing thread      
        schedulerPanel = new JPanel();
        final JFXPanel fxPanel = new JFXPanel();
        schedulerPanel.setSize(1370, 700);
        
        // Add JavaFX panel into swing JFrame
        schedulerPanel.add(fxPanel);
        //schedulerPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //schedulerPanel.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        try {
            // This method is invoked on JavaFX thread
            Parent parent = FXMLLoader.load(getClass().getResource("scheduler.fxml"));
            Scene scene = new Scene(parent);
            fxPanel.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(SchedulerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SchedulerGUI st = new SchedulerGUI();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                st.initAndShowGUI();
            }
        });
        
        // This should be run on main thread of the smart desk repeatedly **this is added here only for test purpoes**
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
        },0,60*60*1000);
    }
}
