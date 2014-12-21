package com.github.MitI_7;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import java.util.Map;
import java.util.HashMap;
import org.jetbrains.annotations.Nullable;


@State(
        name = "IDEOM",
        storages = {
                @Storage(
                        id = "IDEOM",
                        file = "$APP_CONFIG$/IDEOM_setting.xml"
                )
        }
)
public class IDEOMConfig implements PersistentStateComponent<IDEOMConfig.State> {
    public static final class State {
        public Map<String, EditorSetting> editorSetting = new HashMap<String, EditorSetting>();
        public Map<String, SoundSetting> soundSetting = new HashMap<String, SoundSetting>();
    }

    public IDEOMConfig.State state = new State();

    public IDEOMConfig() {
        // editor
        for (String editorName : EditorSetting.editorNameList) {
            EditorSetting s = new EditorSetting();
            this.state.editorSetting.put(editorName, s);
        }

        // sound
        for (String eventName : SoundSetting.eventNameList) {
            SoundSetting s = new SoundSetting();
            this.state.soundSetting.put(eventName, s);
        }
    }

    @Nullable
    @Override
    public IDEOMConfig.State getState() {
        return this.state;
    }

    @Override
    public void loadState(IDEOMConfig.State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    public static IDEOMConfig getInstance() {
        return ServiceManager.getService(IDEOMConfig.class);
    }
}
