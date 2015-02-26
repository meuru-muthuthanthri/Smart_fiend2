package smartfriend.applications.numberlearning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import smartfriend.GraphicRenderer;
import smartfriend.applications.userprofile.UserDetails;
import smartfriend.gui.Button;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.email.dto.EmailDTO;
import smartfriend.util.email.emailmanagement.EmailEngine;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;
import smartfriend.util.general.MainConfiguration;
import smartfriend.util.reports.dto.NumberLearningDTO;
import smartfriend.util.reports.reportmanagement.ReportEngine;

/**
 *
 * @author Isuri
 */
public class NumberAppEndPanel extends JPanel {

    private JPanel foregroundPanel;
    private JLabel congratsLabel, sendEmailLabel;
    private final ImageXMLParser xml;
    private JButton sendButton, closeButton;
    private final VoiceGenerator talk;
    private NumberLearningDTO numberLearningDTO;
    private Button sendMailButton;

    public NumberAppEndPanel() {

        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        xml = new ImageXMLParser("nlImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        runNumberLearningEndPanel();
    }

    public void setNumberLearningDetails(NumberLearningDTO numberLearningDTO) {
        this.numberLearningDTO = numberLearningDTO;
    }

    private void runNumberLearningEndPanel() {

        try {
            //set foreground panel
            foregroundPanel = new JPanel();
            foregroundPanel.setLayout(null);
            foregroundPanel.setOpaque(false);
            foregroundPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);

            congratsLabel = new JLabel();
            congratsLabel.setBounds(0, 0, 500, 500);
            congratsLabel.setLocation(490, 106);
            foregroundPanel.add(congratsLabel);
            congratsLabel.setIcon(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(38)))));

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    playSound();
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();

            Timer timer2 = new Timer(2500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    talk.voiceOutput("Congratulations. You have successfully completed this level.");
                }
            });
            timer2.setRepeats(false); // Only execute once
            timer2.start();
            
            sendEmailLabel = new JLabel();
            sendEmailLabel.setBounds(0, 0, 1500, 500);
            sendEmailLabel.setLocation(440, 360);
            sendEmailLabel.setFont(new Font("Orator Std", 1, 35));
            sendEmailLabel.setText("Send your report to mom");
            sendEmailLabel.setForeground(Color.PINK);
            foregroundPanel.add(sendEmailLabel);

            sendMailButton = new Button("", Color.decode(Colors.WHITE), Color.decode(Colors.LIGHT_PINK), 150, 150, xml.getImageLocation(39));
            sendMailButton.setBounds(640, 620, sendMailButton.getPreferredSize().width, sendMailButton.getPreferredSize().height);
            sendMailButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    sendButtonActionPerformed(ae);
                }
            });
            foregroundPanel.add(sendMailButton);

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
            Logger.getLogger(NumberAppEndPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendButtonActionPerformed(ActionEvent ae) {

        try {
//            boolean isSent=false;
            UserDetails userDetails = new UserDetails();
            //send the email
            String pdfFilePath;
            pdfFilePath = ReportEngine.generateReport(numberLearningDTO);
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setMsgSubject("Message from SMART FRIEND!");
            emailDTO.setMsgBody("Dear Parent, \nPlease find the attached PDF file of your child's number learning report. \n\nThank you.\nSmart Friend");
            emailDTO.setParentEmailAdd(userDetails.getParentEmail());
            emailDTO.setAttachmentPath(pdfFilePath);
            EmailEngine.sendMail(emailDTO);
//            if(isSent){
//                JOptionPane.showMessageDialog(this, "Your report card has been sent successfully!");
//            }
        } catch (MessagingException ex) {
//            JOptionPane.showMessageDialog(this, "Sorry, try again later!");
            Logger.getLogger(NumberAppEndPanel.class.getName()).log(Level.SEVERE, null, ex);
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
