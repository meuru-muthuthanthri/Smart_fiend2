package smartfriend.tts;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.swing.SwingUtilities;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

public class VoiceGenerator {
    
    private static VoiceGenerator VOICE_GENERATOR;

    private VoiceGenerator(){        
    }
    
    public static synchronized VoiceGenerator getVoiceGeneratorInstance(){
        if(VOICE_GENERATOR == null){
            VOICE_GENERATOR = new VoiceGenerator();
        }
        return VOICE_GENERATOR;
    }
            
    
    //text to speech conversion
    public void voiceOutput(String text) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
            
            MaryInterface marytts = new LocalMaryInterface();
            Set<String> voices = marytts.getAvailableVoices();
            //selects the built voice
            marytts.setVoice(voices.iterator().next());
            AudioInputStream audio = marytts.generateAudio(text);
            AudioPlayer player = new AudioPlayer(audio);
            player.start();
            player.join();
//            System.exit(0);
        } catch (MaryConfigurationException ex) {
            Logger.getLogger(VoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SynthesisException ex) {
            Logger.getLogger(VoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(VoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        });
        
    }
}
