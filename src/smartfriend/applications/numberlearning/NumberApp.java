package smartfriend.applications.numberlearning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import smartfriend.GraphicRenderer;
import smartfriend.applications.userprofile.UserDetails;
import smartfriend.gui.Button;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;
import smartfriend.util.general.MainConfiguration;
import smartfriend.util.reports.dto.NumberLearningDTO;

/**
 *
 * @author Isuri
 */
public class NumberApp extends JPanel implements Observer {

    private JPanel foregroundPanel, numberCountBoxPanel, accuracyPanel;
    private JLabel numberLabel, numberMainLabel, animationLabel, accuracyAnimationLabel, correctTextLabel, fireworkAnimationLabel, tellNumberTextLabel;
    private JButton leftButton, rightButton, closeButton;
    private final JButton[] numberCountButton;
    private JTextField spokenWordField;
    private final String[] numberArray;
    private static int nextButtonClickCount;
    private static int numberBoxCount;
    private final ImageXMLParser xml;
    private final VoiceGenerator talk;
    private final HashMap<String, String> results;
    private boolean nextRequested, backRequested;
    private int marks;
    private int count;
    private Button newNextButton, newBackButton;

    public NumberApp() {
        
        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        xml = new ImageXMLParser("nlImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        numberArray = new String[11];
        numberCountButton = new JButton[12];
        nextButtonClickCount = 1;
        numberBoxCount = 0;
        results = initializeResults();
        //runStartUpWIndow();
        runNumberLearningApp();
    }

    //initialize results hashmap
    public HashMap<String, String> initializeResults() {
        HashMap<String, String> tempMap = new HashMap<>();
        for(int i = 0; i < 10 ; i++){
            tempMap.put(String.valueOf(i), "wrong");
        }
        return tempMap;
    }

    //run initial start up window
    private void runStartUpWIndow() {

        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(35))), SwingConstants.CENTER));
        window.setBounds(462, 170, 460, 420);
        window.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }

    //load the main panel of number learning app
    private void runNumberLearningApp() {

        try {
            //set foreground panel           
            foregroundPanel = new JPanel();
            foregroundPanel.setLayout(null);
            foregroundPanel.setOpaque(false);
            foregroundPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);

            numberLabel = new JLabel();
            numberLabel.setBounds(0, 0, 1500, 500);
            numberLabel.setLocation(5, -96);
            foregroundPanel.add(numberLabel);
            numberLabel.setIcon(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(1)))));

            numberCountBoxPanel = new JPanel();
            numberCountBoxPanel.setLayout(new GridLayout(2, 5));
            foregroundPanel.add(numberCountBoxPanel);
            numberCountBoxPanel.setOpaque(false);
            numberCountBoxPanel.getGraphicsConfiguration();
            numberCountBoxPanel.setBounds(0, 0, 500, 150);
            numberCountBoxPanel.setLocation(432, 616);
            
            accuracyPanel = new JPanel();
            accuracyPanel.setBounds(0, 0, 200, 100);
            accuracyPanel.setLocation(710,400);
            accuracyPanel.setOpaque(false);
            foregroundPanel.add(accuracyPanel);

            accuracyAnimationLabel = new JLabel();
            accuracyAnimationLabel.setBounds(0, 0, 140, 200);
            accuracyAnimationLabel.setLocation(1120, 560);
            foregroundPanel.add(accuracyAnimationLabel);

            correctTextLabel = new JLabel();
            correctTextLabel.setBounds(0, 0, 240, 175);
            correctTextLabel.setLocation(1100, 580);
            accuracyPanel.add(correctTextLabel);

            fireworkAnimationLabel = new JLabel();
            fireworkAnimationLabel.setBounds(0, 0, 240, 175);
            fireworkAnimationLabel.setLocation(1050, 10);
            foregroundPanel.add(fireworkAnimationLabel);

            for (int k = 1; k <= 10; k++) {

                numberCountButton[k] = new JButton();
                numberCountButton[k].setSize(50, 50);
                numberCountButton[k].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                numberCountBoxPanel.add(numberCountButton[k]);
                numberCountButton[k].setBackground(Color.WHITE);
                final int h = k;

                numberCountButton[k].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        //generate random color
                        Random random = new Random();
                        final float hue = random.nextFloat();
                        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
                        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
                        Color color = Color.getHSBColor(hue, saturation, luminance);

                        //click on a colorful cell will repaint the cell to white
                        if (!(numberCountButton[h].getBackground().equals(Color.WHITE))) {
                            numberCountButton[h].setBackground(Color.WHITE);
                            numberBoxCount--;
                        } else {
                            numberCountButton[h].setBackground(color);
                            numberBoxCount++;
                        }
                   
                        //if correct no of cells are clicked show animations
                        if (numberBoxCount == nextButtonClickCount) {
                            accuracyAnimationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(33))));
                            correctTextLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(32))));
                            fireworkAnimationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(34))));
                        }else{
                        //set to default values for incorrect clicks
                            accuracyAnimationLabel.setIcon(null);
                            correctTextLabel.setIcon(null);
                            fireworkAnimationLabel.setIcon(null);
                        }
                    }
                });
            }

            numberMainLabel = new JLabel();
            numberMainLabel.setBounds(0, 0, 700, 700);
            numberMainLabel.setLocation(442, -40);
            foregroundPanel.add(numberMainLabel);
            numberMainLabel.setIcon(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(11)))));

            animationLabel = new JLabel();
            animationLabel.setBounds(0, 0, 700, 700);
            animationLabel.setLocation(1030, 140);
            foregroundPanel.add(animationLabel);
            animationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(20))));

            spokenWordField = new JTextField();
            spokenWordField.setFont(new Font("Orator Std", 1, 40)); 
            spokenWordField.setOpaque(false);
            spokenWordField.setBounds(340, 280, 300, 70);
            spokenWordField.setLocation(180, 560);
            spokenWordField.setBorder(null);
            spokenWordField.setForeground(Color.white);
            spokenWordField.setBackground(Color.pink);
            foregroundPanel.add(spokenWordField);
            
            newBackButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 150, xml.getImageLocation(30));
            newBackButton.setBounds(5, 320, newBackButton.getPreferredSize().width, newBackButton.getPreferredSize().height);
            newBackButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    leftButtonActionPerformed(ae);
                }
            });
            foregroundPanel.add(newBackButton);

            //back button 
