package com.github.MitI_7;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
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
        public boolean useWallPaper = false;
        public String imagePath = "";
        public float imageOpacity = 0.2f;
        public int imagePositionNo = 0;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {return true;}
            if (obj == null) {return false;}

            IDEOMConfig.State state = (IDEOMConfig.State)obj;
            if (this.useWallPaper != state.useWallPaper) {return false;}
            if (!this.imagePath.equals(state.imagePath)) {return false;}
            if (this.imageOpacity != state.imageOpacity) {return false;}
            if (this.imagePositionNo != state.imagePositionNo) {return false;}

            return true;
        }
    }

    public IDEOMConfig.State state = new State();

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
