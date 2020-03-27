package com.github.MitI_7;

import java.io.File;
import javax.sound.sampled.*;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
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
            Notifications.Bus.notify(
                    new Notification("IDEOM", "IDEOM: Error in Playing Sound.", e.getMessage(), NotificationType.ERROR)
            );
        }
    }
}