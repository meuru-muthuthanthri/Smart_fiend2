/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Meuru
 */
public class Test extends JFrame {

    static JPanel welcomeScreen, mainScreen;

    public static void main(String[] args)  {
        AudioInputStream audioInputStream = null;
        try {
            //        Test frame = new Test();
            //        frame.setUndecorated(true);
            //        //f.setOpacity(0.5f);
            //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //        welcomeScreen = (JPanel) (new WelcomeScreen(null));
            //        mainScreen = (JPanel) (new MainScreen());
            //        frame.add(welcomeScreen);
            //        frame.add(mainScreen);
            //        mainScreen.setVisible(false);
            //
            ////        f.add(new MainScreen(),-1);
            //        frame.setSize(welcomeScreen.getSize());
            //        //f.setLocation(100, 100);
            //        frame.setVisible(true);
            String soundName = "C:\\Users\\Meuru\\Desktop\\mouse1.wav";
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            System.out.println("sssss");
            while(true){
                System.out.println("kk");
            }
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Test() {
    }

    public void addPanel() {
        mainScreen.setVisible(true);
        remove(welcomeScreen);
//        welcomeScreen.setVisible(false);
    }
}
