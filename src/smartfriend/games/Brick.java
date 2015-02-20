package smartfriend.games;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meuru
 */
import javax.swing.ImageIcon;


public class Brick extends Sprite {

    String brickie = "src\\smartfriend\\games\\brickie.png";

    boolean destroyed;


    public Brick(int x, int y) {
      this.x = x;
      this.y = y;

      ImageIcon ii = new ImageIcon(brickie);
      image = ii.getImage();

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
      this.destroyed = destroyed;
    }

}
