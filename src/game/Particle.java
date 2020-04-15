package game;

/**
 * 爆炸效果所用粒子类
 *
 * @author xsc
 * date 2020/04/14
 */
public class Particle {
    int x;
    int y;
    int radius;
    int speed;
    double newRate = 0;

    public Particle() {
    }

    public Particle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getNewRate() {
        return newRate;
    }

    public void setNewRate(double newRate) {
        this.newRate = newRate;
    }
}