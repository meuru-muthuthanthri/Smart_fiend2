package smartfriend.speechRecognition;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartfriend.applications.userprofile.UserDetails;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Isuri
 */
public class SpeechConfiguration {
    
    private static String modelPath;
    
    public static String getAcousticModel(){
        
        //Get User Details         
        UserDetails userdetails = new UserDetails();
        System.out.println(userdetails.getChildAge());
        if(userdetails.getChildAge()<=5){
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("acousticModelPath");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("acousticModelPath2");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelPath;
    }
    
     public static String getLanguageModel(){
        
        //Get User Details         
        UserDetails userdetails = new UserDetails();
        System.out.println(userdetails.getChildAge());
        if(userdetails.getChildAge()<=5){
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("languageModelPath");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("languageModelPath2");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelPath;
    }
     
      public static String getDIctionary(){
        
        //Get User Details         
        UserDetails userdetails = new UserDetails();
        System.out.println(userdetails.getChildAge());
        if(userdetails.getChildAge()<=5){
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("dictionaryPath");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            try {
                modelPath="file:/" + MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("dictionaryPath2");
            } catch (IOException ex) {
                Logger.getLogger(SpeechConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelPath;
    }
}
