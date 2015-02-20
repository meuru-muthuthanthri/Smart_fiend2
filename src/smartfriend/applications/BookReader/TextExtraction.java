/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import smartfriend.Applications.BookReader.Tess4j.net.sourceforge.tess4j.*;
import smartfriend.Applications.BookReader.Tess4j.net.sourceforge.tess4j.Tesseract;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import smartfriend.util.general.Camera;
import smartfriend.util.general.Consts;
import smartfriend.util.general.MainConfiguration;

public class TextExtraction {

    private Mat template;
    private boolean isWordShowing;
    private Camera cam;
    private String currentDicPath;

    public TextExtraction() {
        try {
            System.load(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("opencv_java2410"));

            template = Highgui.imread(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("TempImage"));
            isWordShowing = false;
            cam = new Camera(Consts.CAMERA_ID_BOOKREADER);
            currentDicPath = MainConfiguration.getCurrentDirectory();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int[] getFingerPoint(Mat sourceImage, int match_method) {

        // Create the result matrix
        Mat image = sourceImage;
        int result_cols = sourceImage.cols() - template.cols() + 1;
        int result_rows = sourceImage.rows() - template.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        try {
            // Do the Matching and Normalize
            Imgproc.matchTemplate(image, template, result, match_method);
            Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Localizing the best match with minMaxLoc
        MinMaxLocResult mmr = Core.minMaxLoc(result);

        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }
        int xReal = (int) (matchLoc.x + (template.cols() / 2 - 0.5));
        int yReal = (int) (matchLoc.y + (template.rows() / 2 - 0.5));
        Rect rectCrop = new Rect(xReal, yReal, template.rows(), template.cols());
        Core.rectangle(sourceImage, new Point(xReal, yReal), new Point(xReal + template.cols(), yReal + template.rows()), new Scalar(255, 0, 0), 3);
        // Highgui.imwrite("D:/images/temOnImage.jpg", sourceImage);
        // System.out.println("getFingerPoint:template matching point.x,y " + xReal + "," + yReal);

        return new int[]{xReal, yReal};
    }

    public Mat getSelectedWord(Mat source, String outFile, int match_method) {
        try {

            Mat sourceImage = source;
            double angle = 0.0;
            int threshold = 195;

            //rotate source image by 180 degrees
            sourceImage = rotatedImage(sourceImage, 180, 1.0);

            // get skew angle 
            angle = getSkewAngle(sourceImage);

            // deskew  source image
            sourceImage = rotatedImage(sourceImage, angle * 180 / Math.PI, 1.0);

            // get finger point cordinates
            int cordinates[] = getFingerPoint(sourceImage, match_method);

            // check whether there is real points
            if (cordinates.length == 0) {
                return null;
            }

            // assign cordinates values
            int xReal = cordinates[0];
            int vReal = (int) xReal;
            int zReal = (int) xReal;
            int yReal = cordinates[1];

            // get closest line y cordination
            int yClose = getClosetLineY(xReal, yReal, sourceImage);
            yClose = yClose - 10;

            int leftWhiteSpace = 0;
            int rightWhiteSpace = 0;

            // convert to grey imgage
            Mat grey = new Mat();
            Imgproc.cvtColor(sourceImage, grey, Imgproc.COLOR_BGR2GRAY);

            // convert mat to Buffered image
            MatOfByte byteMat = new MatOfByte();
            Highgui.imencode(".jpg", grey, byteMat);
            byte[] bytes = byteMat.toArray();
            InputStream in = new ByteArrayInputStream(bytes);
            BufferedImage buffImage = ImageIO.read(in);

            // get word left ending points
            while (vReal != 0) {

                int pixelIntencity = buffImage.getRGB(vReal, yClose) & 0xFF;
                // System.out.println("left" + pixelIntencity);

                if (pixelIntencity >= threshold) {

                    leftWhiteSpace++;
                }
                if (pixelIntencity < threshold) {
                    leftWhiteSpace = 0;
                }
                vReal--;
                if (leftWhiteSpace >= 15) {
                    break;
                }

            }
         //   System.out.println("xreal" + xReal);

            // get word right ending points
            while ((zReal + 1) < grey.cols()) {
                zReal++;
                int pixelIntencity = buffImage.getRGB(zReal, yClose) & 0xFF;
                   System.out.println("right" + pixelIntencity);
                if (pixelIntencity >= threshold) {
                    rightWhiteSpace++;
                }
                if (pixelIntencity < threshold) {
                    rightWhiteSpace = 0;
                }
                if (rightWhiteSpace >= 15) {
                    break;
                }
            }
            // System.out.println("zreal" + zReal);
            // initialized Y cordinate of top point of crop word
            int upperYBound = yClose - 40;
            // initialized crop word height
            int height = 50;

            // check whether upperbound exceed image top boundry
            if (upperYBound < 0) {
                upperYBound = 0;
            }

            // check whether height exceed image bottom boundry
            if (upperYBound + 50 > sourceImage.height()) {
                height = sourceImage.height() - upperYBound;
            }

            // crop the image 
            Rect rectCrop = new Rect(vReal, upperYBound, zReal - vReal, height);
            Mat imCrop = new Mat(sourceImage, rectCrop);
            //Imgproc.threshold(imCrop, imCrop, 20, 255, Imgproc.THRESH_BINARY);
            Highgui.imwrite("D:/images/result.png", imCrop);
            return imCrop;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /*Crop 340X100 area from the image */
    public Mat cropSelectedArea(Mat source, Point point) {

        int x;
        int y;
        y = (int) point.y;
        x = (int) point.x;

        // if y is less than 150, get top starting y cordinate 0 
        try {
            if (y < 150) {
                y = 0;
                // System.out.println("y<100" + y);
            } else if ((source.height() - y) < 150) {
                y = y - 150 - (150 - (source.height() - y));
//                System.out.println(source.height() - y);
//                System.out.println("y>100" + y);
            } else {
                y = y - 150;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (x < 170) {
                x = 0;
            } else if (source.width() - x < 170) {
                x = x - 170 - (170 - (source.width() - 340));
            } else {
                x = x - 170;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Rect rectCrop = new Rect(x, y, 340, 300);
        Mat cropImage = new Mat(source, rectCrop);
        Highgui.imwrite("D:/images/Acrop.jpg", cropImage);

        return cropImage;

    }

    public Mat rotatedImage(Mat image, double angle, double scale) {

        MatOfByte byteMat = new MatOfByte();
        Highgui.imencode(".jpg", image, byteMat);
        byte[] bytes = byteMat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage img = null;
        try {
            img = ImageIO.read(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        javaxt.io.Image temimage = new javaxt.io.Image(img);
        temimage.rotate(angle);
        new javaxt.io.Image(temimage.getBufferedImage()).saveAs("D:/images/CrotatedJavaXT.jpg");
        Mat rotate = Highgui.imread("D:/images/CrotatedJavaXT.jpg");

        return rotate;

    }

    public double getSkewAngle(Mat Inputimage) {
        Mat image = new Mat();
        Mat lines = new Mat();
        Mat gray = new Mat();
        double angle = 0.0;
        double tempAngle = 0.0;
        int numAngles = 0;

        try {
        } catch (Exception e) {
        }

        Imgproc.erode(Inputimage, image, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 6)));
        Imgproc.dilate(image, image, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 6)));
        Imgproc.Canny(image, gray, 60, 40);
        Imgproc.HoughLinesP(gray, lines, 1, Math.PI / 180, 100, 15, 20);

        // System.out.println("num of lines" + lines.cols());
        try {
            for (int r = 0; r < lines.cols(); r++) {
                double[] vec = lines.get(0, r);
                double x1 = vec[0],
                        y1 = vec[1],
                        x2 = vec[2],
                        y2 = vec[3];
                Point start = new Point(x1, y1);
                Point end = new Point(x2, y2);
                Core.line(gray, start, end, new Scalar(255, 0, 0), 1);
                //  System.out.println(Math.atan2(y2 - y1, x2 - x1));
                tempAngle = Math.atan2(y2 - y1, x2 - x1);
                if (tempAngle < 0.9 && tempAngle > -0.9) {
                    angle += tempAngle;
                    numAngles++;
                }
            }
        } catch (Exception e) {
        }

        Highgui.imwrite("D:/images/DangleCanny.jpg", gray);

        if (angle != 0) {
            angle = -1 * angle / numAngles;
        }
        System.out.println("angle:" + angle);

        return angle;

    }

    public int getClosetLineY(int x, int y, Mat image) {
        int closestY = 0;
        double[] temp = null;
        Point start = null;
        Point end = null;
        int ydif = 1000000;
        int xdif = 0;
        Mat lines = new Mat();
        Mat gray = new Mat();
        try {
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
            for (int i = 0; i < 5; i++) {
                Imgproc.erode(gray, gray, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 6)));
                Imgproc.dilate(gray, gray, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 6)));

            }
            Imgproc.Canny(gray, gray, 60, 40);
            Imgproc.HoughLinesP(gray, lines, 1, Math.PI / 180, 50, 10, 25);

            //  System.out.println(lines.cols());
            for (int r = 0; r < lines.cols(); r++) {
                temp = lines.get(0, r);
//
                if ((temp[1] < y || temp[3] < y) && (ydif > y - temp[1] || ydif > y - temp[3])) {
                    double[] vec = temp;

                    if (temp[1] < temp[3] && (temp[3] - temp[1]) > 5) {
                        continue;
                    }
                    if (temp[3] < temp[1] && (temp[1] - temp[3]) > 5) {
                        continue;
                    }
                    double x1 = vec[0],
                            y1 = vec[1],
                            x2 = vec[2],
                            y2 = vec[3];

                    if (temp[3] < temp[1]) {
                        closestY = (int) temp[3];
                    } else {
                        closestY = (int) temp[1];
                    }
                    ydif = (int) (y - closestY);
                    start = new Point(x1, y1);
                    end = new Point(x2, y2);

                    Core.line(gray, start, end, new Scalar(255, 0, 0), 3);
                    //   System.out.println("lines x1: " + x1 + "  y1: " + y1 + "  x2: " + x2 + "  y2: " + y2);
                }
            }
            //System.out.println(closestY);
            Core.line(gray, start, end, new Scalar(255, 0, 0), 3);
            Highgui.imwrite("D:/images/Ecanny.jpg", gray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return closestY;
    }

    public String getImageText(String filePath) {

        File imageFile = new File(filePath);
        Tesseract instance = Tesseract.getInstance();
        String result = null;
        try {
            result = instance.doOCR(imageFile);
            System.out.println(result);

        } catch (TesseractException e) {
            System.err.println("error" + e.getMessage());
        }
        return result;
    }

    public String getImageText(Mat image) {
        String result = null;

        try {

            // convert mat to Buffered image
            MatOfByte byteMat = new MatOfByte();
            Highgui.imencode(".jpg", image, byteMat);
            byte[] bytes = byteMat.toArray();
            InputStream in = new ByteArrayInputStream(bytes);

            BufferedImage img = ImageIO.read(in);
            Tesseract instance = Tesseract.getInstance();
            result = instance.doOCR(img);
            System.out.println(result);

        } catch (Exception ex) {
            Logger.getLogger(TextExtraction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String startTextExtracter() {
        try {
            // System.out.println("st");
            System.load(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("opencv_java2410"));
            Mat frame= cam.capturePhoto();
            for (int i = 0; i < 3; i++) {
                frame = cam.capturePhoto();
            }
           // Highgui.imwrite("D:/images/test/" + System.currentTimeMillis() + ".jpg", frame);

            Mat crop = getSelectedWord(frame, "D:/images/result.jpg", Imgproc.TM_CCORR_NORMED);
            String word = getImageText(crop).replaceAll("(?m)^[ \t]*\r?\n", "");
            word = word.trim();
            word = word.replaceAll("[^a-zA-Z]", "");

            System.out.println("word  " + word);

            /*
             gave     QQVE, save , QaVE, Save
             suited   suited 
             else       else
             anything anythinQ
             velvet   vel_vet
             */
            return word;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
