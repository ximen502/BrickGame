package game;

import java.awt.*;

/**
 * 砖块类
 * @author xsc
 * date 2020/04/12
 */
public class Brick extends RectGameObject {
    private Ball ball;
    private Point leftTop = new Point();
    private Point leftBottom = new Point();
    private Point rightTop = new Point();
    private Point rightBottom = new Point();

    public Brick(){}

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(x, y, getWidth(), getHeight());
    }

    @Override
    public void onTick() {
        if (ball.isMoving()) {

            //start 碰撞检测/////////////////////////////////////////////
            boolean is = isSameQuadrant(ball.getCenter(), getLeftTop(), getRightBottom());
            if (is) {
                int r = ball.getRadius();
                Point lt = getLeftTop();
                Point lb = getLeftBottom();
                Point rt = getRightTop();
                Point rb = getRightBottom();
                Point c = ball.getCenter();
                int dx1 = Math.abs(c.x - lt.x), dy1 = Math.abs(c.y - lt.y);
                int dx2 = Math.abs(c.x - lb.x), dy2 = Math.abs(c.y - lb.y);
                int dx3 = Math.abs(c.x - rt.x), dy3 = Math.abs(c.y - rt.y);
                int dx4 = Math.abs(c.x - rb.x), dy4 = Math.abs(c.y - rb.y);

                if(((dx1*dx1) + (dy1*dy1) <= r*r)
                ||((dx2*dx2) + (dy2*dy2) <= r*r)
                ||((dx3*dx3) + (dy3*dy3) <= r*r)
                ||((dx4*dx4) + (dy4*dy4) <= r*r)){
                    System.out.println("发生了碰撞");
                    if (hitListener != null) {
                        hitListener.hit(this);
                    }
                    setGone(true);
                }

            } else {
                Point c = ball.getCenter();
                int squareW = ball.getRadius() * 2;
                int squareH = squareW;

                int brcx = x + getWidth() / 2;
                int brcy = y + getHeight() / 2;

                if((Math.abs(c.x - brcx) <= (squareW + getWidth())*0.5)
                        &&(Math.abs(c.y - brcy) <= (squareH + getHeight())*0.5)
                ){
                    System.out.println("......发生碰撞");
                    if (hitListener != null) {
                        hitListener.hit(this);
                    }
                    setGone(true);

                    /* 击中砖块，改变小球的方向 */
                    // 判断小球首先撞击的是砖块的左右还是上下侧，非常重要，否则出现不合理的移动方向。
                    double horizontal = (squareW + getWidth())*0.5 - Math.abs(c.x - brcx);
                    double vertical = (squareH + getHeight())*0.5 - Math.abs(c.y - brcy);
                    if (horizontal < vertical)
                        ball.setHoriMove(-ball.getHoriMove());
                    else
                        ball.setVertMove(-ball.getVertMove());
                }

            }
            //end碰撞检测//////////////////////////////////////////////
        }
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Point getLeftTop(){
        leftTop.x = x;
        leftTop.y = y;
        return leftTop;
    }

    public Point getRightTop(){
        rightTop.x = x+getWidth();
        rightTop.y = y;
        return rightTop;
    }

    public Point getLeftBottom(){
        leftBottom.x = x;
        leftBottom.y = y + getHeight();
        return leftBottom;
    }

    public Point getRightBottom(){
        rightBottom.x = x + getWidth();
        rightBottom.y = y + getHeight();
        return rightBottom;
    }

    private HitListener hitListener;
    public void setHitListener(HitListener hitListener){
        this.hitListener = hitListener;
    }

    public interface HitListener {
        void hit(Brick brick);
    }
}
