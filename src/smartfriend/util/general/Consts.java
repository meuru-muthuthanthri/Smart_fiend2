/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.util.general;

/**
 *
 * @author Meuru
 */
public final class Consts {

    //Disply Constants
    public static final int GRAPHIC_DIVICE_NO = 1;
    public static final int SCREEN_WIDHT = 1366;
    public static final int SCREEN_HEIGHT = 768;
//    public static final int SCREEN_WIDHT = 1280;
//    public static final int SCREEN_HEIGHT = 1024;
    //Hand Gesture Recognition Constants
    public static final int CAMERA_ID = 1;
    public static final int CAMERA_ID_BOOKREADER = 1;
    public static final int CAMERA_WIDTH = 640;
    public static final int CAMERA_HEIGHT = 480;
    public static final int CAMERA_HALF_WIDTH = 320;
    public static final int CAMERA_HALF_HEIGHT = 240;
    //image locations
    private static final String IMAGE_SOURCE_DIRECTORY = "src\\smartfriend\\resources\\images\\main\\";
    public static final String WELCOME_IMAGE = IMAGE_SOURCE_DIRECTORY + "welcome.jpg";
    public static final String MAIN_IMAGE = IMAGE_SOURCE_DIRECTORY + "main.jpg";
    public static final String WRITE_APP_IMAGE = IMAGE_SOURCE_DIRECTORY + "writeApp.jpg";
    public static final String KITE_IMAGE = IMAGE_SOURCE_DIRECTORY + "kite.png";
    public static final String ENTER_ICON = IMAGE_SOURCE_DIRECTORY + "enter_icon.png";
    public static final String EXIT_ICON = IMAGE_SOURCE_DIRECTORY + "exit_icon.png";
    public static final String LETTERS_ICON = IMAGE_SOURCE_DIRECTORY + "letters_icon.png";
    public static final String NUMBER_ICON = IMAGE_SOURCE_DIRECTORY + "number_icon.png";
    public static final String INTERACTIVE_BOOK_ICON = IMAGE_SOURCE_DIRECTORY + "interactiveBook_icon.png";
    public static final String SCEDULAR_ICON = IMAGE_SOURCE_DIRECTORY + "scheduler_icon.png";
    public static final String PROFILE_ICON = IMAGE_SOURCE_DIRECTORY + "profile_icon.png";
    public static final String PALM_BACKGROUND_IMAGE = IMAGE_SOURCE_DIRECTORY + "palm_background.jpg"; 
    public static final String PALM_SCANNER_IMAGE = IMAGE_SOURCE_DIRECTORY + "palm_scanner.jpg";
    
    //sounds
    public static final String MOUSEOVER_SOUND = "resources\\music\\mouse1.wav";
    public static boolean saveImage = false;
    public static boolean GRAPHICAL_DEBUG = true;
    public static boolean INDEX_ONLY = true;
    public static boolean SAVE_IMAGE_INTERACTIVE_BOOK = true;
    public static boolean TALK = true;
    public static boolean WITHOUT_SEGMENTATION = true;
    
    //applications
    public static final String WRITE_APP = "WriteApp";
    public static final String MAIN_SCREEN = "MainScreen";
    public static final String INTERACTIVE_BOOK = "InteractiveBook";
    public static final String NUMBERAPP = "NumberApp";
    public static final String SCHEDULER = "Scheduler";
    public static final String USER_PROFILES = "User Profiles";
    public static final String PALM_RECOGNIZER = "Palm Reader";
}
