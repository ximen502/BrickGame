package game;

import java.awt.*;

/**
 * 游戏开始的提示
 */
public class Tips extends GameObject {
    int ttl = 0;
    int alpha = 0x0;
    Color textColor;
    Font font;
    String text;

    public Tips() {
        font = new Font("Arial", Font.PLAIN, 26);
        textColor = new Color(0xff, 0xff, 0xff, alpha);
        text = "按方向键上开始游戏, Press Up key Start";
    }

    @Override
    public void draw(Graphics g) {
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        textColor = new Color(0xff, 0xff, 0xff, alpha);
        g.setColor(textColor);
        g.drawString(text, x - stringWidth / 2, y);
    }

    @Override
    public void onTick() {
        ttl++;
        if(ttl <= 20) {
            alpha += 13;
            if (alpha > 255) {
                alpha = 255;
            }
        } else if (ttl > 90 && ttl <= 120) {// 渐渐淡出
            alpha -= 9;
            if (alpha <= 0) {// 防止异常发生
                alpha = 0;
            }
        } else if (ttl > 120) { // 彻底消失
            setGone(true);
        }
    }
}
