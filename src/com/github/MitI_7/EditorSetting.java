package com.github.MitI_7;


public class EditorSetting {
    public String editorName = "";
    public boolean useWallPaper = false;
    public String imagePath = "";
    public float imageOpacity = 0.2f;
    public int imageHorizonPositionNo = 0;
    public int imageVerticalPositionNo = 0;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}

        EditorSetting editorSetting = (EditorSetting)obj;
        if (!this.editorName.equals(editorSetting.editorName)) {return false;}
        if (this.useWallPaper != editorSetting.useWallPaper) {return false;}
        if (!this.imagePath.equals(editorSetting.imagePath)) {return false;}
        if (this.imageOpacity != editorSetting.imageOpacity) {return false;}
        if (this.imageHorizonPositionNo != editorSetting.imageHorizonPositionNo) {return false;}
        if (this.imageVerticalPositionNo != editorSetting.imageVerticalPositionNo) {return false;}

        return true;
    }
}
