/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.numberlearning;

import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Consts;

/**
 *
 * @author Isuri
 */
public class test {
    
    private static SpeechRecognizer startVoiceRecognition() {
        SpeechRecognizer sp = SpeechRecognizer.getSpeechInstance();
        new Thread(sp).start();
        return sp;
    }
    
    public static void main(String[] args) {
        
        JPanel nm=new NumberApp();
//        NumberAppEndPanel endPanel= new NumberAppEndPanel();
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(Consts.SCREEN_WIDHT,Consts.SCREEN_HEIGHT);
        frame.add(nm);
//        frame.add(endPanel);
        nm.setVisible(true);
//        endPanel.setVisible(true);
        frame.setVisible(true);
        startVoiceRecognition();
        SpeechRecognizer.getSpeechInstance().addObserver((Observer) (nm));
        VoiceGenerator voice = VoiceGenerator.getVoiceGeneratorInstance();
        voice.voiceOutput("How many rabits you see here?");
    }

}
