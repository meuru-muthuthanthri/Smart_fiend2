/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartfriend.speechRecognition;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import smartfriend.applications.numberlearning.NumberApp;
import smartfriend.applications.scheduler.Scheduler;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Consts;

/**
 *
 * @author user
 */
public class tester {
    
    public static void main(String[] args) {
        NumberApp nm=new NumberApp();
//        NumberAppEndPanel endPanel= new NumberAppEndPanel();
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(Consts.SCREEN_WIDHT,Consts.SCREEN_HEIGHT);
        frame.add(nm);
//        frame.add(endPanel);
        nm.setVisible(true);
//        endPanel.setVisible(true);
        frame.setVisible(true);
        VoiceGenerator voice = VoiceGenerator.getVoiceGeneratorInstance();
        
        voice.voiceOutput("How many rabits you see here?");
        SpeechRecognizer sp= SpeechRecognizer.getSpeechInstance();
        sp.addObserver(nm);
        new Thread(sp).start();
        
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
