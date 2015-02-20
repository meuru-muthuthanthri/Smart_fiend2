package smartfriend.applications.scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import smartfriend.applications.userprofile.UserDetailManagement;
import smartfriend.gui.Button;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;

/**
 *
 * @author Nilaksha
 */
public class SchedulerManagement extends JPanel implements MouseListener {

    private JPanel foregroundPanel;
    private final ImageXMLParser xml;
    private final VoiceGenerator talk;
    private JLabel dateLabel, yearLabel, monthLabel, DayLabel, timeLabel, hoursLabel, minutesLabel, recordLabel, mainLabel;
    private JTextField yearTextField, monthTextField, dayTextField, hoursTextField, minutesTextField;
    private Button amButton, pmButton, recordingButton, saveButton;
    
    public SchedulerManagement() {

        foregroundPanel = new JPanel();
        xml = new ImageXMLParser("sImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        runSchedulerGUI();
    }

    private void runSchedulerGUI() {

        try {
            //set foreground panel
            foregroundPanel.setLayout(null);
            foregroundPanel.setOpaque(false);
            foregroundPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);

            mainLabel = new JLabel();
            mainLabel.setBounds(0, 0, 700, 200);
            mainLabel.setLocation(50, 0);
            mainLabel.setFont(new Font("Orator Std", 1, 60));
            mainLabel.setForeground(Color.WHITE);
            mainLabel.setText("Task Scheduler");
            foregroundPanel.add(mainLabel);
            
            dateLabel = new JLabel();
            dateLabel.setBounds(0, 0, 400, 200);
            dateLabel.setLocation(50, 200);
            dateLabel.setFont(new Font("Orator Std", 1, 40));
            dateLabel.setText("Date");
            foregroundPanel.add(dateLabel);
            
            yearLabel = new JLabel();
            yearLabel.setBounds(0, 0, 400, 200);
            yearLabel.setLocation(250, 180);
            yearLabel.setFont(new Font("Orator Std", 1, 25));
            yearLabel.setText("Year");
            foregroundPanel.add(yearLabel);

            yearTextField = new JTextField();
            yearTextField.setBounds(0, 0, 150, 100);
            yearTextField.setLocation(210, 300);
            yearTextField.setBackground(Color.LIGHT_GRAY);
            yearTextField.setBorder(null);
            yearTextField.setFont(new Font("Orator Std", 1, 25));
            foregroundPanel.add(yearTextField);
            yearTextField.addMouseListener(this);

            monthLabel = new JLabel();
            monthLabel.setBounds(0, 0, 400, 200);
            monthLabel.setLocation(420, 180);
            monthLabel.setFont(new Font("Orator Std", 1, 25));
            monthLabel.setText("Month");
            foregroundPanel.add(monthLabel);

            monthTextField = new JTextField();
            monthTextField.setBounds(0, 0, 150, 100);
            monthTextField.setLocation(370, 300);
            monthTextField.setBackground(Color.LIGHT_GRAY);
            monthTextField.setBorder(null);
            monthTextField.setFont(new Font("Orator Std", 1, 25));
            foregroundPanel.add(monthTextField);
            monthTextField.addMouseListener(this);

            DayLabel = new JLabel();
            DayLabel.setBounds(0, 0, 400, 200);
            DayLabel.setLocation(590, 180);
            DayLabel.setFont(new Font("Orator Std", 1, 25));
            DayLabel.setText("Day");
            foregroundPanel.add(DayLabel);

            dayTextField = new JTextField();
            dayTextField.setBounds(0, 0, 150, 100);
            dayTextField.setLocation(530, 300);
            dayTextField.setBackground(Color.LIGHT_GRAY);
            dayTextField.setBorder(null);
            dayTextField.setFont(new Font("Orator Std", 1, 25));
            foregroundPanel.add(dayTextField);
            dayTextField.addMouseListener(this);
            
            timeLabel = new JLabel();
            timeLabel.setBounds(0, 0, 400, 200);
            timeLabel.setLocation(50, 350);
            timeLabel.setFont(new Font("Orator Std", 1, 40));
            timeLabel.setText("hour");
            foregroundPanel.add(timeLabel);
            
            hoursLabel = new JLabel();
            hoursLabel.setBounds(0, 0, 400, 200);
            hoursLabel.setLocation(250, 330);
            hoursLabel.setFont(new Font("Orator Std", 1, 25));
            hoursLabel.setText("hour");
            foregroundPanel.add(hoursLabel);

            hoursTextField = new JTextField();
            hoursTextField.setBounds(0, 0, 150, 100);
            hoursTextField.setLocation(210, 450);
            hoursTextField.setBackground(Color.LIGHT_GRAY);
            hoursTextField.setBorder(null);
            hoursTextField.setFont(new Font("Orator Std", 1, 25));
            foregroundPanel.add(hoursTextField);
            hoursTextField.addMouseListener(this);

            minutesLabel = new JLabel();
            minutesLabel.setBounds(0, 0, 400, 200);
            minutesLabel.setLocation(400, 330);
            minutesLabel.setFont(new Font("Orator Std", 1, 25));
            minutesLabel.setText("minute");
            foregroundPanel.add(minutesLabel);

            minutesTextField = new JTextField();
            minutesTextField.setBounds(0, 0, 150, 100);
            minutesTextField.setLocation(370, 450);
            minutesTextField.setBackground(Color.LIGHT_GRAY);
            minutesTextField.setBorder(null);
            minutesTextField.setFont(new Font("Orator Std", 1, 25));
            foregroundPanel.add(minutesTextField);
            minutesTextField.addMouseListener(this);
            
            amButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 140, 100, xml.getImageLocation(2));
            amButton.setBounds(530, 450, amButton.getPreferredSize().width, amButton.getPreferredSize().height);
            foregroundPanel.add(amButton);
            
            pmButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 140, 100, xml.getImageLocation(3));
            pmButton.setBounds(680, 450, pmButton.getPreferredSize().width, pmButton.getPreferredSize().height);
            foregroundPanel.add(pmButton);
            
