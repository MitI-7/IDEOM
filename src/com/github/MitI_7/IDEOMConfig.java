package com.github.MitI_7;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "IDEOM",
        storages = {
                @Storage(
                        file = StoragePathMacros.APP_CONFIG + "/IDEOM_setting.xml"
                )
        }
)
public class IDEOMConfig implements PersistentStateComponent<IDEOMConfig> {
    public boolean useWallPaper = false;
    public String imagePath = "";
    public float imageOpacity = 0.2f;
    public int imagePositionNo = 0;

    @Nullable
    @Override
    public IDEOMConfig getState() {
        return this;
    }

    @Override
    public void loadState(IDEOMConfig config) {
        XmlSerializerUtil.copyBean(config, this);
    }

    public static IDEOMConfig getInstance() {
        return ServiceManager.getService(IDEOMConfig.class);
    }
}
