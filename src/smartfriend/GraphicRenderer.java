/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import smartfriend.applications.BookReader.BookReaderGUI;
import smartfriend.applications.numberlearning.NumberApp;
import smartfriend.applications.scheduler.SchedulerGUI;
import smartfriend.applications.scheduler.SchedulerManagement;
import smartfriend.applications.userprofile.UserDetailManagement;
import smartfriend.gui.GUIForm;
import smartfriend.gui.HandGestureDisplayPanel;
import smartfriend.gui.MainScreen;
import smartfriend.gui.WelcomeScreen;
import smartfriend.gui.BoardGame;
import smartfriend.handGesture.FingerName;
import smartfriend.handGesture.HandPoint;
import smartfriend.util.general.Camera;
import smartfriend.util.general.Consts;

/**
 *
 * @author Meuru
 */
public class GraphicRenderer implements Runnable {

    private static GraphicRenderer gr;

    private static int X_OFFSET = 10;
    private static int Y_OFFSET = 30;
    private JFrame infoPanel;
    private Graphics2D infoPanelGraphics2D;
    private HandGestureDisplayPanel screenPanel;
    private Container panelContainer;
    private JPanel welcomeScreen, mainScreen, numberApp, boardGame, interactiveBookPanel, scheduler, userProfiles;
    private JPanel currentApplication;
//    private GUIForm basePanel;

    public static synchronized GraphicRenderer getInstance() {
        return gr;
    }

    public GraphicRenderer(GUIForm base, final Camera camera) {
        infoPanel = setUpInfoPanel();

        //        basePanel = base;
        infoPanelGraphics2D = (Graphics2D) infoPanel.getGraphics();

        panelContainer = base.getContentPane();

        welcomeScreen = new WelcomeScreen(this);
        mainScreen = new MainScreen(this);

        welcomeScreen.setVisible(false);
        mainScreen.setVisible(false);

        panelContainer.add(welcomeScreen, -1);
        panelContainer.add(mainScreen, -1);

        screenPanel = new HandGestureDisplayPanel();
        base.setGlassPane(screenPanel);
        base.getGlassPane().setVisible(true);

        gr = this;

    }

    public void startGraphicRendererThread() {
        new Thread(this).start();
        welcomeScreen.setVisible(true);

    }

    public void showMainScreen() {
        mainScreen.setVisible(true);
        panelContainer.remove(welcomeScreen);
    }
    
    public JPanel getCurrentApplication(){
        return currentApplication;
    }

    public void showScreen(String screenName) {
        JPanel panel;
        switch (screenName) {
            case Consts.BOARD_GAME:
                panel = new BoardGame();
                break;
            case Consts.INTERACTIVE_BOOK:
                panel = BookReaderGUI.getInstance();
                break;
            case Consts.NUMBERAPP:
                panel = new NumberApp();
                break;
            case Consts.SCHEDULER:
                panel = new SchedulerManagement();
                break;
            case Consts.USER_PROFILES:
                panel = new UserDetailManagement();
                break;
            default:
                panel = new JPanel();
                System.out.println("Cannot find the application to open");
                break;
        }
        panel.setVisible(true);
        panelContainer.add(panel, 0);
        panel.repaint();
        panel.revalidate();
        currentApplication = panel;
    }

    public void closeScreen() {
        panelContainer.remove(currentApplication);
    }

    private JFrame setUpInfoPanel() {
        JFrame infoPanel = new JFrame("Info Panel");
        infoPanel.getContentPane().setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        infoPanel.getContentPane().add(jPanel, BorderLayout.CENTER);
        infoPanel.setSize(1280, 800);
        infoPanel.setVisible(true);
        infoPanel.setLayout(new BorderLayout());

        return infoPanel;
    }

