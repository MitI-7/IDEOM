package com.github.MitI_7;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.*;
import com.intellij.openapi.ui.Messages;
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
        public Map<String, Setting> editorSetting = new HashMap<String, Setting>();
    }

    public IDEOMConfig.State state = new State();

    public IDEOMConfig() {
        Setting s = new Setting();
        this.state.editorSetting.put("Default", s);

        String versionName = ApplicationInfo.getInstance().getVersionName();
        // PyCharm系のときの設定
        if (versionName.contains("PyCharm")) {
            this.state.editorSetting.put("Python Console", s);
            this.state.editorSetting.put(".*.py", s);
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
