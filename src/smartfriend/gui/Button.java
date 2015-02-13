/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import smartfriend.util.general.Colors;
import smartfriend.util.general.Consts;

/**
 *
 * @author Meuru Windows tiles style buttons
 */
public class Button extends JButton {

    private static final long serialVersionUID = 1L;
    private String text, iconPath;
    protected static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 30);
    private Color normalColor;
    private Color pressedColor;

    /**
     *
     * @param text button name
     * @param color1 default color
     * @param color2 pressed color
     * @param width button width
     * @param height button height
     */
    public Button(String text, Color color1, Color color2, int width, int height, String icon) {
        //super(text);
        this.text = text;
        //this.setContentAreaFilled(false);
//        this.setMargin(new Insets(2, 5, 2, 5));
        //this.setFocusPainted(false);
        if (width != 0 & height != 0) {
            this.setPreferredSize(new Dimension(width, height));
        } else {
            setButtonPreferredSize(text.length());
        }
        this.normalColor = color1;
        this.pressedColor = color2;
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setFont(DEFAULT_FONT);
        this.iconPath = icon;

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        {
                            AudioInputStream audioInputStream = null;
                            try {
                                audioInputStream = AudioSystem.getAudioInputStream(new File(Consts.MOUSEOVER_SOUND).getAbsoluteFile());
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInputStream);
                                clip.start();
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    audioInputStream.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     *
     * @param text button name
     */
    public Button(String text) {
        this(text, Color.decode(Colors.LIGHT_GREEN), Color.decode(Colors.LIGHT_GREEN), 0, 0, null);
    }

    private void setButtonPreferredSize(int lenght) {
        Dimension preferredDimension = new Dimension(30 * lenght * 2, 30 * 4);
        setPreferredSize(preferredDimension);
        //System.out.println("LENGTH  : " + lenght + "  Dim :" + preferredDimension);
    }

//    public void setPreferredSize(Dimension dim) {
//        super.setPreferredSize(dim);
//        System.out.println("################ " + dim);
//    }
    /**
     *
     * @param text button name
     * @param color1 default color
     * @param color2 pressed color
     */
    public Button(String text, Color color1, Color color2, String icon) {
        this(text, color1, color2, 0, 0, icon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(hints);

        g2.setColor(Color.decode("#209dc7"));
        if (getModel().isRollover()) {
            g2.setColor(pressedColor);
        } else {
            if (this.hasFocus()) {
                g2.setColor(normalColor);
            } else {
                g2.setColor(normalColor);
            }
        }

        g2.fillRect(0, 0, getWidth(), getHeight());
        int iconSize = getHeight() * 7 / 10;
        try {
            BufferedImage icon = ImageIO.read(new File(iconPath));
            g2.drawImage(icon, (getWidth() - iconSize) / 2, getHeight() / 10, iconSize, iconSize, this);
        } catch (IOException ex) {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
        }

        g2.setColor(Color.WHITE);
        g2.drawString(text, getWidth() - (text.length() * 20), getHeight() * 9 / 10);
//        System.out.println(text + "   " + getPreferredSize() + "  " + normalColor.toString());
    }
}
