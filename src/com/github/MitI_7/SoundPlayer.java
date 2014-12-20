package com.github.MitI_7;

import java.io.File;
import javax.sound.sampled.*;
import com.intellij.openapi.ui.Messages;


public class SoundPlayer {
    public static void play(String filePath, double volume) {
        assert 0.0 <= volume && volume <= 1.0;

        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip.open(audioInputStream);
            FloatControl floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue((float)Math.log10(volume) * 20);
            clip.start();

        } catch(Exception e) {
            Messages.showErrorDialog(e.toString(), "Error in Playing Sound.");
        }
    }
}