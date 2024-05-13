package game;

import com.brackeen.sound.SoundManager;
import game.audio.AC;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 打砖块游戏尝试开发
 * (灵感来源于最近的想法和学习Java Swing之余的一个实践初心，一些思路和一些代码参考一个CSDN博客)
 *
 * @author xsc
 * date&time 2020/04/06 17:28:15
 */
public class BricksGame extends BaseGame {
    private ArrayList<GameObject> list = new ArrayList<>();
    private RenderTask render;
    private Random random = new Random();
    private boolean over;

    private Image memImage;
    private Graphics memG;// 双缓冲的画布

    // 游戏开始后等待一定的帧数再开始绘制游戏对象，避免一些Exception, NPE...
    private int waitFrames = 0;
    private ExplodeObject[] eObjArray = new ExplodeObject[3];

    public BricksGame(int width, int height, String title) throws HeadlessException {
        super(width, height, title);
        super.setBgColor(new Color(0x23,0x30,0x41));
        initGame(width, height);
    }

    private void initGame(int width, int height) {
        setFps(60);
        over=false;

        // 获取窗口的内外大小
        Container contentPane = this.getContentPane();
        Dimension size = contentPane.getSize();
        int contentWidth = size.width;
        int contentHeight = size.height;
        this.setContentWidth(contentWidth);
        this.setContentHeight(contentHeight);
        //System.out.println(contentPane instanceof JPanel);// true
        //System.out.println(size.width);//582
        //System.out.println(size.height);//403
        // 窗口四个方向的边界值
        Insets insets = getInsets();
        //System.out.println(insets.top);
        //System.out.println(insets.bottom);
        //System.out.println(insets.left);
        //System.out.println(insets.right);

        Scene env = new Scene(width, height, new Margin(insets.left, insets.right, insets.top, insets.bottom));

        ImageIcon woodIcon = new ImageIcon("image/wood.png");
        int w = woodIcon.getIconWidth();
        int h = woodIcon.getIconHeight();
        Wood wood = new Wood(w, h, new Color(0x97, 0x5B, 0x12));
        wood.setScene(env);
        wood.setX(getWidth()/2 - w/2);
        wood.setY(getHeight()-50);
        wood.setImage(woodIcon.getImage());
        list.add(wood);

        Ball ball = new Ball(10, Color.WHITE);
        ImageIcon iconBall = new ImageIcon("image/ball2.png");
        ball.setImage(iconBall.getImage());
        ball.setScene(env);
        ball.setCoordinator(width / 2 - ball.getRadius(), wood.getY()-ball.getRadius()*2);
        ball.setWoodBar(wood);
        list.add(ball);

        eObjArray[0] = new ExplodeObject();
        eObjArray[1] = new ExplodeObject();
        eObjArray[2] = new ExplodeObject();

        Tips tips = new Tips();
        tips.setX(getWidth() / 2);
        tips.setY(getHeight() * 2 / 3);
        list.add(tips);

        Color[] colors = new Color[]{
                new Color(0xAA, 0xCF, 0x51),
                new Color(0xFC, 0xA9, 0x4B),
                new Color(0x73, 0xC7, 0xFF),
                Color.PINK,
                Color.GRAY
        };
        //ImageIcon brickIcon = new ImageIcon("image/brick_1.png");
        int brW = 60;
        int brH = 30;
        int start = 25;
        int brickX = start;
        int brickY = 100;
        int brickAreaWidth = getWidth() - start *2;
        int count = brickAreaWidth / brW - 1;
        int remainWidth = brickAreaWidth - count * brW;
        int intervalHort = brW + 3;
        start = brickX = (getWidth() - intervalHort * (count)) / 2;
        int intervalVert = brH + 3;

        HitBrick hitBrick = new HitBrick();
        for (int j = 0; j < 3; j++) {// brick line
            for(int i = 0; i< count; i++){// brick columns
                Brick brick = new Brick();
                brick.setColor(colors[i % colors.length]);
                //brick.setImage(brickIcon.getImage());
                brick.setWidth(brW);
                brick.setHeight(brH);
                brick.setX(brickX);
                brick.setY(brickY);
                brick.setBall(ball);
                brick.setHitListener(hitBrick);
                brickX += intervalHort;
                list.add(brick);
            }
            brickX = start;
            brickY += intervalVert;
        }

        // 双缓冲，在内存里面创建一个和窗口JFrame一样大小的Image
        memImage = createImage(getWidth(), getHeight());
        memG = memImage.getGraphics();
        GameOver gameOver = new GameOver(memG);
        ball.setGameOverListener(gameOver);

        AC ac = AC.getInstance();
        ac.soundManager = new SoundManager(AC.HIT_FORMAT, 3);
        ac.hit = ac.soundManager.getSound("/game/audio/brick2.wav");

        // 键盘事件的监听
        Input input = new Input();
        input.init();
        addKeyListener(input);

        // 重新渲染画面任务
        render = new RenderTask(this);
        render.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                System.out.println(String.format("x:%d, y:%d", e.getX(), e.getY()));
                //x:218,y:305,w:164,h:45
                if ((e.getX() >= 218 && e.getX() <=(218+164))
                        && (e.getY() >= 305 && e.getY() <= (305+45))
                ){
                    render.setExitd(true);
                    render = null;
                    memImage = null;
                    memG = null;
                    removeKeyListener(input);
                    removeMouseListener(this);
                    list.clear();

                    initGame(width, height);
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (waitFrames < 30) {
            System.out.println(waitFrames);
            waitFrames++;
            return;
        }
        clear(memG);// 将画布清空为背景色

        for (int i = 0; i < list.size(); i++) {
            list.get(i).onTick();
            list.get(i).draw(memG);
        }

        if (list.size() == 2){// 只剩下小球和挡板，则通关成功！
            Wood wood = (Wood) list.get(0);
            wood.setX(getWidth()/2 - wood.getWidth()/2);
            wood.setY(getHeight()-50);

            Ball ball = (Ball) list.get(1);
            ball.setCoordinator(getWidth() / 2 - ball.getRadius(), /*bottom*/wood.getY()-ball.getRadius()*2);
            ball.setMoving(false);

            String gameOver = "恭喜通关！";
            memG.setFont(new Font("Serif", Font.BOLD, 35));
            int stringWidth = memG.getFontMetrics().stringWidth(gameOver);
            memG.setColor(Color.RED);
            memG.drawString(gameOver, getWidth()/2 - stringWidth/2, getHeight()/2);

            stopRenderTask();
        }

        if (over) {
            String gameOver = "Game Over";
            memG.setFont(new Font("Serif", Font.BOLD, 35));
            int stringWidth = memG.getFontMetrics().stringWidth(gameOver);
            memG.setColor(Color.WHITE);
            memG.drawString(gameOver, getWidth()/2 - stringWidth/2, getHeight()/2);

            String playAgain = "重新开始";
            stringWidth = memG.getFontMetrics().stringWidth(playAgain);
            int increase = 16;
            int fontSize = memG.getFont().getSize();
            int rectH = (int) (fontSize * 1.3);
            int rx=getWidth()/2 - stringWidth/2 - increase /2;
            int ry=getHeight() - fontSize * 4-(rectH-fontSize)/2;
            int rw = stringWidth + increase;
            int rh = rectH;
            //System.out.println(String.format("x:%d,y:%d,w:%d,h:%d", rx, ry, rw, rh));
            memG.drawRect(rx, ry, rw, rh);
            memG.setColor(new Color(33, 165, 230));
            memG.drawString(playAgain, getWidth()/2 - stringWidth/2, getHeight() - fontSize * 3 - 5);
        }

        // 将内存Image的内容复制到窗口上
        g.drawImage(memImage, 0, 0, null);

        // 耗性能的轮询判断，一个对象是否要消失
        for (int i = 2; i < list.size(); i++) {// 0,1位置是挡板和小球，不能消失
            GameObject gameObject = list.get(i);
            if (gameObject.isGone()) {
                list.remove(i);
                --i;
            }
        }
    }

    private void stopRenderTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                render.setExitd(true);
            }
        }).start();
    }

    public void exit(){
        //System.exit(1);
    }

    public void clear(Graphics g){
        if (g!=null) {
            g.setColor(getBgColor());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // 小球碰到砖块的回调
    class HitBrick implements Brick.HitListener{

        private int index = 0;
        public HitBrick() { }

        @Override
        public void hit(Brick brick) {
            AC ac = AC.getInstance();
            ac.soundManager.play(ac.hit);

            ExplodeObject eo = new ExplodeObject();
//            ExplodeObject eo = eObjArray[index % 3];
//            eo.reset();
            eo.x = brick.x;
            eo.y = brick.y;
            eo.width = brick.width;
            eo.height = brick.height;
            eo.color = brick.color;
            list.add(eo);
            index++;
        }
    }

    // 游戏结束内容的绘制
    class GameOver implements Ball.GameOverListener{
        private Graphics memG;

        public GameOver(Graphics g) {
            this.memG = g;
        }

        @Override
        public void over() {
            over = true;
        }
    }
}
