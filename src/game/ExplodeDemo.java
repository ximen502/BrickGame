package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class ExplodeDemo extends JFrame{
    private ExplodePanel explodePanel = new ExplodePanel();

    public static void main(String[] args) {
        ExplodeDemo frame = new ExplodeDemo();
        frame.setTitle("ExplodeDemo");
        frame.setSize(500, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public ExplodeDemo() throws HeadlessException {
        JButton jbt = new JButton("click");

        add(explodePanel, BorderLayout.CENTER);
        add(jbt, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                explodePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
                explodePanel.setCenter(getWidth(), getHeight());
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                explodePanel.explode();
            }
        });
        timer.setDelay(16);
        timer.start();
        explodePanel.setTimer(timer);

        jbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                explodePanel.setCenter(getWidth(), getHeight());
                explodePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
                explodePanel.explode();
                timer.stop();
                timer.start();
            }
        });
    }

    class ExplodePanel extends JPanel {

        Random random = new Random();

        int x, y;
        final int DEFAULT_RADIUS = 30;// 初始半径
        final int DEFAULT_COUNT = 25;// 初始
        int radius = DEFAULT_RADIUS;// 初始半径
        double rate = 0.8;// 每次衰减为0.8
        int width;
        int height;
        java.util.List<Point> list = new ArrayList<>();
        Timer timer;
        public ExplodePanel() {
            x = width / 2;
            y = height / 2;
        }

        public ExplodePanel(int width, int height) {
            this.width = width;
            this.height = height;
            x = width / 2;
            y = height / 2;
        }

        @Override
        public void setPreferredSize(Dimension preferredSize) {
            this.width = preferredSize.width;
            this.height = preferredSize.height;
        }

        public void setCenter(int width, int height){
            if (!list.isEmpty()){
                list.clear();
            }
            x = width / 2;
            y = height / 2;

            radius = DEFAULT_RADIUS;

            for (int i = 0; i < DEFAULT_COUNT; i++) {
                Point point = new Point(x, y, random.nextInt(30) + 2);
                point.setSpeed(random.nextInt(2) + 2);
                point.setNewRate(0.1);
                list.add(point);
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.RED);

            for (int i = 0; i < list.size(); i++) {
                Point p = list.get(i);
                g.fillOval(p.x, p.y, p.radius * 2, p.radius * 2);
                g.setColor(Color.BLUE);
                g.drawRect(p.x, p.y,p.radius * 2, p.radius * 2);
                break;
            }
        }

        public void explode(){
            for (int i = 0; i < list.size(); i++) {
                Point p = list.get(i);
                double temp = DEFAULT_RADIUS * p.getNewRate();
                double angle = (2 * Math.PI / DEFAULT_COUNT)*i;
                p.x = x+(int) Math.round(temp*Math.sin(angle));
                p.y = y+(int) Math.round(temp * Math.cos(angle));
                //p.radius= (int) Math.round(p.radius*0.8);
                System.out.println(String.format("p.x:%d, p.y:%d, p.radius:%d", p.x,p.y,p.radius));
                p.setNewRate(p.getNewRate() + 0.05*p.getSpeed());
            }

            //repaint();
        }

        public void setTimer(Timer timer) {
            this.timer = timer;
        }

    }

    class Point {
        int x;
        int y;
        int radius;
        int speed;
        double rate=0.1;
        double newRate=0;

        public Point(int x, int y, int radius) {
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
}
