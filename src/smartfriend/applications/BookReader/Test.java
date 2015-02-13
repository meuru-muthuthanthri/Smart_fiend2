/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.Applications.BookReader;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.omg.CORBA.BAD_CONTEXT;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.util.general.Consts;

/**
 *    
 * @author Keshani
 */
public class Test {
    
    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("sun.arch.data.model") ); 
            
             System.out.println(1e9);
            JFrame f = new JFrame();
            f.setUndecorated(false);
            f.setSize(1366, 768);
            //     f.setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
            JPanel bookGui = BookReaderGUI.getInstance();
            bookGui.setVisible(true);
            f.add(bookGui);
            
            f.setVisible(true);
//            SpeechRecognizer sp= SpeechRecognizer.getSpeechInstance();
//            new Thread(sp).start();
//            BookReader rd = new BookReader();
//            rd.createMarker("D:/marker192140.jpg", 192, 140);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
