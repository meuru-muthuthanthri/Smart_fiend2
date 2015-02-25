/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BookReaderGUI.java
 *
 * Created on Nov 12, 2014, 9:57:54 AM
 */
package smartfriend.applications.BookReader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import smartfriend.gui.Button;
import smartfriend.gui.TalkingAgent;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Keshani
 */
public class BookReaderGUI extends JPanel implements ActionListener {

    /**
     * Creates new form BookReaderGUI
     */
    private BookReader bkReader;
    private String currentDicPath;
    private Book book;
    private TalkingAgent agent;
    private static BookReaderGUI instance = null;
    private Thread bookreader;
    private TextExtraction textExtractor;
    private JButton exitButton;
    private JButton startReading;
    private JButton getMeaning;
    private int screenWidth;
    private int screenHeight;
    private JPanel panel;
    private WordDictionary dictionary;

    private BookReaderGUI() throws IOException {

        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        dictionary = new WordDictionary();
        agent = new TalkingAgent();
        currentDicPath = MainConfiguration.getCurrentDirectory();
        bkReader = new BookReader();
        textExtractor = new TextExtraction();

        //get the screen size
        Toolkit tk = Toolkit.getDefaultToolkit();
        screenHeight = ((int) tk.getScreenSize().getHeight());
        screenWidth = ((int) tk.getScreenSize().getWidth());

        // load background image
        BufferedImage image = ImageIO.read(new File(currentDicPath + "/resources/images/bookreader/bookImage3.jpg"));
        BufferedImage resizedImage = resize(image, screenWidth, screenHeight);

        // initialize buttons 
        JLabel backgroundImage = new JLabel(new ImageIcon(resizedImage));

        exitButton = new Button("Exit", Color.decode(Colors.RED), Color.decode(Colors.DULL_PINK), 200, 200, Consts.BACK_ICON);;
        startReading = new Button("Start", Color.decode(Colors.GREEN), Color.decode(Colors.DULL_PINK), 200, 200, Consts.PLAY_ICON);
        getMeaning = new Button("Meaning", Color.decode(Colors.DULL_ORANGE), Color.decode(Colors.DULL_PINK), 200, 200, Consts.MEANING_ICON);

        // remove the layout from the panel
        this.setLayout(null);
        backgroundImage.setBounds(0, 0, screenWidth, screenHeight);

        exitButton.setText("Exit");
//        exitButton.setBackground(Color.green);
        exitButton.setBounds(100, 100, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
        exitButton.addActionListener(this);

        startReading.setText("Start");
//        startReading.setBackground(Color.green);
        startReading.setBounds(900, 100, 200, 200);
        startReading.addActionListener(this);

        getMeaning.setText("Meaning");
//        getMeaning.setBackground(Color.green);
        getMeaning.setBounds(100, 400, 200, 200);
        getMeaning.addActionListener(this);
        getMeaning.setVisible(false);

        this.add(exitButton);
        this.add(startReading);
        this.add(getMeaning);
        this.add(backgroundImage, -1);
    }

    public static synchronized BookReaderGUI getInstance() {
        if (instance == null) {
            try {
                instance = new BookReaderGUI();
            } catch (IOException ex) {
                Logger.getLogger(BookReaderGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Start":
                try {

                    System.out.println("BookReaderGUI, actionPerformed method, Start Reading butoon press");
                    if (agent.isShowing()) {
                        agent.removeAgent();
                    }
                    book = bkReader.CreateBook();
                    if (book.getBkName() != null) {
                        System.out.println(currentDicPath + "/resources/booksXMLs/" + book.getBkName() + ".xml");
                        book.createBooKMarkerList(currentDicPath + "/resources/booksXMLs/" + book.getBkName() + ".xml");
                        bookreader = new Thread() {

                            public void run() {
                                bkReader.startReading();
                            }
                        };
                        getMeaning.setVisible(true);
                        startReading.setVisible(false);
                        this.revalidate();
                        this.repaint();
                        bookreader.start();

                    }
                } catch (Exception ex) {

                    showAgentHappyMessage("Oops ! Sorry. Something went wrong./n Wait I will go and check");

                }

                break;

            case "Book Narrator":

                System.out.println("Book Narrator");

                break;

            case "Meaning":
                try {
                    System.out.println("getmeaning");
                    if (agent.isShowing()) {
                        agent.removeAgent();
                    }

                    // pause bkreader thread
                    bkReader.setRun(false);

                    // get the selected word meaning
                    String wordObj = textExtractor.startTextExtracter();
                    if (wordObj == null) {
                        throw new Exception("wordDetectionError");
                    }
                    WordObject currentWord = dictionary.getWord(wordObj);
                    if (currentWord == null) {
                        currentWord = dictionary.spellCorrectedWord(wordObj);
                    }

                    if (currentWord == null) {
                        throw new Exception("noWord");
                    }
                    

                    panel = new JPanel();

                    // set background image
                    BufferedImage bufImage = ImageIO.read(new File(currentDicPath + "/resources/images/bookreader/unnamed.png"));
                    bufImage = resize(bufImage, screenWidth, screenHeight);
                    JLabel background = new JLabel(new ImageIcon(bufImage));
                    background.setBounds(0, 0, screenWidth, screenHeight);

                    //  word showing label
                    JLabel word = new JLabel(currentWord.getName() + " - " + currentWord.getMeaning());
                    word.setBounds(100, 0, 700, 100);
                    word.setHorizontalTextPosition(JLabel.LEFT);
                    word.setVerticalTextPosition(JLabel.TOP);
                    word.setFont(new Font("Calibri", Font.BOLD, 70));
                    word.setForeground(Color.white);

                    // word related image showing label
                    BufferedImage img = ImageIO.read(new File(currentWord.getFilePath()));
                    img = resize(img, 643, 350);
                    JLabel image = new JLabel(new ImageIcon(img));
                    image.setBounds(350, 150, 643, 350);

                    // close button
                    JButton close = new JButton();
                    close.setText("Close");
                    close.setBackground(Color.green);
                    close.setBounds(1100, 650, 120, 50);
                    close.addActionListener(this);

                    panel.add(word);
                    panel.add(image);
                    panel.add(close);
                    panel.add(background, -1);
                    panel.setVisible(true);
                    panel.setLayout(null);
                    panel.setBounds(0, 0, screenWidth, screenHeight);
                    panel.setBackground(Color.BLUE);
                    this.add(panel, 4);
                    getMeaning.setVisible(false);
                    exitButton.setVisible(false);
                    this.revalidate();
                    this.repaint();

                } catch (Exception ex) {
                    if (ex.getMessage().equals("noWord")) {
                        showAgentHappyMessage("Oh could not detect the word");
                    } else {
                        ex.printStackTrace();
                    }
                    System.out.println("@@@@@@@@@ " + ex.getMessage());

                }
                break;
        }
    }

    public void showAgentHappyMessage(String msg) {
        try {
            agent.showHappyAgent(BookReaderGUI.getInstance(), msg);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeAgent() {
        agent.removeAgent();
    }
}

class ImagePanel extends JComponent {

    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
