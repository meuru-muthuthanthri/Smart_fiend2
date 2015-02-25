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
import java.awt.event.MouseAdapter;
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
public class BoardGame extends JPanel implements MouseMotionListener {

    private ArrayList<Point> pointList;
    private Button backButton;
    Board board;

    public BoardGame() {

        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        setLayout(null);
        //welcome text
        
        addMouseMotionListener(this);

        backButton = new Button("Back", Color.decode(Colors.DARK_ORANGE), Color.decode(Colors.DULL_PINK), 200, 200, Consts.BACK_ICON);
        backButton.setBounds(1066, 100, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GraphicRenderer.getInstance().closeScreen();
            }
        });
        add(backButton);

        Button startButton = new Button("", Color.decode(Colors.GREEN), Color.decode(Colors.DULL_PINK), 100, 50, Consts.PLAY_ICON);
        startButton.setBounds(633, 450, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(board != null){
                    remove(board);
                }
                board = new Board();
                board.setBounds((Consts.SCREEN_WIDHT - 300) / 2, 50, 300, 400);
                board.setVisible(true);
                add(board);
                board.repaint();
                board.revalidate();
            }
        });
        add(startButton);

        Button leftButton = new Button("", Color.decode(Colors.YELLOW), Color.decode(Colors.DULL_PINK), 200, 50, Consts.LEFT_ICON);
        leftButton.setBounds(483, 500, leftButton.getPreferredSize().width, leftButton.getPreferredSize().height);
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (board != null) {
                    board.paddleLeft();
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (board != null) {
                    board.paddleStop();
                }
            }
        });
        add(leftButton);

        Button rightButton = new Button("", Color.decode(Colors.YELLOW), Color.decode(Colors.DULL_PINK), 200, 50, Consts.RIGHT_ICON);
        rightButton.setBounds(683, 500, rightButton.getPreferredSize().width, rightButton.getPreferredSize().height);
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (board != null) {
                    board.paddleRight();
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (board != null) {
                    board.paddleStop();
                }
            }
        });
        add(rightButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.drawImage(ImageIO.read(new File(Consts.BRICK_GAME_IMAGE)), 0, 0, this);
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
