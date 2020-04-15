package game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 左右移动的木头板类
 *
 * @author xsc
 * date 2020/04/06
 */
public class Wood extends GameObject{
    private static final int speed = 8;

    public Wood() {}

    public Wood(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
//        g.drawImage(image, x, y, null);
    }

    @Override
    public void onTick() {
        if (Input.getKeyDown(KeyEvent.VK_LEFT))
            this.transferBy(-1 * speed, 0);
        if(Input.getKeyDown(KeyEvent.VK_RIGHT))
            this.transferBy(1 * speed, 0);
//        if (Input.getKeyDown(KeyEvent.VK_UP))
//            this.transferBy(0, -1 * speed);
//        if(Input.getKeyDown(KeyEvent.VK_DOWN))
//            this.transferBy(0, 1*speed);
    }

    @Override
    public void transferBy(int offsetX, int offsetY) {
        super.transferBy(offsetX, offsetY);
        Scene scene = getScene();
        Margin margin = scene.in;
        if (super.x < margin.left)
            super.x = margin.left;
        if (super.x > scene.width - margin.right - this.width)
            super.x = scene.width - margin.right - this.width;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean isReachEdge(){
        Scene scene = getScene();
        Margin margin = scene.in;
        if (super.x < margin.left || super.x > scene.width - margin.right - this.width){
            return true;
        } else {
            return false;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
