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
        
        JPanel nm=new UserDetailManagement();
//        NumberAppEndPanel endPanel= new NumberAppEndPanel();
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(Consts.SCREEN_WIDHT,Consts.SCREEN_HEIGHT);
        frame.add(nm);
        nm.setVisible(true);
        frame.setVisible(true);
    }
}
