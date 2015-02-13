/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.userprofile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import smartfriend.applications.numberlearning.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Consts;

/**
 *
 * @author user
 */
public class test {
    
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(Consts.SCREEN_WIDHT,Consts.SCREEN_HEIGHT);
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        fxPanel.setVisible(true);
        frame.setVisible(true);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
            // This method is invoked on JavaFX thread
            Parent parent = FXMLLoader.load(getClass().getResource("user_details.fxml"));
            Scene scene = new Scene(parent);
            fxPanel.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(UserDetailsGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        });
        
        UserDetails userdetails = new UserDetails();
        System.out.println(userdetails.getChildName()+" "+userdetails.getParentEmail()+" "+userdetails.getChildAge());
    }
}
