package game;

import java.applet.AudioClip;

/**
 * 播放砖块被撞音效
 *
 * @author xsc
 * date 2020/04/12
 */
public class PlayHitAudioTask implements Runnable {
    protected AudioClip audio;

    public PlayHitAudioTask(AudioClip audio) {
        this.audio = audio;
    }

    @Override
    public void run() {
        if (audio!=null) {
            audio.play();
        }
    }
}
