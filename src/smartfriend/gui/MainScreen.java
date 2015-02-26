/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import smartfriend.GraphicRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;

/**
 *
 * @author Meuru
 */
public class MainScreen extends JPanel implements Runnable {

    private GraphicRenderer graphicRenderer;
    private Button exitButton, boardGame, numberAppButton, interactiveBookButton, schedularButton, userDetails;
    
    public MainScreen(GraphicRenderer gr) {
        this.graphicRenderer = gr;
        //new Thread(this).start();
        initPanel();
    }

    private void initPanel() {
        setLayout(null);
        exitButton = new Button("Exit", Color.decode(Colors.RED), Color.decode(Colors.DULL_PINK), Consts.EXIT_ICON);
        add(exitButton);
        exitButton.setBounds((Consts.SCREEN_WIDHT - exitButton.getPreferredSize().width) - 20, 100, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        numberAppButton = new Button("Numbers", Color.decode(Colors.DARK_ORANGE), Color.decode(Colors.DULL_PINK), 200, 200, Consts.NUMBER_ICON);
        numberAppButton.setBounds(100, 100, numberAppButton.getPreferredSize().width, numberAppButton.getPreferredSize().height);
        numberAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("You are using numbers application");
                graphicRenderer.showScreen(Consts.NUMBERAPP);
                SpeechRecognizer.getSpeechInstance().addObserver((Observer) graphicRenderer.getCurrentApplication());
                new Thread(SpeechRecognizer.getSpeechInstance()).start();
                
            }
        });
        add(numberAppButton);

        boardGame=  new Button("Game", Color.decode(Colors.RED), Color.decode(Colors.DULL_PINK), 200, 200, Consts.GAME_ICON);
        boardGame.setBounds(350, 100, boardGame.getPreferredSize().width, boardGame.getPreferredSize().height);
        boardGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("Welcome to the board game");
                graphicRenderer.showScreen(Consts.BOARD_GAME);
            }
        });
        add(boardGame);

        interactiveBookButton = new Button("Book Read", Color.decode(Colors.RED), Color.decode(Colors.DULL_PINK), 450, 200, Consts.INTERACTIVE_BOOK_ICON);
        interactiveBookButton.setBounds(100, 350, interactiveBookButton.getPreferredSize().width, interactiveBookButton.getPreferredSize().height);
        interactiveBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("You are using book read application");
                graphicRenderer.showScreen(Consts.INTERACTIVE_BOOK);
            }
        });
        add(interactiveBookButton);

        schedularButton = new Button("Scheduler", Color.decode(Colors.RED), Color.decode(Colors.DULL_PINK), 200, 200, Consts.SCEDULAR_ICON);
        schedularButton.setBounds(600, 100, schedularButton.getPreferredSize().width, schedularButton.getPreferredSize().height);
        schedularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (Consts.TALK) {
                    VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("You are using book read application");
                }
                graphicRenderer.showScreen(Consts.SCHEDULER);
            }
        });
        add(schedularButton);
        
        userDetails = new Button("Profiles", Color.decode(Colors.PURPLE), Color.decode(Colors.DULL_PINK), 200, 200, Consts.PROFILE_ICON);
        userDetails.setBounds(600, 350, userDetails.getPreferredSize().width, userDetails.getPreferredSize().height);
        userDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (Consts.TALK) {
                    VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("You are using book read application");
                }
                graphicRenderer.showScreen(Consts.USER_PROFILES);
            }
        });
        add(userDetails);
        
        

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.drawImage(ImageIO.read(new File(Consts.MAIN_IMAGE)), 0, 0, this);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("hehe");
            repaint();
        }
    }
}