            recordLabel = new JLabel();
            recordLabel.setBounds(0, 0, 400, 200);
            recordLabel.setLocation(50, 500);
            recordLabel.setFont(new Font("Orator Std", 1, 40));
            recordLabel.setText("Task");
            foregroundPanel.add(recordLabel);
            
            recordingButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 200, 150, xml.getImageLocation(4));
            recordingButton.setBounds(210, 580, recordingButton.getPreferredSize().width, recordingButton.getPreferredSize().height);
            recordingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    recordingButtonActionPerformed(ae);
                }
            });
            
            foregroundPanel.add(recordingButton);
            
            saveButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 200, 150, xml.getImageLocation(5));
            saveButton.setBounds(1130, 580, saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
            foregroundPanel.add(saveButton);
            
            foregroundPanel.setOpaque(false);
            // create wrapper JPanel
            JPanel backgroundPanel = new JPanel(new GridBagLayout());
            // add the passed in swing component first to ensure that it is in front
            backgroundPanel.add(foregroundPanel, gbc);
            // create foregroundPanel label to paint the background image
            JLabel backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(1)))));
            backgroundImage.setPreferredSize(new Dimension(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT));
            backgroundImage.setMinimumSize(new Dimension(1, 1));
            // align the image as specifi .
            backgroundImage.setVerticalAlignment(JLabel.TOP);
            backgroundImage.setHorizontalAlignment(JLabel.LEADING);
            // add the background label
            backgroundPanel.add(backgroundImage, gbc);
            this.add(backgroundPanel);
        } catch (IOException ex) {
            Logger.getLogger(UserDetailManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void recordingButtonActionPerformed(ActionEvent ae){
        taskRecordingManagement trm = new taskRecordingManagement();
        trm.setLayout(new GridLayout(1, 1));
        trm.setBounds(0,0,650,150);
        trm.setLocation(450,580);
        trm.setOpaque(false);
        foregroundPanel.add(trm);
        trm.setVisible(true);
        foregroundPanel.validate();
        foregroundPanel.repaint();
        
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

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
