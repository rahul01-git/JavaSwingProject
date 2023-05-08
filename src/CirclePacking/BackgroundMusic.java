package CirclePacking;

import javax.sound.sampled.*;
import java.io.*;

public class BackgroundMusic {
    private Clip clip;

    public void playMusic() {
        try {
            File musicFile = new File("R:\\Circle packing\\res\\music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("Error playing background music: " + e.getMessage());
        }
    }

    public void stopMusic() {
        clip.stop();
        clip.close();
    }
}
