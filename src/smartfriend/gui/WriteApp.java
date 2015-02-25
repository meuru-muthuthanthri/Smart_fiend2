/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.opencv.core.Point;
import smartfriend.GraphicRenderer;
import smartfriend.games.Board;
import smartfriend.speechRecognition.SpeechRecognizer;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;

/**
 *
 * @author Meuru
 */
public class WriteApp extends JPanel implements MouseMotionListener{

    private ArrayList<Point> pointList;
    private Button backButton;
    
    public WriteApp() {
        Board board = new Board();
        board.setBounds(0, 150, 300, 400);
        board.setVisible(true);
        add(board);
        pointList = new ArrayList<>();
        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        setLayout(null);
        //welcome text
        JLabel welcomeText = new JLabel("W");
        final Font font = new Font("Araial", Font.BOLD, 350);
        welcomeText.setFont(font);
        welcomeText.setForeground(Color.WHITE);
        //add(welcomeText);
        welcomeText.setBounds((Consts.SCREEN_WIDHT - welcomeText.getPreferredSize().width) / 2, 150, welcomeText.getPreferredSize().width, welcomeText.getPreferredSize().height);

        addMouseMotionListener(this);
        
        backButton = new Button("Back", Color.decode(Colors.DARK_ORANGE), Color.decode(Colors.DULL_PINK), 200, 200, Consts.NUMBER_ICON);
        backButton.setBounds(500, 200, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GraphicRenderer.getInstance().closeScreen(Consts.WRITE_APP, Consts.MAIN_SCREEN);
            }
        });
        add(backButton);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.drawImage(ImageIO.read(new File(Consts.WRITE_APP_IMAGE)), 0, 0, this);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        System.out.println("!!!!");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//         System.out.println("@@@" + e.getX() + "  " + e.getY());
    }
}
