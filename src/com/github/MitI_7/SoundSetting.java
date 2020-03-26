package com.github.MitI_7;

import java.util.List;
import static java.util.Arrays.asList;


public class SoundSetting {
    public String eventName = "";
    public boolean useSound = false;
    public String soundPath = "";
    public String consoleFilter = "";
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
        if (!this.soundPath.equals(soundSetting.consoleFilter)) {return false;}
        if (this.soundVolume != soundSetting.soundVolume) {return false;}

        return true;
    }

    // イベント名
    public static final String PROJECTOPEN = "Project Open";
    public static final String PROJECTCLOSE = "Project Close";
    public static final String RUN = "Run";
    public static final String CONSOLEFILTER = "Console Filter";
    public static final List<String> eventNameList = asList(RUN, CONSOLEFILTER, PROJECTOPEN, PROJECTCLOSE);

}
