package smartfriend.speechRecognition;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartfriend.util.general.MainConfiguration;

public class SpeechRecognizer extends Observable implements Runnable {

    private String command;
//    private String correctCommand;
    private final Configuration configuration;
    private static SpeechRecognizer instance=null;

    private SpeechRecognizer() {
//        try {
            configuration = new Configuration();
            configuration.setAcousticModelPath(SpeechConfiguration.getAcousticModel());
            configuration.setDictionaryPath(SpeechConfiguration.getDIctionary());
            configuration.setLanguageModelPath(SpeechConfiguration.getLanguageModel());
            
            
//        } catch (IOException ex) {
//            Logger.getLogger(SpeechRecognizer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public static synchronized SpeechRecognizer getSpeechInstance(){
        if(instance ==null){
            instance= new SpeechRecognizer();
        }
        return instance;
    }

    public void setSpeechCommand(String command) {

        this.command = command;
    }

    public String getWord() {
        return command;
    }

    public String getSpeechCommand(String command) {

        if(command.contains("next")){
            command = "next";
        }
        
        if(command.contains("yes")){
            command = "yes";
        }
        
        if (command.contains("story") || command.contains("book")) {
            System.out.println("story book command");
            command = "story book";
        }

        if (command.contains("try") || command.contains("again")) {
            System.out.println("try again command");
            command = "try again";
        }

        if (command.contains("play") || command.contains("video")) {
            System.out.println("play video command");
            command = "play video";
        }     
        return command;
    }

    public void recognizeSpeech() throws InterruptedException {

        //speech recording
        try {
//            final SoundRecorder recorder = new SoundRecorder();
//            Thread stopper = new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                    recorder.finish();
//                }
//            });
//            stopper.start();
//            // start recording
//            recorder.start();
//
//            //Read from a recorded wave file 
//            StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
////            recognizer.startRecognition(new FileInputStream(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/recording.wav"));
//            recognizer.startRecognition(new FileInputStream(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/speaker1_1.wav"));
//
//            SpeechResult result;
//
//            while ((result = recognizer.getResult()) != null) {
//                setSpeechCommand(result.getHypothesis());
//                 setSpeechCommand(getSpeechCommand());
//            }
//            recognizer.stopRecognition();

            //************for live speech recognition****************//
//            Configuration configuration = new Configuration();
//            configuration.setAcousticModelPath("file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("acousticModelPath2"));
//            configuration.setDictionaryPath("file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("dictionaryPath2"));
//            configuration.setLanguageModelPath("file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("languageModelPath2"));
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);

            SpeechResult result;
            System.out.println("start recording......");

            while ((result = recognizer.getResult()) != null) {
                System.out.println("start");
                System.out.println(result.getHypothesis());
                setSpeechCommand(result.getHypothesis());
                setSpeechCommand(getSpeechCommand(getWord()));
                
                /*********************/
//                setSpeechCommand("play video");
                setChanged();        
                notifyObservers(getSpeechCommand(command));
                Thread.sleep(100);
            }
            System.out.println("stop recording....");
            recognizer.stopRecognition();
    }   catch (IOException ex) {
            Logger.getLogger(SpeechRecognizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        
        try {
            System.out.println("running speech");
            recognizeSpeech();     
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeechRecognizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
}
