package smartfriend.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import smartfriend.GraphicRenderer;
import smartfriend.tts.VoiceGenerator;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;

/**
 *
 * @author Nilaksha
 */
public class PalmRecognizer extends JPanel implements Runnable {

    BufferedImage source;
    Point scannerPosition;
    JLabel scannerLabel;
    GraphicRenderer graphicRenderer;
    private volatile boolean active = true;

    public PalmRecognizer(final GraphicRenderer graphicRenderer) {

        setSize(Consts.SCREEN_WIDHT, Consts.SCREEN_HEIGHT);
        setOpaque(true);
        repaint();

        setLayout(null);
        this.graphicRenderer = graphicRenderer;

        JLabel keepPalmLabel = new JLabel("Place Your Hand Here!");
        final Font font = new Font("Araial", Font.BOLD, 50);
        keepPalmLabel.setFont(font);
        keepPalmLabel.setForeground(Color.WHITE);
        add(keepPalmLabel);
        keepPalmLabel.setBounds(600, 30, keepPalmLabel.getPreferredSize().width, keepPalmLabel.getPreferredSize().height);

        //palm
        scannerPosition = new Point();
        try {
            BufferedImage palmImage = ImageIO.read(new File(Consts.PALM_SCANNER_IMAGE));
            scannerLabel = new JLabel(new ImageIcon(palmImage));
            add(scannerLabel);
            scannerLabel.setBounds(50, 0, scannerLabel.getPreferredSize().width, scannerLabel.getPreferredSize().height);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        //button
        Button enterButton = new Button("Enter", Color.decode(Colors.YELLOW), Color.decode(Colors.DULL_PINK), Consts.ENTER_ICON);
        add(enterButton, 2);
        enterButton.setBounds(900, 500, enterButton.getPreferredSize().width, enterButton.getPreferredSize().height);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                graphicRenderer.showMainScreen();
                active = false;
            }
        });

        new Thread(this).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.drawImage(ImageIO.read(new File(Consts.PALM_BACKGROUND_IMAGE)), 0, 0, this);
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        if (Consts.TALK) {
            VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("Please place your palm here.");
        }
        while (active) {
            if (scannerLabel.getBounds().y == scannerPosition.y) {
                scannerPosition.y = (int) (768 * (Math.random()%2));
                
            } else {
                Rectangle scannerBounds = scannerLabel.getBounds();
                if (scannerPosition.y > scannerBounds.y) {
                    scannerBounds.y += 1;
                } else if (scannerPosition.y < scannerBounds.y) {
                    scannerBounds.y -= 1;
                }
                scannerLabel.setBounds(scannerBounds);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WelcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        //f.setOpacity(0.5f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new PalmRecognizer(null);
        f.add(panel);
//        f.add(new MainScreen(),-1);
        f.setSize(panel.getSize());
        //f.setLocation(100, 100);
        f.setVisible(true);

        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if ((keyCode == KeyEvent.VK_NUMPAD5) || (keyCode == KeyEvent.VK_ENTER)
                        || (keyCode == KeyEvent.VK_SPACE)) // take a snap when press NUMPAD-5, enter, or space is pressed
                {
                    System.out.println("DDDDDD");
                    System.exit(0);
                }
            }
        });
    }

}
