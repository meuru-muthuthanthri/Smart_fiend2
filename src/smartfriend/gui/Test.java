/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import smartfriend.applications.scheduler.SchedulerManagement;

/**
 *
 * @author Meuru
 */
public class Test extends JFrame {

    static JPanel gameScreen;

    public static void main(String[] args) {
        Test frame = new Test();
        frame.setUndecorated(true);
        //f.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameScreen = new BoardGame();
        frame.add(gameScreen);
//        mainScreen.setVisible(false);

        //        f.add(new MainScreen(),-1);
        frame.setSize(gameScreen.getSize());
        //f.setLocation(100, 100);
        frame.setVisible(true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel board = new SchedulerManagement();
//        board.setBounds((Consts.SCREEN_WIDHT - 300) / 2, 50, 300, 400);
        board.setVisible(true);
        frame.add(board);
        board.repaint();
        board.revalidate();

    }

    public Test() {
    }

    public void addPanel() {
//        mainScreen.setVisible(true);
        remove(gameScreen);
//        gameScreen.setVisible(false);
    }
}