    public void wipeScreen() {
        screenPanel.getGraphics().setColor(Color.BLACK);
        screenPanel.getGraphics().fillRect(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
    }

    public void drawPointerOnScreen(HandPoint point) {
        screenPanel.drawPointer(point);
    }

    public void drawPointsOnScreen(ArrayList<Point> points) {
        if (points != null) {
            screenPanel.setHandPoints(points);
        }
    }

    public void drawPointsOnInfoPanel(Mat image, List<Point> points, Color color, int x, int y, int downScale) {
        infoPanelGraphics2D.drawImage(getImage(image), x + X_OFFSET, y + Y_OFFSET, image.width() / downScale, image.height() / downScale, null);
        for (Point pt : points) {
            infoPanelGraphics2D.setColor(color);
            infoPanelGraphics2D.fillOval(x + X_OFFSET + (int) pt.x / downScale - 5, y + Y_OFFSET + (int) pt.y / downScale - 5, 10, 10);
        }
    }

    public void drawImageOnInfoPanel(Mat image, int x, int y, int downScale) {
        drawImageOnInfoPanel(getImage(image), x, y, downScale);
    }

    public void drawImageOnInfoPanel(BufferedImage image, int x, int y, int downScale) {
        infoPanelGraphics2D.drawImage(image, x + X_OFFSET, y + Y_OFFSET, Consts.CAMERA_WIDTH / downScale, Consts.CAMERA_HEIGHT / downScale, null);
    }

    public void drawPoint(Point pt, Color color) {
        infoPanelGraphics2D.setColor(color);
        infoPanelGraphics2D.fillOval((int) pt.x - 5, (int) pt.y - 5, 20, 20);
    }

    public void drawShape(List<Point> points, List<Point> convexHull, Point cog, int distance, Color color, int x, int y, int downScale) {
        infoPanelGraphics2D.setColor(Color.YELLOW);
        infoPanelGraphics2D.fillRect(x, y, screenPanel.getSize().width / downScale, screenPanel.getSize().height / downScale);
        infoPanelGraphics2D.setColor(color);
        if (points.size() > 0) {
            int x1Points[] = new int[points.size()];
            int y1Points[] = new int[points.size()];
            for (int i = 0; i < points.size(); i++) {
                //g2d.fillOval(pt.x - 10, pt.y - 10, 20, 20);
                Point pt = points.get(i);
                x1Points[i] = x + (int) pt.x / downScale;
                y1Points[i] = y + (int) pt.y / downScale;
            }

            GeneralPath polygon
                    = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                            x1Points.length);
            polygon.moveTo(x1Points[0], y1Points[0]);

            for (int index = 1; index < x1Points.length; index++) {
                polygon.lineTo(x1Points[index], y1Points[index]);
            }
            polygon.closePath();
            infoPanelGraphics2D.fill(polygon);

            for (Point pt : points) {
                infoPanelGraphics2D.setColor(Color.CYAN);
                infoPanelGraphics2D.fillOval(x + (int) pt.x / downScale - 5, y + (int) pt.y / downScale - 5, 10, 10);
            }
            for (Point pt : convexHull) {
                infoPanelGraphics2D.setColor(Color.black);
                infoPanelGraphics2D.fillOval(x + (int) pt.x / downScale - 5, y + (int) pt.y / downScale - 5, 10, 10);
            }

            infoPanelGraphics2D.setColor(Color.RED);
            infoPanelGraphics2D.fillOval(x + (int) cog.x / downScale - 5, y + (int) cog.y / downScale - 5, 10, 10);
            infoPanelGraphics2D.drawOval(x + (int) cog.x / downScale, y + (int) cog.y / downScale, distance, distance);
        }
    }

