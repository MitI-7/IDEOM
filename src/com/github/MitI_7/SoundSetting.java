package com.github.MitI_7;

import java.util.List;
import static java.util.Arrays.asList;


public class SoundSetting {
    public String eventName = "";
    public boolean useSound = false;
    public String soundPath = "";
    public double soundVolume = 0.5;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}

        SoundSetting soundSetting = (SoundSetting)obj;
        // sound
        if (!this.eventName.equals(soundSetting.eventName)) {return false;}
        if (this.useSound != soundSetting.useSound) {return false;}
        if (!this.soundPath.equals(soundSetting.soundPath)) {return false;}
        if (this.soundVolume != soundSetting.soundVolume) {return false;}

        return true;
    }

    // イベント名
    public static final String PROJECTOPEN = "Project Open";
    public static final String PROJECTCLOSE = "Project Close";
    public static final List<String> eventNameList = asList(PROJECTOPEN, PROJECTCLOSE);

}
