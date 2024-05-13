package game.audio;

import com.brackeen.sound.Sound;
import com.brackeen.sound.SoundManager;

import javax.sound.sampled.AudioFormat;

public class AC {
    private AC(){}

    // 静态内部类，用于实现懒汉式单例
    private static class SingletonHolder {
        // 静态变量，保证唯一实例
        private static final AC INSTANCE = new AC();
    }

    // 提供静态方法获取唯一实例
    public static AC getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static final AudioFormat HIT_FORMAT = new AudioFormat(22050f, 16,2,true,false);
    public SoundManager soundManager = null;
    public Sound hit = null;
}
