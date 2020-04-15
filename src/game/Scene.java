package game;

/**
 * 游戏对象所处环境
 * 主要是环境的宽高和边界值
 *
 * @author xsc
 * date 2020/04/15
 */
public class Scene {
    public int width;
    public int height;
    public Margin in = new Margin();// (可见区域的4个边界的边距)

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scene(int width, int height, Margin in) {
        this.width = width;
        this.height = height;
        this.in = in;
    }

}
