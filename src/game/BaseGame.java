package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// 游戏基础框架类，主要提供了各种游戏对象绘制的窗口
// 所有的游戏对象都要在这里面显示
// 根据CSDN某大牛的文章代码略加修改而得之
public class BaseGame extends JFrame {
    private int width;
    private int height;
    private int contentWidth;// 内容区域宽度
    private int contentHeight;// 内容区域高度
    private String title;
    private Color bgColor;
    private int fps;

    public BaseGame() throws HeadlessException {
    }

    public BaseGame(int width, int height, String title) throws HeadlessException {
        this.width = width;
        this.height = height;
        this.title = title;

        initParams(width, height, title);
    }

    private void initParams(int width, int height, String title) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        g.setColor(bgColor == null ? Color.BLACK : bgColor);
        g.fillRect(0,0, width, height);
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getContentWidth() {
        return contentWidth;
    }

    public void setContentWidth(int contentWidth) {
        this.contentWidth = contentWidth;
    }

    public int getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(int contentHeight) {
        this.contentHeight = contentHeight;
    }
}
