/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend;

import smartfriend.util.general.Camera;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import smartfriend.applications.scheduler.Scheduler;
import smartfriend.gui.GUIForm;
import smartfriend.handGesture.DisplayEngine;
import smartfriend.handGesture.HandDetector;
import smartfriend.handGesture.HandPoint;
import smartfriend.handGesture.SystemController;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Consts;

/**
 *
 * @author Meuru This Class handles all the communication with the components of
 * the hand gesture recognition
 */
public class MainFlow implements Runnable {

    private GUIForm gUIForm;
    private Camera camera;
    private DisplayEngine displayEngine;
    private HandDetector handDetector;
    private GraphicRenderer graphicRenderer;
    private SystemController systemController;
    private Scheduler scheduler;

    public MainFlow() {

        VoiceGenerator voice;
        SpeechRecognizer sp;

        try {
            System.load(new File(".").getCanonicalPath() + "/lib/OpenCV2410/opencv_java2410.dll");
        } catch (IOException ex) {
            Logger.getLogger(MainFlow.class.getName()).log(Level.SEVERE, null, ex);
        }

        gUIForm = new GUIForm();
        camera = new Camera(Consts.CAMERA_ID);

        graphicRenderer = new GraphicRenderer(gUIForm, camera);
        gUIForm.setVisible(true);
        displayEngine = new DisplayEngine(camera, gUIForm.getDisplyDimentions(), graphicRenderer);
        Mat initialImage = graphicRenderer.drawShapeOnImage(displayEngine.getInitialImage(), displayEngine.getBoundryPoints());
        handDetector = new HandDetector(displayEngine, graphicRenderer, initialImage);
        systemController = new SystemController(gUIForm.getGraphicsDevice(), displayEngine.getBoundryPoints());

        voice = VoiceGenerator.getVoiceGeneratorInstance();
        sp = startVoiceRecognition();
//        scheduler = new Scheduler();
//        new Thread(scheduler).start();

        new Thread(this).start();
    }

    public static void main(String[] args) {
        new MainFlow();
    }

    @Override
    public void run() {
        System.out.println("Started Hand Gesture Recognition Thread");
        HandPoint handpointer;
        BufferedImage screenImage;
        int boundryX = (int) Math.min(displayEngine.getBoundryPoints().get(0).x, displayEngine.getBoundryPoints().get(1).x);
        int boundryY = (int) Math.min(displayEngine.getBoundryPoints().get(0).y, displayEngine.getBoundryPoints().get(3).y);

        while (true) {
            //gUIForm.setOpacity(0.5f);
            screenImage = systemController.getSkewedScreenShot();

            //graphicRenderer.drawImageOnInfoPanel(screenImage, 1);
            handpointer = handDetector.getHandPoint(camera.captureSmallPhoto(), screenImage);
            graphicRenderer.drawPointerOnScreen(handpointer);
//            systemController.moveMousePointer(handpointer);
            if (handpointer.getState()) {
//                systemController.leftMouseClick(handpointer);
            }

        }
    }

    private SpeechRecognizer startVoiceRecognition() {
        SpeechRecognizer sp = SpeechRecognizer.getSpeechInstance();
        new Thread(sp).start();
        return sp;
    }

}