//            leftButton = new JButton();
//            foregroundPanel.add(leftButton);
//            leftButton.setSize(90, 90);
//            leftButton.setLocation(40, 310);
//            leftButton.setOpaque(false);
//            leftButton.setContentAreaFilled(false);
//            leftButton.setFocusPainted(false);
//            leftButton.setBorderPainted(false);
//            leftButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(30))));
//            leftButton.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent ae) {
//                    leftButtonActionPerformed(ae);
//                }
//            });

            newNextButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 150, xml.getImageLocation(31));
            newNextButton.setBounds(160, 320, newNextButton.getPreferredSize().width, newNextButton.getPreferredSize().height);
            newNextButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    rightButtonActionPerformed(ae);
                }
            });
            foregroundPanel.add(newNextButton);
            
            //next button
//            rightButton = new JButton();
//            foregroundPanel.add(rightButton);
//            rightButton.setSize(90, 90);
//            rightButton.setLocation(180, 310);
//            rightButton.setOpaque(false);
//            rightButton.setContentAreaFilled(false);
//            rightButton.setFocusPainted(false);
//            rightButton.setBorderPainted(false);
//            //rightButton.setIcon(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(31)))));
//            rightButton.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent ae) {
//                    rightButtonActionPerformed(ae);
//                }
//            });

            //speech input button
            JButton speakButton = new JButton();
            foregroundPanel.add(speakButton);
            speakButton.setSize(150, 150);
            speakButton.setLocation(50, 600);
            speakButton.setOpaque(false);
            speakButton.setContentAreaFilled(false);
            speakButton.setFocusPainted(false);
            speakButton.setBorderPainted(false);
            speakButton.setIcon(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(36)))));

            closeButton = new JButton();
            foregroundPanel.add(closeButton);
            closeButton.setBounds(0, 0, 500, 500);
            closeButton.setLocation(1052, -190);
            closeButton.setOpaque(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusPainted(false);
            closeButton.setBorderPainted(false);
            closeButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(xml.getImageLocation(40))));
            closeButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    GraphicRenderer.getInstance().closeScreen();
                }
            });
