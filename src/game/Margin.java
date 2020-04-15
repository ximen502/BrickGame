package game;

/**
 * 表示边距的类，内容区与窗口的边距
 * @author xsc
 * date 2020/04/15
 */
public class Margin {
    public int left;
    public int right;
    public int top;
    public int bottom;

    public Margin(){}

    public Margin(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
}
