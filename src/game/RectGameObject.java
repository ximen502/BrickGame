package game;

import java.awt.*;

/**
 * 砖块的父类
 *
 * @author xsc
 * date 2020/04/15
 */
public abstract class RectGameObject extends GameObject {
    /**
     * 判断矩形是不是在一个象限里面(参考CSDN某大牛的碰撞检测JS代码，作者牛批)
     *
     * @param cood 圆形小球的圆心坐标
     * @param objA 矩形的左上角坐标
     * @param objB 矩形的右下角坐标
     * @return 圆形小球和矩形是否处于同一个直角坐标系象限
     */
    protected boolean isSameQuadrant(Point cood, Point objA, Point objB) {
        int coodX = cood.x;
        int coodY = cood.y;
        int xoA = objA.x, yoA = objA.y, xoB = objB.x, yoB = objB.y;

        if (xoA - coodX > 0 && xoB - coodX > 0) {
            if ((yoA - coodY > 0 && yoB - coodY > 0) || (yoA - coodY < 0 && yoB - coodY < 0)) {
                return true;
            }
            return false;
        } else if (xoA - coodX < 0 && xoB - coodX < 0) {
            if ((yoA - coodY > 0 && yoB - coodY > 0) || (yoA - coodY < 0 && yoB - coodY < 0)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
