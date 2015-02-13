package smartfriend.Applications.BookReader;

import java.io.IOException;
import smartfriend.util.general.MainConfiguration;
import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.opencv.highgui.Highgui;
import static org.opencv.core.Core.*;
import static org.opencv.highgui.Highgui.*;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.video.Video.*;

public class MotionDetection {

    private int videoHeight, videoWidth;
    private int last = 0;
    private VideoCapture capture;
    private int camera;
    //MHI, orientation,valid orientation mask,motion segmentation map
    private Mat motion, mhi;
    // number of cyclic frame buffer used for motion detection
    // (should, probably, depend on FPS)
    private final int N = 4;
    int i;

    public MotionDetection(int width, int height) {

        try {
            System.load(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("opencv_java2410"));
            videoHeight= height;
            videoWidth = width;
            setup();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
    }

    public Mat getMhi() {
        return mhi;
    }
    Mat[] buf;//ring iamge buffer,
    Size size;
    double magnitude, startTime = 0;

    public void setup() {
        size = new Size(videoWidth, videoHeight);

        buf = new Mat[N];
        for (int i = 0; i < N; i++) {
            buf[i] = Mat.zeros(size, CvType.CV_8UC1);
        }
        mhi = Mat.zeros(size, CvType.CV_32FC1);
        startTime = System.nanoTime();
    }

    public void updateMHI(Mat image, int MHI_DURATION) {

        if (image.empty()) {
            return;
        }

        getDifferenceOfImages(image, 35, MHI_DURATION);

    }

//Parameters
// img - input video frame
// dst - resultant motion picture
    private void getDifferenceOfImages(Mat img, int diff_threshold, int MHI_DURATION) {
        double timestamp = (System.nanoTime() - startTime) / 1e9;
       
        
        System.out.println(timestamp);
        int idx2;
        Mat silh;
        cvtColor(img, buf[last], COLOR_BGR2GRAY);// convert frame to grayscale
        idx2 = (last + 1) % 2; // index of (last - (N-1))th frame
        last = idx2;
        i++;
        if (i >= 2) {

            silh = buf[3];
            // buf[idx1]=gray image of img
            absdiff(buf[0], buf[1], silh);//get the difference between frames
            threshold(silh, silh, diff_threshold, 255, THRESH_BINARY);
            updateMotionHistory(silh, mhi, timestamp, MHI_DURATION);
           // Highgui.imwrite("D:/mhi.jpg", mhi);
        }

    }
}
