package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * 一个负责显示爆炸效果的类
 *
 * @author xsc
 * date 2020/04/14
 */
public class ExplodeObject extends GameObject{
    private Random random = new Random();
    private final int DEFAULT_RADIUS = 30;// 初始半径
    private final int DEFAULT_COUNT = 25;// 初始粒子个数
    private java.util.List<Particle> list = new ArrayList<>();
    private int frames = 0;

    public ExplodeObject(){
        for (int i = 0; i < DEFAULT_COUNT; i++) {
            Particle p = new Particle(x, y, random.nextInt(3) + 2);
            p.setSpeed(random.nextInt(3) + 2);
            p.setNewRate(0.1);
            list.add(p);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        for (int i = 0; i < list.size(); i++) {
            Particle p = list.get(i);
            g.fillOval(p.x, p.y, p.radius * 2, p.radius * 2);
        }
    }

    @Override
    public void onTick() {
        frames++;
        for (int i = 0; i < list.size(); i++) {
            Particle p = list.get(i);
            double temp = DEFAULT_RADIUS * p.getNewRate();
            double angle = (2 * Math.PI / DEFAULT_COUNT)*i;
            p.x = x+getWidth()/2+(int) Math.round(temp*Math.sin(angle));
            p.y = y+getHeight()/2+(int) Math.round(temp * Math.cos(angle));
            //p.radius= (int) Math.round(p.radius*0.8);
            //System.out.println(String.format("p.x:%d, p.y:%d, p.radius:%d", p.x,p.y,p.radius));
            p.setNewRate(p.getNewRate() + 0.05*p.getSpeed());
        }
        if (frames > 30)// 只需要绘制30帧(hard code)
            setGone(true);
    }

    public void reset() {
        if (frames > 30) {
            frames = 0;
            for (int i = 0; i < list.size(); i++) {
                Particle p = list.get(i);
                p.setSpeed(random.nextInt(3) + 2);
                p.setNewRate(0.1);
                p.x = x;
                p.y = y;
            }
        }
    }
}
