package game;

import java.awt.*;

// 来源于CSDN某大牛的博客文章，略加修改而得之。
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    protected Scene env;// 游戏对象所在窗口的所有信息
    protected Image image;// 游戏对象的前景图
    private boolean gone;

    public GameObject() {
    }

    public abstract void draw(Graphics g);

    public abstract void onTick();

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    public int getTop() {
        return y;
    }

    public int getBottom() {
        return y + height;
    }

    public void setCoordinator(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void transferBy(int x, int y){
        this.x += x;
        this.y += y;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public Scene getScene() {
        return env;
    }

    public void setScene(Scene env) {
        this.env = env;
    }

    public boolean isGone() {
        return gone;
    }

    public void setGone(boolean gone) {
        this.gone = gone;
    }
}
