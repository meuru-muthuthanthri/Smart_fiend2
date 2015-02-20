/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import smartfriend.applications.numberlearning.NumberAppEndPanel;
import smartfriend.gui.Button;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Nilaksha
 */
public class taskRecordingManagement extends JPanel {

    private JPanel foregroundPanel;
    private final ImageXMLParser xml;
    private final VoiceGenerator talk;
    private Button startRecordButton, stopRecordingButton, listenRecordingButton, saveRecordingButton;
    int line_No;

    public taskRecordingManagement() {

        xml = new ImageXMLParser("sImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        runRecorder();
    }

    private void runRecorder() {

        try {

            line_No = 0;
            int l = getLineNumber() + 1;
            TaskRecorder recorder = new TaskRecorder(l);
            //set foreground panel
            foregroundPanel = new JPanel();
            foregroundPanel.setLayout(null);
            foregroundPanel.setOpaque(false);
            foregroundPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);

            startRecordButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 100, xml.getImageLocation(7));
            startRecordButton.setBounds(10, 20, startRecordButton.getPreferredSize().width, startRecordButton.getPreferredSize().height);
            startRecordButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    System.out.println("start");
                    recorder.captureAudio();
                }
            });
            foregroundPanel.add(startRecordButton);

            stopRecordingButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 100, xml.getImageLocation(8));
            stopRecordingButton.setBounds(170, 20, stopRecordingButton.getPreferredSize().width, stopRecordingButton.getPreferredSize().height);
            stopRecordingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    recorder.stopAudio();
                }
            });
            foregroundPanel.add(stopRecordingButton);

            listenRecordingButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 100, xml.getImageLocation(9));
            listenRecordingButton.setBounds(330, 20, listenRecordingButton.getPreferredSize().width, listenRecordingButton.getPreferredSize().height);
            listenRecordingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("recordingFilePath") + l + ".wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                }
            });
            foregroundPanel.add(listenRecordingButton);

            saveRecordingButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 100, xml.getImageLocation(5));
            saveRecordingButton.setBounds(490, 20, saveRecordingButton.getPreferredSize().width, saveRecordingButton.getPreferredSize().height);
            saveRecordingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    foregroundPanel.setVisible(false);
                }
            });
            foregroundPanel.add(saveRecordingButton);

            foregroundPanel.setOpaque(false);
            // create wrapper JPanel
            JPanel backgroundPanel = new JPanel(new GridBagLayout());
            // add the passed in swing component first to ensure that it is in front
            backgroundPanel.add(foregroundPanel, gbc);
            // create foregroundPanel label to paint the background image
            JLabel backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(6)))));
            backgroundImage.setPreferredSize(new Dimension(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT));
            backgroundImage.setMinimumSize(new Dimension(1, 1));
            // align the image as specifi .
            backgroundImage.setVerticalAlignment(JLabel.TOP);
            backgroundImage.setHorizontalAlignment(JLabel.LEADING);
            // add the background label
            backgroundPanel.add(backgroundImage, gbc);
            this.add(backgroundPanel);
        } catch (IOException ex) {
            Logger.getLogger(NumberAppEndPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getLineNumber() {

        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("schedulerFilePath")));

            while ((bufferedReader.readLine()) != null) {
                line_No++;
            }
            System.out.println("**line" + line_No);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return line_No;
    }

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
}
