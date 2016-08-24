package com.github.MitI_7;

import com.intellij.openapi.application.ApplicationInfo;
import java.util.List;
import static java.util.Arrays.asList;


public class EditorSetting {
    public String editorName = "";
    public boolean useWallPaper = true;
    public String imagePath = "";
    public float imageOpacity = 0.5f;
    public double imageSize = 1.0;
    public int imageHorizonPositionNo = 0;
    public int imageVerticalPositionNo = 0;
    public boolean useIcon = false;
    public String iconImagePath = "";

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}

        EditorSetting editorSetting = (EditorSetting)obj;
        if (!this.editorName.equals(editorSetting.editorName)) {return false;}
        if (this.useWallPaper != editorSetting.useWallPaper) {return false;}
        if (!this.imagePath.equals(editorSetting.imagePath)) {return false;}
        if (this.imageOpacity != editorSetting.imageOpacity) {return false;}
        if (this.imageSize != editorSetting.imageSize) {return false;}
        if (this.imageHorizonPositionNo != editorSetting.imageHorizonPositionNo) {return false;}
        if (this.imageVerticalPositionNo != editorSetting.imageVerticalPositionNo) {return false;}
        if (this.useIcon != editorSetting.useIcon) {return false;}
        if (!this.iconImagePath.equals(editorSetting.iconImagePath)) {return false;}

        return true;
    }

    public static final String DEFALUT = "Default";
    public static final String PYTHONCONSOLE = "Python Console";
    public static List<String> editorNameList = ApplicationInfo.getInstance().getVersionName().contains("PyCharm") ? asList(DEFALUT, PYTHONCONSOLE): asList(DEFALUT);
}
