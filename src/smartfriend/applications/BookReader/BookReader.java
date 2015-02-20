package smartfriend.applications.BookReader;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import java.awt.image.BufferedImage;
import java.util.Observable;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import smartfriend.Applications.BookReader.aruco.Marker;
import smartfriend.Applications.BookReader.aruco.MarkerDetector;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.opencv.imgproc.Imgproc;
import smartfriend.util.general.MainConfiguration;
import java.util.Observer;
import java.util.Vector;
import javax.swing.JFrame;
import smartfriend.util.general.MediaContentPlayer;
import smartfriend.gui.TalkingAgent;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.util.general.Camera;
import smartfriend.util.general.Consts;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

public class BookReader implements Observer {

    // parameters
    private boolean permission;// permission to play the video
    private boolean exit;//  application exit condition
    private String currentDir;
    private MediaContentPlayer mediaPlayer;
    private JFrame frame;
    private Camera cam;
    private Book book;
    private volatile boolean run;

    public BookReader() {
        try {

            currentDir = MainConfiguration.getCurrentDirectory();
            System.load(currentDir + MainConfiguration.getInstance().getProperty("opencv_java2410"));
            cam = new Camera(2);
            exit = false;// application start
//            permission = true;
            run = true; // use to check whether get new word feature on or off
            SpeechRecognizer.getSpeechInstance().addObserver(this);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean getRun() {
        return run;
    }

// interact with the book
    public Book CreateBook() {
        book = new Book();
        Mat frame = cam.capturePhoto();
        String bkName = "TestBook";
//        BufferedImage bufImg = Common.toBufferedImage(frame);
//        Map hintMap = new HashMap();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        String QRText = QRMarker.readQRCode(bufImg, "UTF-8", hintMap);

//        if (!QRText.isEmpty()) {
//            bkName = QRText;
//        } else {
//            System.out.println("QR Code is not detected");
//        }
        book.setBkName(bkName);
        return book;

    }
    /*start reading process. System try to find markers in the book*/

    public void startReading() {
        Mat frame = new Mat();
        int priviousID = 1025;
        boolean play = false;
        int observerRuns = 0;
        long currentMotionLevel = 0;

        try {

            ChildObserver observer = new ChildObserver(640, 480);
            observer.setLastRunTime(System.currentTimeMillis());
            // loop until user wants to exit
            while (!exit) {
                //check whether word extractor is started or not. If it is started sleep 100 milisecond
                while (!run) {
                    Thread.sleep(5000);
                }

                //check whether media player is open or not.
                if (mediaPlayer != null && mediaPlayer.isOpen()) {
                    //System.out.println("media player open");
                    continue;
                }
                //check whether camera is open or not
                if (!cam.isOpen()) {
                    // System.out.println("no cam");
                    throw new Exception("webcam is not opened");
                }

                // get a frame from camera
                frame = cam.capturePhoto();

                // If frame is empty continue
                if (frame.empty()) {
                    continue;
                }

//
//                if (System.currentTimeMillis() - observer.getLastRunTime() >= 30000) {
//
//                     observer.motionDetection(frame);
//                     observerRuns++;
//                    if (observerRuns == 2) {
//                       // System.out.println("4 not exceed ");
//                    
//                        currentMotionLevel = observer.calMotionLevel();
//                     //   System.out.println("4 exceed" + observer.calMotionLevel());
//                        if (currentMotionLevel < ChildObserver.threshold) {
//                            BookReaderGUI.getInstance().showAgentHappyMessage("Hay are u sleeping. I cannot see you moving");
//                        }
//                        observerRuns = 0;
//                    }
//                    observer.setLastRunTime(System.currentTimeMillis());
//
//                }
                // detect markers on the frame
                Vector<Marker> detMarkers = detectMarkers(frame, 0.01f);

                if (Consts.SAVE_IMAGE_INTERACTIVE_BOOK) {
                    Highgui.imwrite("D:/test.jpg", frame);
                }

                // if there is no marker continue
                if (detMarkers.isEmpty()) {
                    System.out.println("no marker");
                    continue;
                }

                // if there is no new marker continue
                if (priviousID == detMarkers.firstElement().getMarkerId()) {
                    continue;
                }

                // if new marker find set it to previousID
                if (priviousID != detMarkers.firstElement().getMarkerId()) {
                    priviousID = detMarkers.firstElement().getMarkerId();
                }

                // get the permission from child
                BookReaderGUI.getInstance().showAgentHappyMessage("Hi, I have a video. Shall I show it to you");

                // wait until user responce
                Thread.sleep(1000);
//                while (!permission) {
//                }

                // remove talking agent 
                BookReaderGUI.getInstance().removeAgent();
                permission = true;
                // check whether child give permission to run the video
                if (permission) {

                    // get ARMarker object relevent to detected marker
                    ARMarker mk = (ARMarker) (book.getMarkerIDMap().get(detMarkers.firstElement().getMarkerId()));
                    if ((mediaPlayer != null) && (mediaPlayer.getPlayer().isPlaying())) {
                        mediaPlayer.closePlayer();
                    }
                    playVideo(mk.getVideoPath());
                    permission = false;
                } else {
                    BookReaderGUI.getInstance().showAgentHappyMessage("Hi, If you want to watch the video say PLAY");
                }
//
//
                System.out.println("sleep");
                //  Thread.sleep(5000);
            }

        } catch (Exception e) {
        }

    }
    /*detect markers in the book*/

    public Vector<Marker> detectMarkers(Mat image, float sizeMeteres) {
        Vector<Marker> detMarkers = new Vector<Marker>();
        MarkerDetector det = new MarkerDetector();
        det.detect(image, detMarkers, sizeMeteres, image);
        return detMarkers;

    }

    /*play video in given path*/
    public boolean playVideo(String file_name) {
        boolean play = true;
        try {
            System.out.println("playing " + file_name);
            mediaPlayer = new MediaContentPlayer();
            System.out.println("@@@ media player loaded");
            mediaPlayer.playVideo(file_name, BookReaderGUI.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
            play = false;
        }
        return play;

    }
    /*create markers by giving size and id number*/

    public void createMarker(String savePath, int id, int size) {

        Mat marker = Marker.createMarkerImage(id, size);
        Imgproc.cvtColor(marker, marker, Imgproc.COLOR_GRAY2RGBA, 4);
        Highgui.imwrite(savePath, marker);

    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.closePlayer();
        }
    }

    public void Exit() {
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg.equals("play video")) {
            permission = true;

            if (arg.equals("close")) {
                if (mediaPlayer != null) {
                    stopMediaPlayer();
                }
            }

            if (arg.equals("exit")) {
                this.Exit();
            }
            //if (media player dosent start)
            // play video
            //     System.out.println("get");

        }

    }
}
