/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.handGesture;

import org.opencv.core.Point;

/**
 *
 * @author Meuru
 */
public class HandPoint extends Point {

    private double clickTimePerecentage;
    private boolean clicked = false;

    public HandPoint(double x, double y, double clickTimePerecentage) {
        super(x, y);
        this.clickTimePerecentage = clickTimePerecentage;
    }

    public HandPoint(Point pt) {
        super(pt.x, pt.y);
    }

    public HandPoint(double x, double y) {
        super(x, y);
    }

    public HandPoint() {
        super();
    }

    public HandPoint(double clickTimePerecentage) {
        this.clickTimePerecentage = clickTimePerecentage;
    }

    public double getClickTimePerecentage() {
        return clickTimePerecentage;
    }

    public void setClickTimePerecentage(double clickTimePerecentage) {
        this.clickTimePerecentage = clickTimePerecentage;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseTime() {
        if (clickTimePerecentage < 1) {
            this.clickTimePerecentage += .2;
        } else {
            clickTimePerecentage = 1;
            clicked = true;
        }
    }

    public void resetTime() {
        this.clickTimePerecentage = 0;
    }

    public boolean getState() {
        if (clicked) {
            clicked = false;
            return true;
        } else {
            return false;
        }
    }
}
