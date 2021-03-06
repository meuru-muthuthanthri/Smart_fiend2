/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import smartfriend.util.general.Colors;
import smartfriend.util.general.ImageXMLParser;

/**
 *
 * @author Isuri
 */
public class KeyBoardPanel extends JPanel {

    StringBuilder word = new StringBuilder("");
    private Button keyboardCloseButton, letterOne, letterTwo, letterThree, letterFour, letterFive, letterSix, letterSeven, letterEight, letterNine, backButton, nextButton;
    private final ImageXMLParser xml;
    private JPanel keyBoardPanel,backgroundPanel;
    JTextField t;
    boolean isSameLetter;

    public KeyBoardPanel(JTextField source) {
        xml = new ImageXMLParser("udImagesPath");
        t = source;
        runKeyBoard();
    }

    private void runKeyBoard() {

//        try {
            //set foreground panel
            keyBoardPanel = new JPanel();
            keyBoardPanel.setLayout(null);

            keyboardCloseButton = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(12));
            keyboardCloseButton.setBounds(210, 0, keyboardCloseButton.getPreferredSize().width, keyboardCloseButton.getPreferredSize().height);

            keyboardCloseButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    keyBoardPanel.setVisible(false);
                    backgroundPanel.setVisible(false);
                    keyBoardPanel.validate();
                    keyBoardPanel.repaint();
                }
            });
            keyBoardPanel.add(keyboardCloseButton);

            nextButton = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(14));
            nextButton.setBounds(105, 0, nextButton.getPreferredSize().width, nextButton.getPreferredSize().height);

            nextButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
////                    System.out.println("ssad"+t.getCaretPosition());
//                    t.setCaretPosition(1);
                    
                }
            });
            keyBoardPanel.add(nextButton);

            
            letterOne = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(1));
            letterOne.setBounds(0, 105, letterOne.getPreferredSize().width, letterOne.getPreferredSize().height);

            letterOne.addActionListener(new ActionListener() {
                int count = 0;

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(isSameLetter){
                        count=0;
                        isSameLetter=false;
                    }
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == '@') {
                            word.deleteCharAt(word.length() - 1);
                        }

                        word.append("1");
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '1') {
                            word.deleteCharAt(word.length() - 1);
                        }

                        word.append('0');
                    } else if (count == 3) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == '0') {
                            word.deleteCharAt(word.length() - 1);
                        }

                        word.append('@');
                    }
                    t.setText(word.toString());
                }
            });
            keyBoardPanel.add(letterOne);

            letterTwo = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(2));
            letterTwo.setBounds(105, 105, letterTwo.getPreferredSize().width, letterTwo.getPreferredSize().height);

            letterTwo.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(isSameLetter){
                        count=0;
                        isSameLetter=false;
                    }
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'c') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append("2");
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '2') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('a');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'a') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('b');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'b') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('c');
                    }
                    t.setText(word.toString());
                }
            });
            keyBoardPanel.add(letterTwo);

            letterThree = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(3));
            letterThree.setBounds(210, 105, letterThree.getPreferredSize().width, letterThree.getPreferredSize().height);

            letterThree.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'f') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('3');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '3') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('d');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'd') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('e');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'e') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('f');
                    }
                    t.setText(word.toString());
                }
            });
            keyBoardPanel.add(letterThree);

            letterFour = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(4));
            letterFour.setBounds(0, 210, letterFour.getPreferredSize().width, letterFour.getPreferredSize().height);

            letterFour.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.length() != 0 && word.toString().charAt(word.length() - 1) == 'i') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('4');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '4') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('g');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'g') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('h');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'h') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('i');
                    }
                    t.setText(word.toString());
                }
            });
            keyBoardPanel.add(letterFour);

            letterFive = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(5));
            letterFive.setBounds(105, 210, letterFive.getPreferredSize().width, letterFive.getPreferredSize().height);

            letterFive.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.length() != 0 && word.toString().charAt(word.length() - 1) == 'l') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('5');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '5') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('j');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'j') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('k');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'k') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('l');
                    }
                    t.setText(word.toString());
                }
            });
            keyBoardPanel.add(letterFive);

            letterSix = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(6));
            letterSix.setBounds(210, 210, letterSix.getPreferredSize().width, letterSix.getPreferredSize().height);

            letterSix.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'o') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('6');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '6') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('m');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'm') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('n');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'n') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('o');
                    }
                    t.setText(word.toString());
                }

            });
            keyBoardPanel.add(letterSix);

            letterSeven = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(7));

            letterSeven.setBounds(0, 315, letterSeven.getPreferredSize().width, letterSeven.getPreferredSize().height);

            letterSeven.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae
                ) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 's') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('7');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '7') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('p');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'p') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('q');
                    } else if (count == 4) {
                        if (word.toString().charAt(word.length() - 1) == 'q') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('r');
                    } else if (count == 5) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'r') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('s');
                    }
                    t.setText(word.toString());
                }
            }
            );
            keyBoardPanel.add(letterSeven);

            letterEight = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(8));

            letterEight.setBounds(
                    105, 315, letterEight.getPreferredSize().width, letterEight.getPreferredSize().height);

            letterEight.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae
                ) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'v') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('8');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '8') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('t');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 't') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('u');
                    } else if (count == 4) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'u') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('v');
                    }
                    t.setText(word.toString());
                }
            }
            );
            keyBoardPanel.add(letterEight);

            letterNine = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.WHITE), 100, 100, xml.getImageLocation(9));
            letterNine.setBounds(210, 315, letterNine.getPreferredSize().width, letterNine.getPreferredSize().height);

            letterNine.addActionListener(new ActionListener() {
                int count = 0;
                StringBuilder letterbuilder = new StringBuilder("");

                @Override
                public void actionPerformed(ActionEvent ae
                ) {
                    count++;
                    if (count == 1) {
                        if (word.length() != 0 && word.toString().charAt(word.length() - 1) == 'z') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('9');
                    } else if (count == 2) {
                        if (word.toString().charAt(word.length() - 1) == '9') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('w');
                    } else if (count == 3) {
                        if (word.toString().charAt(word.length() - 1) == 'w') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('x');
                    } else if (count == 4) {
                        if (word.toString().charAt(word.length() - 1) == 'x') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('y');
                    } else if (count == 5) {
                        count = 0;
                        if (word.toString().charAt(word.length() - 1) == 'y') {
                            word.deleteCharAt(word.length() - 1);
                        }
                        word.append('z');
                    }
//                word.append(Character.toString(letterbuilder.charAt(letterbuilder.length() - 1)));
                    t.setText(word.toString());
                }
            }
            );
            keyBoardPanel.add(letterNine);

            backButton = new Button("", Color.decode(Colors.GREY), Color.decode(Colors.GREY), 100, 100, xml.getImageLocation(13));
            backButton.setBounds(0, 0, backButton.getPreferredSize().width, backButton.getPreferredSize().height);

            backButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    word.deleteCharAt(word.length() - 1);
                    t.setText(word.toString());
                }
            }
            );
            keyBoardPanel.add(backButton);

            word.delete(0, word.length());

            keyBoardPanel.setOpaque(false);         
            // create wrapper JPanel
            backgroundPanel = new JPanel(new GridBagLayout());
            // add the passed in swing component first to ensure that it is in front
            backgroundPanel.add(keyBoardPanel, gbc);
            backgroundPanel.setOpaque(false);
            this.add(backgroundPanel);
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
