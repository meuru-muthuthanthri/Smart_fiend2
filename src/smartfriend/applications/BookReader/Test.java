/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.omg.CORBA.BAD_CONTEXT;
import smartfriend.MainFlow;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.util.general.Consts;

/**
 *    
 * @author Keshani
 */
public class Test {
    
    public static void main(String[] args) {
        try {
            try {
//            System.load(new File(".").getCanonicalPath() + "/lib/TextExtraction/liblept168.dll");
//            System.load(new File(".").getCanonicalPath() + "/lib/TextExtraction/libtesseract302.dll");
        } catch (Exception ex) {
            Logger.getLogger(MainFlow.class.getName()).log(Level.SEVERE, null, ex);
        }
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
