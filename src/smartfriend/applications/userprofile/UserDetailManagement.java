package smartfriend.applications.userprofile;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import smartfriend.GraphicRenderer;
import smartfriend.gui.Button;
import smartfriend.gui.KeyBoardPanel;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;
import smartfriend.util.general.ImageXMLParser;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Isuri
 */
public class UserDetailManagement extends JPanel implements MouseListener, Observer {

    private JPanel foregroundPanel, keyBoardPanel;
    private final ImageXMLParser xml;
    private final VoiceGenerator talk;
    private JLabel childLabel, parentLabel, childNameLabel, dobLabel, yearLabel, monthLabel, DayLabel, parentEmailAddressLabel, comLabel, userDetailsLabel, testLabel;
    private JTextField nameTextField, yearTextField, monthTextField, dayTextField, emailTextField;
    private Button saveButton, closeButton, keyboardCloseButton, letterOne, letterTwo, letterThree, letterFour, letterFive, letterSix, letterSeven, letterEight, letterNine, backButton;
    String userName, emailAddress, yearSelected, monthSelected, daySelected;
    StringBuilder word = new StringBuilder("");

    public UserDetailManagement() {

//        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
//        setOpaque(true);
//        repaint();
        foregroundPanel = new JPanel();
//        keyBoardPanel = new JPanel();
        xml = new ImageXMLParser("udImagesPath");
        talk = VoiceGenerator.getVoiceGeneratorInstance();
        runUserDetailGUI();
        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();
    }

