package smartfriend.applications.scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import smartfriend.GraphicRenderer;
import smartfriend.applications.userprofile.UserDetailManagement;
import smartfriend.gui.Button;
import smartfriend.gui.KeyBoardPanel;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Nilaksha
 */
public class SchedulerManagement extends JPanel implements MouseListener, Observer {

    private JPanel foregroundPanel;
    private final ImageXMLParser xml;
    private final VoiceGenerator talk;
    private JLabel dateLabel, yearLabel, monthLabel, DayLabel, timeLabel, hoursLabel, minutesLabel, recordLabel, mainLabel;
    private JTextField yearTextField, monthTextField, dayTextField, hoursTextField, minutesTextField;
    private Button amButton, pmButton, recordingButton, saveButton, closeButton;

    private int line_No;
    private int am, pm;

    public SchedulerManagement() {

        foregroundPanel = new JPanel();
        xml = new ImageXMLParser("sImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        runSchedulerGUI();

        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();
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
            mainLabel.setFont(new Font("Century Gothic", 1, 60));
            mainLabel.setForeground(Color.WHITE);
            mainLabel.setText("Task Scheduler");
            foregroundPanel.add(mainLabel);

            dateLabel = new JLabel();
            dateLabel.setBounds(0, 0, 400, 200);
            dateLabel.setLocation(50, 220);
            dateLabel.setFont(new Font("Ariel", 1, 40));
            dateLabel.setForeground(Color.white);
            dateLabel.setText("Date");
            foregroundPanel.add(dateLabel);

            yearLabel = new JLabel();
            yearLabel.setBounds(0, 0, 400, 200);
            yearLabel.setLocation(250, 180);
            yearLabel.setFont(new Font("Ariel", 1, 25));
            yearLabel.setForeground(Color.white);
            yearLabel.setText("Year");
            foregroundPanel.add(yearLabel);

            yearTextField = new JTextField();
            yearTextField.setBounds(0, 0, 150, 100);
            yearTextField.setLocation(210, 300);
            yearTextField.setBackground(Color.decode(Colors.LIGHT_PINK));
            yearTextField.setBorder(null);
            yearTextField.setFont(new Font("Ariel", 1, 25));
            foregroundPanel.add(yearTextField);
            yearTextField.addMouseListener(this);

            monthLabel = new JLabel();
            monthLabel.setBounds(0, 0, 400, 200);
            monthLabel.setLocation(420, 180);
            monthLabel.setFont(new Font("Ariel", 1, 25));
            monthLabel.setForeground(Color.white);
            monthLabel.setText("Month");
            foregroundPanel.add(monthLabel);

            monthTextField = new JTextField();
            monthTextField.setBounds(0, 0, 150, 100);
            monthTextField.setLocation(370, 300);
            monthTextField.setBackground(Color.decode(Colors.LIGHT_PINK));
            monthTextField.setBorder(null);
            monthTextField.setFont(new Font("Ariel", 1, 25));
            foregroundPanel.add(monthTextField);
            monthTextField.addMouseListener(this);

            DayLabel = new JLabel();
            DayLabel.setBounds(0, 0, 400, 200);
            DayLabel.setLocation(590, 180);
            DayLabel.setFont(new Font("Ariel", 1, 25));
            DayLabel.setForeground(Color.white);
            DayLabel.setText("Day");
            foregroundPanel.add(DayLabel);

            dayTextField = new JTextField();
            dayTextField.setBounds(0, 0, 150, 100);
            dayTextField.setLocation(530, 300);
            dayTextField.setBackground(Color.decode(Colors.LIGHT_PINK));
            dayTextField.setBorder(null);
            dayTextField.setFont(new Font("Ariel", 1, 25));
            foregroundPanel.add(dayTextField);
            dayTextField.addMouseListener(this);

            timeLabel = new JLabel();
            timeLabel.setBounds(0, 0, 400, 200);
            timeLabel.setLocation(50, 370);
            timeLabel.setFont(new Font("Ariel", 1, 40));
            timeLabel.setForeground(Color.white);
            timeLabel.setText("Time");
            foregroundPanel.add(timeLabel);

            hoursLabel = new JLabel();
            hoursLabel.setBounds(0, 0, 400, 200);
            hoursLabel.setLocation(250, 330);
            hoursLabel.setFont(new Font("Ariel", 1, 25));
            hoursLabel.setForeground(Color.white);
            hoursLabel.setText("hour");
            foregroundPanel.add(hoursLabel);

            hoursTextField = new JTextField();
            hoursTextField.setBounds(0, 0, 150, 100);
            hoursTextField.setLocation(210, 450);
            hoursTextField.setBackground(Color.decode(Colors.LIGHT_PINK));
            hoursTextField.setBorder(null);
            hoursTextField.setFont(new Font("Ariel", 1, 25));
            foregroundPanel.add(hoursTextField);
            hoursTextField.addMouseListener(this);

            minutesLabel = new JLabel();
            minutesLabel.setBounds(0, 0, 400, 200);
            minutesLabel.setLocation(400, 330);
            minutesLabel.setFont(new Font("Ariel", 1, 25));
            minutesLabel.setForeground(Color.white);
            minutesLabel.setText("minute");
            foregroundPanel.add(minutesLabel);

            minutesTextField = new JTextField();
            minutesTextField.setBounds(0, 0, 150, 100);
            minutesTextField.setLocation(370, 450);
            minutesTextField.setBackground(Color.decode(Colors.LIGHT_PINK));
            minutesTextField.setBorder(null);
            minutesTextField.setFont(new Font("Ariel", 1, 25));
            foregroundPanel.add(minutesTextField);
            minutesTextField.addMouseListener(this);

            amButton = new Button("", Color.decode(Colors.PURPLE), Color.decode(Colors.LIGHT_PINK), 140, 100, xml.getImageLocation(2));
            amButton.setBounds(530, 450, amButton.getPreferredSize().width, amButton.getPreferredSize().height);
            foregroundPanel.add(amButton);
            amButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    am = 1;
                }
            });

            pmButton = new Button("", Color.decode(Colors.PURPLE), Color.decode(Colors.LIGHT_PINK), 140, 100, xml.getImageLocation(3));
            pmButton.setBounds(680, 450, pmButton.getPreferredSize().width, pmButton.getPreferredSize().height);
            foregroundPanel.add(pmButton);
            pmButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    pm = 1;
                }
            });

            recordLabel = new JLabel();
            recordLabel.setBounds(0, 0, 400, 200);
            recordLabel.setLocation(50, 540);
            recordLabel.setFont(new Font("Ariel", 1, 40));
            recordLabel.setForeground(Color.white);
            recordLabel.setText("Task");
            foregroundPanel.add(recordLabel);

            recordingButton = new Button("", Color.decode(Colors.PURPLE), Color.decode(Colors.LIGHT_PINK), 200, 140, xml.getImageLocation(4));
            recordingButton.setBounds(210, 620, recordingButton.getPreferredSize().width, recordingButton.getPreferredSize().height);
            recordingButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    recordingButtonActionPerformed(ae);
                }
            });

            foregroundPanel.add(recordingButton);

            saveButton = new Button("", Color.decode(Colors.PURPLE), Color.decode(Colors.LIGHT_PINK), 200, 140, xml.getImageLocation(5));
            saveButton.setBounds(1130, 620, saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
            foregroundPanel.add(saveButton);
            saveButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    appendToFile();
                    System.exit(0);
                }
            });

            closeButton = new Button("", Color.decode(Colors.PURPLE), Color.decode(Colors.LIGHT_PINK), 130, 130, xml.getImageLocation(10));
            closeButton.setBounds(1235, 0, closeButton.getPreferredSize().width, closeButton.getPreferredSize().height);
            foregroundPanel.add(closeButton);
            closeButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    GraphicRenderer.getInstance().showScreen(Consts.SCHEDULER, Consts.MAIN_SCREEN);
                }
            });

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

    private void recordingButtonActionPerformed(ActionEvent ae) {
        taskRecordingManagement trm = new taskRecordingManagement();
        trm.setLayout(new GridLayout(1, 1));
        trm.setBounds(0, 0, 650, 140);
        trm.setLocation(430, 620);
        trm.setOpaque(false);
        foregroundPanel.add(trm);
        trm.setVisible(true);
        foregroundPanel.validate();
        foregroundPanel.repaint();

    }

    public void appendToFile() {
        try {

            line_No = 0;
            int mm = Integer.parseInt(monthTextField.getText());
            int dd = Integer.parseInt(dayTextField.getText());
            int yy = Integer.parseInt(yearTextField.getText());
            int hours = Integer.parseInt(hoursTextField.getText());
            int minutes = Integer.parseInt(minutesTextField.getText());
            String am_pm;
            if (am == 1) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            int waveRecordingNo = getLineNumber() + 1;

            String data = "\n" + waveRecordingNo + " " + yy + " " + mm + " " + dd + " " + hours + " " + minutes + " " + am_pm;

            File file = new File(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("schedulerFilePath"));

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.newLine();
            bufferWritter.close();

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get the number of the recording 
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

    @Override
    public void mouseClicked(MouseEvent e) {

        JTextField t = (JTextField) e.getSource();
        KeyBoardPanel keyBoardPanel = new KeyBoardPanel(t);
        keyBoardPanel.setLayout(new GridLayout(1, 1));
        keyBoardPanel.setBounds(0, 0, 650, 800);
        keyBoardPanel.setLocation(860, 130);
        keyBoardPanel.setOpaque(false);
        foregroundPanel.add(keyBoardPanel);
        keyBoardPanel.setVisible(true);
        keyBoardPanel.validate();
        keyBoardPanel.repaint();
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

    @Override
    public void update(Observable o, Object arg) {
    
        if (arg.equals("close")) {
            GraphicRenderer.getInstance().showScreen(Consts.SCHEDULER, Consts.MAIN_SCREEN);
        }
    }
}
