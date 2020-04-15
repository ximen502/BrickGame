package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

// 来源于CSDN某大牛的博客文章，略加修改而得之。
public class Input implements KeyListener {
    private static HashMap<Integer, Boolean> keys;
    public final static int KEY_COUNTS = 300;//存放的按键数量

    public void init() {
        keys = new HashMap<Integer, Boolean>(KEY_COUNTS);
        for (int i = 0; i < KEY_COUNTS; i++) {
            keys.put(i, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
    }

    /**
     * 此方法偶尔出现NPE异常，尚未仔细查看原因
     * @param keyCode
     * @return
     */
    public static Boolean getKeyDown(int keyCode){
        return keys.get(keyCode);
    }
}
