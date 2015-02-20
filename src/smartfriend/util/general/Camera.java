/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.util.general;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import static org.opencv.highgui.Highgui.*;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author
 */
public class Camera {

    private int cameraID;
    private VideoCapture webcam;

    /**
     * This class controls the camera and related actions
     *
     * @param id Camera ID
     */
    public Camera(int id) {
        cameraID = id;
        webcam = new VideoCapture(cameraID);
        webcam.open(cameraID);
        runCamera();        // allow camera to adjust itself
        System.out.println("Camera " + cameraID + " is ready");
    }

    public Dimension getCameraDimension() {
        Size photoSize = capturePhoto().size();
        return new Dimension((int) photoSize.width, (int) photoSize.height);
    }

    /**
     * Capture the photo from camera
     *
     * @return Captured frame as
     */
    public Mat capturePhoto() {
        Mat frame = new Mat();
        if (webcam.isOpened()) {
            webcam.read(frame);
        }
        return frame.clone();
    }

    public Mat captureSmallPhoto() {
        Mat smallImage = new Mat(new Size(Consts.CAMERA_HALF_WIDTH, Consts.CAMERA_HALF_HEIGHT), 3);
        Imgproc.resize(capturePhoto(), smallImage, smallImage.size());
        return smallImage;
    }

    public boolean isOpen() {
        return webcam.isOpened();
    }

    public int getVideoWidth() {
//        System.out.println( webcam.get(CV_CAP_PROP_FRAME_WIDTH));
        return (int) webcam.get(CV_CAP_PROP_FRAME_WIDTH);

    }

    public int getVideoHeight() {
//         System.out.println( webcam.get(CV_CAP_PROP_FRAME_HEIGHT));
        return (int) webcam.get(CV_CAP_PROP_FRAME_HEIGHT);
    }

    private void runCamera() {
        for (int i = 0; i < 30; i++) {
            capturePhoto();
        }
    }

//    public void saveImage(Mat image, String name) {
//        Highgui.imwrite("D:/FYP/test/" + name + ".jpg", image);
//    }
    public static void saveImage(Mat image, String name) {
        Highgui.imwrite("D:/FYP/test/" + name + ".jpg", image);
    }

    public void takeSaveImage() {
        System.out.println("#### saving");
        saveImage(capturePhoto(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
    }
}
