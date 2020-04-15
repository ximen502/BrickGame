package game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 满屏乱跑的小球类，负责打碎砖块
 *
 * @author xsc
 * date 2020/04/06
 */
public class Ball extends RectGameObject{
    private int radius;
    private int speed = 4;// 小球移动速度
    private boolean moving;// 是否在移动
    private boolean gameOver;// 是否over
    private boolean postv;// 初始水平方向的移动左右方向
    private int horiMove;// 水平移动距离(正负号代表移动方向)
    private int vertMove;// 垂直移动距离(正负号代表移动方向)
    private Wood woodBar;//木头板
    private Image image;
    private Point center = new Point();
    private GameOverListener l;

    public Ball(int radius, Color color){
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        //g.drawImage(image, x, y, null);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public void onTick() {
        if (!moving){
            if(Input.getKeyDown(KeyEvent.VK_UP)){
                postv = (Math.random() * 10) <= 4 ? true : false;
                moving = true;
                gameOver = false;
                horiMove = postv ? speed : -speed;
                vertMove = -speed;
            } /*else if(Input.getKeyDown(KeyEvent.VK_LEFT)){
                Wood wb = woodBar;
                if (!wb.isReachEdge()) {
                    transferBy(-wb.getSpeed(), 0);
                }
            } else if(Input.getKeyDown(KeyEvent.VK_RIGHT)){
                Wood wb = woodBar;
                if (!wb.isReachEdge()) {
                    transferBy(wb.getSpeed(), 0);
                }
            }*/
        }

        if (moving){
            // arrive at left and right edge
            Scene scene = getScene();
            Margin margin = scene.in;
            if (x <= margin.left || x >= scene.width - margin.right - radius * 2){
                horiMove = -horiMove;
            }

            // arrive at top edge
            if (y <= margin.top && vertMove < 0){
                vertMove = -vertMove;
            }

            // 小球落在了挡板上
            if(getCenter().x >= woodBar.getX()
                    && getCenter().x <= woodBar.getX() + woodBar.getWidth()
                    && Math.abs(getCenter().y - woodBar.y) <= radius
                    && vertMove > 0
            ){
                vertMove = -vertMove;
            }

            // arrive at bottom edge
            // 小球落在了窗口的底部，停住小球 GAME OVER
            if (y >= scene.height - margin.bottom - radius * 2){
                moving = false;
                gameOver = true;
                if (l != null)
                    l.over();
                return;
            }

            this.transferBy(horiMove, vertMove);
        }

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Wood getWoodBar() {
        return woodBar;
    }

    public void setWoodBar(Wood woodBar) {
        this.woodBar = woodBar;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getHoriMove() {
        return horiMove;
    }

    public void setHoriMove(int horiMove) {
        this.horiMove = horiMove;
    }

    public int getVertMove() {
        return vertMove;
    }

    public void setVertMove(int vertMove) {
        this.vertMove = vertMove;
    }

    @Override
    public int getWidth() {
        return 2 * radius;
    }

    @Override
    public int getHeight() {
        return getWidth();
    }

    public Point getCenter(){
        center.x = x + radius;
        center.y = y + radius;
        return center;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameOverListener getGameOverListener() {
        return l;
    }

    public void setGameOverListener(GameOverListener l) {
        this.l = l;
    }

    public interface GameOverListener{
        void over();
    }
}
