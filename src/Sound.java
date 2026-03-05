import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    private Clip clip;

    public Sound(String path) {
        try {
            URL url = getClass().getResource(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
