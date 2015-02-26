/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Keshani
 */
public class TalkingAgent {

    private JPanel f;
    private String msgText;
    private int displayTimePeriod; 
    private String directoryPath;// time period to display message
    private JLabel gifLabel;
    private JLabel talkingLabel ;
    private boolean isShowing;

    public TalkingAgent() throws IOException {
        directoryPath= MainConfiguration.getCurrentDirectory();
        isShowing = false;
    }
    public boolean isShowing()
    {
     return isShowing;
    }

    public void showAgent(JPanel BcgContentPain, String msg, String face) throws MalformedURLException {
      
        isShowing=true;
     
        f= BcgContentPain;
        msgText= msg;
        String filePath = directoryPath+"/resources/images/bookreader/"+"animated1.gif";
        // create agent label
        File gif = new File(filePath);
        URL url = gif.toURI().toURL();
        Icon icon = new ImageIcon(url);
        gifLabel = new JLabel(icon);
        gifLabel.setBounds(1000, 0, 400, 500);

        //create label, contain message
        talkingLabel = new JLabel();
        talkingLabel.setForeground(Color.white);
        talkingLabel.setFont(new Font("Calibri", Font.BOLD, 40));
       // Border border = BorderFactory.createLineBorder(Color.ORANGE, 5);
       // talkingLabel.setBorder(border);
        talkingLabel.setText(msgText);
        talkingLabel.setHorizontalTextPosition(JLabel.RIGHT);
        talkingLabel.setVerticalTextPosition(JLabel.CENTER);
        talkingLabel.setBounds(500, 40, 1000, 200);

        BcgContentPain.add(gifLabel,0);
        BcgContentPain.add(talkingLabel,0);
        BcgContentPain.setVisible(true);
        BcgContentPain.revalidate();
        BcgContentPain.repaint();
    }
    public void showHappyAgent(JPanel contentframe, String msg) throws MalformedURLException
    {
        System.out.println("happy agent start");
        showAgent(contentframe, msg, "s");
    }
    
    public void showSadAgent(JPanel contentframe, String msg) throws MalformedURLException
    {
        showAgent(contentframe, msg, "s");
    }
    
    public boolean removeAgent()
    {
        boolean removed = false;
        f.remove(gifLabel);
        f.remove(talkingLabel);
        f.revalidate();
        f.repaint();
        isShowing=false;
        return removed;
    }
    public JLabel getLabel()
    {
        return  talkingLabel;
    }

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
//        JFrame f = new JFrame();
//        JPanel panel = new JPanel();
//        panel.setLayout(null);
//        panel.setBounds(0, 0,1500, 800);
//          TalkingAgent t = new TalkingAgent();
//              t.showAgent(panel, "test", "s");
//       
//        f.getContentPane().setBackground(Color.BLUE);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setLayout(null);
//        f.add(panel);
//        panel.setVisible(true);
//        f.pack();
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
        
            
     


    }
}
