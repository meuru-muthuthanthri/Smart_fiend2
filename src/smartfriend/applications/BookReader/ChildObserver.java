/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.Applications.BookReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.MatOfByte;
import smartfriend.util.general.Camera;

public class ChildObserver  {

    private String message;
    private MotionDetection test;
    private Camera camera;
    private long lastRunTime;
    static final long threshold;

    static{
             threshold = 10000;
    }
    public ChildObserver(int width, int height) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        test = new MotionDetection(width, height);
   
    }

    public String getMessage() {
        return message;
    }

    public void setLastRunTime(long lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public long getLastRunTime() {
        return lastRunTime;
    }

    public void motionDetection(Mat frame) {
        test.updateMHI(frame, 60);
    }

    public long calMotionLevel() {
        long h = 0;
        long startTime = System.currentTimeMillis()-this.lastRunTime;
        try {
            // convert mat to Buffered image
            MatOfByte byteMat = new MatOfByte();
            Highgui.imencode(".jpg", test.getMhi(), byteMat);
            byte[] bytes = byteMat.toArray();
            InputStream in = new ByteArrayInputStream(bytes);

            BufferedImage img = ImageIO.read(in);

            // loop over image and detect changes
            for (int j = 0; j < test.getMhi().height(); j++) { // height
                for (int i = 0; i < test.getMhi().width(); i++) { // width
                    // check if at pixel (j,i) intensity is equal to 255
                    if ((img.getRGB(i, j) & 0xFF) >30) {
                        h++;
                    }
                }
            }
             FileWriter fw = new FileWriter("data.txt", true);

        fw.write(startTime + " " + h + "\n");
        fw.write("\r\n");
        fw.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return h;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        ChildObserver ch = new ChildObserver(700, 690);
        //ch.run();
    }
}