//            tellNumberTextLabel = new JLabel();
//            tellNumberTextLabel.setBounds(0, 0, 700, 700);
//            tellNumberTextLabel.setLocation(15, 200);
//            tellNumberTextLabel.setFont(new Font("Orator Std", 1, 18));
//            tellNumberTextLabel.setText("Tell Us What Number It Is?");
//            foregroundPanel.add(tellNumberTextLabel);

            // make the passed in swing component transparent
            foregroundPanel.setOpaque(false);
            // create wrapper JPanel
            JPanel backgroundPanel = new JPanel(new GridBagLayout());
            // add the passed in swing component first to ensure that it is in front
            backgroundPanel.add(foregroundPanel, gbc);
            // create foregroundPanel label to paint the background image
            JLabel backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(37)))));
            backgroundImage.setPreferredSize(new Dimension(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT));
            backgroundImage.setMinimumSize(new Dimension(1, 1));
            // align the image as specifi .
            backgroundImage.setVerticalAlignment(JLabel.TOP);
            backgroundImage.setHorizontalAlignment(JLabel.LEADING);
            // add the background label
            backgroundPanel.add(backgroundImage, gbc);
            this.add(backgroundPanel);

        } catch (IOException ex) {
            Logger.getLogger(NumberApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //back button action
    private void leftButtonActionPerformed(ActionEvent ae) {

        try {

            if (nextButtonClickCount > 0) {
                nextButtonClickCount--;
            }

            //load next page resources
            numberArray[nextButtonClickCount] = MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/n" + nextButtonClickCount + ".jpg";
            numberLabel.setIcon(new ImageIcon(ImageIO.read(new File(numberArray[nextButtonClickCount]))));
            numberMainLabel.setIcon(new ImageIcon(ImageIO.read(new File(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/applications/numberlearning/resources/images/a" + nextButtonClickCount + ".png"))));
            animationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/animated" + nextButtonClickCount + ".gif")));

            //reset the no count box to white color
            for (int x = 1; x <= 10; x++) {

                numberCountButton[x].setBackground(Color.WHITE);
            }

            //reset values to default
            numberBoxCount = 0;
            accuracyAnimationLabel.setIcon(null);
            correctTextLabel.setIcon(null);
            fireworkAnimationLabel.setIcon(null);
            spokenWordField.setText("");

            //voice generation
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    talk.voiceOutput("How many " + xml.getImageDescription(nextButtonClickCount).split(" ")[0] + " you see here?");
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
        } catch (IOException ex) {
            Logger.getLogger(NumberApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //next button action
    private void rightButtonActionPerformed(ActionEvent ae) {
        try {

            if (nextButtonClickCount <= 10) {
                nextButtonClickCount++;
            }

            if (nextButtonClickCount > 10) {

                NumberLearningDTO numberLearningDTO = new NumberLearningDTO();
                Date currentDateTime = new Date();
                UserDetails userDetails = new UserDetails();
                numberLearningDTO.setReportType("NUMBERLEARNING");
                numberLearningDTO.setAppID("app1");
                numberLearningDTO.setChildName(userDetails.getChildName());
                numberLearningDTO.setReportID("rpt" + count);
                numberLearningDTO.setDateTime(currentDateTime);
                numberLearningDTO.setMarks(String.valueOf(calculateMarks()));
                numberLearningDTO.setResults(results);
                numberLearningDTO.setMessage("These are my results of number learning. \nFrom " + userDetails.getChildName());
                count++;

                this.setLayout(null);
                this.removeAll();
                NumberAppEndPanel endPanel = new NumberAppEndPanel();
                endPanel.setNumberLearningDetails(numberLearningDTO);
                endPanel.setVisible(true);
                endPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
                this.add(endPanel);
                this.revalidate();
                this.repaint();

                return;
            }
            //load next page resources
            numberArray[nextButtonClickCount] = MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/n" + nextButtonClickCount + ".jpg";
            numberLabel.setIcon(new ImageIcon(ImageIO.read(new File(numberArray[nextButtonClickCount]))));
            numberMainLabel.setIcon(new ImageIcon(ImageIO.read(new File(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/applications/numberlearning/resources/images/a" + nextButtonClickCount + ".png"))));
            animationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/animated" + nextButtonClickCount + ".gif")));

            //reset the no count box to white color
            for (int x = 1; x <= 10; x++) {

                numberCountButton[x].setBackground(Color.WHITE);
            }

            //reset values to default
            numberBoxCount = 0;
            accuracyAnimationLabel.setIcon(null);
            correctTextLabel.setIcon(null);
            fireworkAnimationLabel.setIcon(null);
            spokenWordField.setText("");

            //voice generation
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    talk.voiceOutput("How many " + xml.getImageDescription(nextButtonClickCount).split(" ")[0] + " you see here?");
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();

        } catch (IOException ex) {
            Logger.getLogger(NumberApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //checks for the accuracy of the spoken word
    public String checkAccuracy(String spokenWord, int number) {

        //if number is spoken by the child correctly, play a sound 
        if (spokenWord.equals(xml.getImageDescription(number).split(" ")[1])) {

            playSound();
            return "correct";
        } else {

            talk.voiceOutput("Shall we try it again?");
            return "wrong";
        }
    }

    public void playSound() {
        try {
            //kids cheering sound
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/speech/audio/Kids_cheering.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    //calculate marks of child for number learning
    public int calculateMarks() {
        marks = 0;
        for (String key : results.keySet()) {
            if (results.get(key).equals("correct")) {
                marks++;
            }
        }
        return marks;
    }

    // Set up contraints so that the user supplied component and the
    // background image label overlap and resize identically
    private static final GridBagConstraints gbc;

    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
    }

    //speech command actions
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg.equals("one") || arg.equals("two") || arg.equals("three") || arg.equals("four") || arg.equals("five") || arg.equals("six") || arg.equals("seven") || arg.equals("eight") || arg.equals("nine") || arg.equals("ten")) {
            spokenWordField.setText(arg.toString());
            String isCorrect = checkAccuracy(arg.toString(), nextButtonClickCount);
//            System.out.println("key " + nextButtonClickCount + " value " + arg.toString() + " " + isCorrect);
            results.put(String.valueOf(nextButtonClickCount), isCorrect);
        }
        else if (arg.equals("next")) {
            talk.voiceOutput("Do you want to try next?");
            nextRequested = true;
        }
        else if (nextRequested && arg.equals("yes")) {

            try {

                if (nextButtonClickCount <= 10) {
                    nextButtonClickCount++;
                }

                if (nextButtonClickCount > 10) {
                    this.setLayout(null);
                    this.removeAll();
                    NumberAppEndPanel endPanel = new NumberAppEndPanel();
                    endPanel.setVisible(true);
                    endPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
                    this.add(endPanel);
                    this.revalidate();
                    this.repaint();

                    return;
                }
                //load next page resources
                numberArray[nextButtonClickCount] = MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/n" + nextButtonClickCount + ".jpg";
                numberLabel.setIcon(new ImageIcon(ImageIO.read(new File(numberArray[nextButtonClickCount]))));
                numberMainLabel.setIcon(new ImageIcon(ImageIO.read(new File(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/a" + nextButtonClickCount + ".png"))));
                animationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/animated" + nextButtonClickCount + ".gif")));

                //reset the no count box to white color
                for (int x = 1; x <= 10; x++) {

                    numberCountButton[x].setBackground(Color.WHITE);
                }

                //reset values to default
                numberBoxCount = 0;
                accuracyAnimationLabel.setIcon(null);
                correctTextLabel.setIcon(null);
                fireworkAnimationLabel.setIcon(null);
                spokenWordField.setText("");
                nextRequested = false;
                //voice generation
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        talk.voiceOutput("How many " + xml.getImageDescription(nextButtonClickCount).split(" ")[0] + " you see here?");
                    }
                });
                timer.setRepeats(false); // Only execute once
                timer.start();
            } catch (IOException ex) {
                Logger.getLogger(NumberApp.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if (arg.equals("back")) {

            talk.voiceOutput("Do you want to go back?");
            backRequested = true;
        }
        else if (backRequested && arg.equals("yes")) {
            try {

                if (nextButtonClickCount > 0) {
                    nextButtonClickCount--;
                }

                //load next page resources
                numberArray[nextButtonClickCount] = MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/n" + nextButtonClickCount + ".jpg";
                numberLabel.setIcon(new ImageIcon(ImageIO.read(new File(numberArray[nextButtonClickCount]))));
                numberMainLabel.setIcon(new ImageIcon(ImageIO.read(new File(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/a" + nextButtonClickCount + ".png"))));
                animationLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainConfiguration.getCurrentDirectory() + "/src/smartfriend/resources/images/speech/animated" + nextButtonClickCount + ".gif")));

                //reset the no count box to white color
                for (int x = 1; x <= 10; x++) {

                    numberCountButton[x].setBackground(Color.WHITE);
                }

                //reset values to default
                numberBoxCount = 0;
                accuracyAnimationLabel.setIcon(null);
                correctTextLabel.setIcon(null);
                fireworkAnimationLabel.setIcon(null);
                spokenWordField.setText("");
                backRequested = false;

                //voice generation
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        talk.voiceOutput("How many " + xml.getImageDescription(nextButtonClickCount).split(" ")[0] + " you see here?");
                    }
                });
                timer.setRepeats(false); // Only execute once
                timer.start();
            } catch (IOException ex) {
                Logger.getLogger(NumberApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else if (arg.equals("close")) {
            GraphicRenderer.getInstance().closeScreen();
        }
        else if (arg.equals("home")) {
            //set visible home screen
        }
        else {
            nextRequested = false;
            backRequested = false;
        }
    }
}