    /**
     *
     * @param points List of points of the hand contour
     * @param convexHull Convex hull of the hand contour
     * @param cog Center of gravity
     * @param ditostance Size of the palm
     * @param fingerTips Finger tips points list
     * @param namedFingers Corresponding names of the finger tips
     * @param x x offset
     * @param y y offset
     * @param downScale down scaling factor
     */
    public void drawHandInfo(List<Point> points, List<Point> convexHull, Point cog, int distance, ArrayList<Point> fingerTips, ArrayList<FingerName> namedFingers, int x, int y, int downScale) {
        infoPanelGraphics2D.setColor(Color.YELLOW);
        infoPanelGraphics2D.fillRect(x, y, screenPanel.getSize().width / downScale, screenPanel.getSize().height / downScale);
        infoPanelGraphics2D.setColor(Color.pink);
        if (points.size() > 0) {
            int x1Points[] = new int[points.size()];
            int y1Points[] = new int[points.size()];
            for (int i = 0; i < points.size(); i++) {
                //g2d.fillOval(pt.x - 10, pt.y - 10, 20, 20);
                Point pt = points.get(i);
                x1Points[i] = x + (int) pt.x / downScale;
                y1Points[i] = y + (int) pt.y / downScale;
            }

            GeneralPath polygon
                    = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                            x1Points.length);
            polygon.moveTo(x1Points[0], y1Points[0]);

            for (int index = 1; index < x1Points.length; index++) {
                polygon.lineTo(x1Points[index], y1Points[index]);
            }
            polygon.closePath();
            infoPanelGraphics2D.fill(polygon);

            for (Point pt : points) {
                infoPanelGraphics2D.setColor(Color.CYAN);
                infoPanelGraphics2D.fillOval(x + (int) pt.x / downScale - 5, y + (int) pt.y / downScale - 5, 10, 10);
            }
            for (Point pt : convexHull) {
                infoPanelGraphics2D.setColor(Color.black);
                infoPanelGraphics2D.fillOval(x + (int) pt.x / downScale - 5, y + (int) pt.y / downScale - 5, 10, 10);
            }

            infoPanelGraphics2D.setColor(Color.RED);
            infoPanelGraphics2D.fillOval(x + (int) cog.x / downScale - 5, y + (int) cog.y / downScale - 5, 10, 10);
            infoPanelGraphics2D.drawOval(x + (int) cog.x / downScale, y + (int) cog.y / downScale, distance, distance);

            for (int i = 0; i < fingerTips.size(); i++) {
//                System.out.println("printing " + namedFingers.get(i));
                Point pt = fingerTips.get(i);
                if (namedFingers.get(i) == FingerName.UNKNOWN) {
                    infoPanelGraphics2D.setPaint(Color.RED);   // unnamed finger tip is red
                    infoPanelGraphics2D.drawOval(x + (int) pt.x / downScale - 8, y + (int) pt.y / downScale - 8, 16, 16);
                    infoPanelGraphics2D.drawString("" + i, x + (int) pt.x / downScale, y + (int) pt.y / downScale - 10);   // label it with a digit
                } else {   // draw yellow line to the named finger tip from COG
                    infoPanelGraphics2D.setPaint(Color.YELLOW);
                    infoPanelGraphics2D.drawLine(x + (int) cog.x / downScale, y + (int) cog.y / downScale, x + (int) pt.x / downScale, y + (int) pt.y / downScale);

                    infoPanelGraphics2D.setPaint(Color.GREEN);   // named finger tip is green
                    infoPanelGraphics2D.drawOval(x + (int) pt.x / downScale - 8, y + (int) pt.y / downScale - 8, 16, 16);
                    infoPanelGraphics2D.drawString(namedFingers.get(i).toString().toLowerCase(), x + (int) pt.x / downScale, y + (int) pt.y / downScale - 10);
//                    System.out.println("@@@@ " + namedFingers.get(i));
                }
            }
        }
    }

    private BufferedImage getImage(Mat matImage) {
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".jpg", matImage, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                screenPanel.repaint();
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(HandGestureDisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Mat drawShapeOnImage(Mat initialImageMat, ArrayList<Point> points, Color color) {
        BufferedImage bi = new BufferedImage(initialImageMat.width(), initialImageMat.height(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        BufferedImage bufferedImage = getImage(initialImageMat);
        int x1Points[] = new int[points.size()];
        int y1Points[] = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            //g2d.fillOval(pt.x - 10, pt.y - 10, 20, 20);
            Point pt = points.get(i);
            x1Points[i] = (int) pt.x;
            y1Points[i] = (int) pt.y;
        }

        GeneralPath polygon
                = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        }
        polygon.closePath();
        g.drawImage(bufferedImage, 0, 0, null);
        g.setColor(color);
        g.fill(polygon);

        return convertToMat(bi);
    }

    public Mat convertToMat(BufferedImage bufferedImage) {
        BufferedImage convertedImg;
        Mat mat;
        convertedImg = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        convertedImg.getGraphics().drawImage(bufferedImage, 0, 0, null);
        byte[] pixels = ((DataBufferByte) convertedImg.getRaster().getDataBuffer()).getData();
        mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    public static void main(String[] args) {
        GUIForm gUIForm = new GUIForm();
        GraphicRenderer gr = new GraphicRenderer(gUIForm, null);
        gr.startGraphicRendererThread();
        gUIForm.setVisible(true);
        gUIForm.setLocation(new java.awt.Point(0, 0));

    }
}