    private void runUserDetailGUI() {

        try {
            //set foreground panel
            foregroundPanel.setLayout(null);
            foregroundPanel.setOpaque(false);
            foregroundPanel.setBounds(0, 0, Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);

            userDetailsLabel = new JLabel();
            userDetailsLabel.setBounds(0, 0, 500, 200);
            userDetailsLabel.setLocation(750, 0);
            userDetailsLabel.setFont(new Font("Orator Std", 1, 60));
            userDetailsLabel.setText("User Details");
            foregroundPanel.add(userDetailsLabel);

            childLabel = new JLabel();
            childLabel.setBounds(0, 0, 400, 200);
            childLabel.setLocation(50, 0);
            childLabel.setFont(new Font("Orator Std", 1, 45));
            childLabel.setText("Child Details");
            foregroundPanel.add(childLabel);

            parentLabel = new JLabel();
            parentLabel.setBounds(0, 0, 400, 200);
            parentLabel.setLocation(50, 350);
            parentLabel.setFont(new Font("Orator Std", 1, 45));
            parentLabel.setText("Parent Details");
            foregroundPanel.add(parentLabel);

            childNameLabel = new JLabel();
            childNameLabel.setBounds(0, 0, 400, 200);
            childNameLabel.setLocation(50, 80);
            childNameLabel.setFont(new Font("Orator Std", 1, 30));
            childNameLabel.setText("Name");
            foregroundPanel.add(childNameLabel);

            nameTextField = new JTextField();
            nameTextField.setBounds(0, 0, 200, 40);
            nameTextField.setLocation(250, 155);
            nameTextField.setBackground(Color.LIGHT_GRAY);
            nameTextField.setBorder(null);
            nameTextField.setFont(new Font("Orator Std", 1, 30));
            foregroundPanel.add(nameTextField);
            nameTextField.addMouseListener(this);

            dobLabel = new JLabel();
            dobLabel.setBounds(0, 0, 400, 200);
            dobLabel.setLocation(50, 180);
            dobLabel.setFont(new Font("Orator Std", 1, 30));
            dobLabel.setText("Birth Day");
            foregroundPanel.add(dobLabel);

            yearLabel = new JLabel();
            yearLabel.setBounds(0, 0, 400, 200);
            yearLabel.setLocation(250, 180);
            yearLabel.setFont(new Font("Orator Std", 1, 20));
            yearLabel.setText("Year");
            foregroundPanel.add(yearLabel);

            yearTextField = new JTextField();
            yearTextField.setBounds(0, 0, 70, 40);
            yearTextField.setLocation(240, 300);
            yearTextField.setBackground(Color.LIGHT_GRAY);
            yearTextField.setBorder(null);
            yearTextField.setFont(new Font("Orator Std", 1, 20));
            foregroundPanel.add(yearTextField);
            yearTextField.addMouseListener(this);

            monthLabel = new JLabel();
            monthLabel.setBounds(0, 0, 400, 200);
            monthLabel.setLocation(350, 180);
            monthLabel.setFont(new Font("Orator Std", 1, 20));
            monthLabel.setText("Month");
            foregroundPanel.add(monthLabel);

            monthTextField = new JTextField();
            monthTextField.setBounds(0, 0, 70, 40);
            monthTextField.setLocation(340, 300);
            monthTextField.setBackground(Color.LIGHT_GRAY);
            monthTextField.setBorder(null);
            monthTextField.setFont(new Font("Orator Std", 1, 20));
            foregroundPanel.add(monthTextField);
            monthTextField.addMouseListener(this);

            DayLabel = new JLabel();
            DayLabel.setBounds(0, 0, 400, 200);
            DayLabel.setLocation(450, 180);
            DayLabel.setFont(new Font("Orator Std", 1, 20));
            DayLabel.setText("Day");
            foregroundPanel.add(DayLabel);

            dayTextField = new JTextField();
            dayTextField.setBounds(0, 0, 70, 40);
            dayTextField.setLocation(440, 300);
            dayTextField.setBackground(Color.LIGHT_GRAY);
            dayTextField.setBorder(null);
            dayTextField.setFont(new Font("Orator Std", 1, 20));
            foregroundPanel.add(dayTextField);
            dayTextField.addMouseListener(this);

            parentEmailAddressLabel = new JLabel();
            parentEmailAddressLabel.setBounds(0, 0, 400, 200);
            parentEmailAddressLabel.setLocation(50, 420);
            parentEmailAddressLabel.setFont(new Font("Orator Std", 1, 30));
            parentEmailAddressLabel.setText("Email");
            foregroundPanel.add(parentEmailAddressLabel);

            emailTextField = new JTextField();
            emailTextField.setBounds(0, 0, 300, 40);
            emailTextField.setLocation(250, 500);
            emailTextField.setBackground(Color.LIGHT_GRAY);
            emailTextField.setBorder(null);
            emailTextField.setFont(new Font("Orator Std", 1, 20));
            foregroundPanel.add(emailTextField);
            emailTextField.addMouseListener(this);

            comLabel = new JLabel();
            comLabel.setBounds(0, 0, 400, 40);
            comLabel.setLocation(555, 500);
            comLabel.setFont(new Font("Orator Std", 1, 20));
            comLabel.setText(".com");
            foregroundPanel.add(comLabel);

            saveButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 250, 150, xml.getImageLocation(11));
            saveButton.setBounds(50, 580, saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
            saveButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    saveButtonActionPerformed(ae);
                }
            });
            foregroundPanel.add(saveButton);

            closeButton = new Button("", Color.decode(Colors.LIGHT_PINK), Color.decode(Colors.LIGHT_PINK), 150, 150, xml.getImageLocation(12));
            closeButton.setBounds(1210, 0, closeButton.getPreferredSize().width, closeButton.getPreferredSize().height);
            closeButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    GraphicRenderer.getInstance().closeScreen(Consts.USER_PROFILES, Consts.MAIN_SCREEN);
                }
            });
            foregroundPanel.add(closeButton);

            foregroundPanel.setOpaque(false);
            // create wrapper JPanel
            JPanel backgroundPanel = new JPanel(new GridBagLayout());
            // add the passed in swing component first to ensure that it is in front
            backgroundPanel.add(foregroundPanel, gbc);
            // create foregroundPanel label to paint the background image
            JLabel backgroundImage = new JLabel(new ImageIcon(ImageIO.read(new File(xml.getImageLocation(10)))));
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

    private void saveButtonActionPerformed(ActionEvent ae) {
        appendToFile();
        
    }

    public void appendToFile() {
        try {

            userName = nameTextField.getText();
            emailAddress = emailTextField.getText();
            yearSelected = yearTextField.getText();
            monthSelected = monthTextField.getText();
            daySelected = dayTextField.getText();

            if (userName.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill the child name field.");
            } else if (yearSelected.equals("") || monthSelected.equals("") || daySelected.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill the date of birth correctly.");
            } else if (emailAddress.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill the email address.");
            }else{
                JOptionPane.showMessageDialog(this, "Thank you! You have successfully saved details.");
            }

            String data = userName + " " + emailAddress + " " + yearSelected + " " + monthSelected + " " + daySelected;
            File file = new File(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("userDetailsFilePath"));

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), false);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            
//            bufferWritter.newLine();
            bufferWritter.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void mouseClicked(MouseEvent e) {

        JTextField t = (JTextField) e.getSource();
        KeyBoardPanel keyBoardPanel = new KeyBoardPanel(t);
        keyBoardPanel.setLayout(new GridLayout(1, 1));
        keyBoardPanel.setBounds(0, 0, 650, 800);
        keyBoardPanel.setLocation(630, 130);
        keyBoardPanel.setOpaque(false);
        foregroundPanel.add(keyBoardPanel);
        keyBoardPanel.setVisible(true);
        keyBoardPanel.validate();
        keyBoardPanel.repaint();
//        keyboardCloseButton = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(12));
//        keyboardCloseButton.setBounds(320, 0, keyboardCloseButton.getPreferredSize().width, closeButton.getPreferredSize().height);
//
//        keyboardCloseButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//
//                keyBoardPanel.setVisible(false);
//            }
//        });
//        keyBoardPanel.add(keyboardCloseButton);
//
//        letterOne = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(1));
//        letterOne.setBounds(0, 155, letterOne.getPreferredSize().width, letterOne.getPreferredSize().height);
//
//        letterOne.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == '@') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//
//                    word.append("1");
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '1') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//
//                    word.append('0');
//                } else if (count == 3) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == '0') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//
//                    word.append('@');
//                }
//                t.setText(word.toString());
//            }
//        });
//        keyBoardPanel.add(letterOne);
//
//        letterTwo = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(2));
//        letterTwo.setBounds(160, 155, letterTwo.getPreferredSize().width, letterTwo.getPreferredSize().height);
//
//        letterTwo.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'c') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append("2");
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '2') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('a');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'a') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('b');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'b') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('c');
//                }
//                t.setText(word.toString());
//            }
//        });
//        keyBoardPanel.add(letterTwo);
//
//        letterThree = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(3));
//        letterThree.setBounds(320, 155, letterThree.getPreferredSize().width, letterThree.getPreferredSize().height);
//
//        letterThree.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'f') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('3');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '3') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('d');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'd') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('e');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'e') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('f');
//                }
//                t.setText(word.toString());
//            }
//        });
//        keyBoardPanel.add(letterThree);
//
//        letterFour = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(4));
//        letterFour.setBounds(0, 310, letterFour.getPreferredSize().width, letterFour.getPreferredSize().height);
//
//        letterFour.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.length() != 0 && word.toString().charAt(word.length() - 1) == 'i') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('4');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '4') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('g');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'g') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('h');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'h') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('i');
//                }
//                t.setText(word.toString());
//            }
//        });
//        keyBoardPanel.add(letterFour);
//
//        letterFive = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(5));
//        letterFive.setBounds(160, 310, letterFive.getPreferredSize().width, letterFive.getPreferredSize().height);
//
//        letterFive.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.length() != 0 && word.toString().charAt(word.length() - 1) == 'l') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('5');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '5') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('j');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'j') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('k');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'k') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('l');
//                }
//                t.setText(word.toString());
//            }
//        });
//        keyBoardPanel.add(letterFive);
//
//        letterSix = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(6));
//        letterSix.setBounds(320, 310, letterSix.getPreferredSize().width, letterSix.getPreferredSize().height);
//
//        letterSix.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'o') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('6');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '6') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('m');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'm') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('n');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'n') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('o');
//                }
//                t.setText(word.toString());
//            }
//
//        });
//        keyBoardPanel.add(letterSix);
//
//        letterSeven = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(7));
//        letterSeven.setBounds(0, 465, letterSeven.getPreferredSize().width, letterSeven.getPreferredSize().height);
//
//        letterSeven.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae
//            ) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 's') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('7');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '7') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('p');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'p') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('q');
//                } else if (count == 4) {
//                    if (word.toString().charAt(word.length() - 1) == 'q') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('r');
//                } else if (count == 5) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'r') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('s');
//                }
//                t.setText(word.toString());
//            }
//        }
//        );
//        keyBoardPanel.add(letterSeven);
//
//        letterEight = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(8));
//        letterEight.setBounds(160, 465, letterEight.getPreferredSize().width, letterEight.getPreferredSize().height);
//
//        letterEight.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae
//            ) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'v') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('8');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '8') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('t');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 't') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('u');
//                } else if (count == 4) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'u') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('v');
//                }
//                t.setText(word.toString());
//            }
//        }
//        );
//        keyBoardPanel.add(letterEight);
//
//        letterNine = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(9));
//        letterNine.setBounds(320, 465, letterNine.getPreferredSize().width, letterNine.getPreferredSize().height);
//
//        letterNine.addActionListener(new ActionListener() {
//            int count = 0;
//
//            @Override
//            public void actionPerformed(ActionEvent ae
//            ) {
//                count++;
//                if (count == 1) {
//                    if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'z') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('9');
//                } else if (count == 2) {
//                    if (word.toString().charAt(word.length() - 1) == '9') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('w');
//                } else if (count == 3) {
//                    if (word.toString().charAt(word.length() - 1) == 'w') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('x');
//                } else if (count == 4) {
//                    if (word.toString().charAt(word.length() - 1) == 'x') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('y');
//                } else if (count == 5) {
//                    count = 0;
//                    if (word.toString().charAt(word.length() - 1) == 'y') {
//                        word.deleteCharAt(word.length() - 1);
//                    }
//                    word.append('z');
//                }
////                word.append(Character.toString(letterbuilder.charAt(letterbuilder.length() - 1)));
//                t.setText(word.toString());
//            }
//        }
//        );
//        keyBoardPanel.add(letterNine);
//
//        backButton = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 150, 150, xml.getImageLocation(13));
//        backButton.setBounds(480, 465, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
//
//        backButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                word.deleteCharAt(word.length() - 1);
//                t.setText(word.toString());
//            }
//        }
//        );
//        keyBoardPanel.add(backButton);
//
//        word.delete(0, word.length());
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
            GraphicRenderer.getInstance().showScreen(Consts.USER_PROFILES, Consts.MAIN_SCREEN);
        }
    }
}
