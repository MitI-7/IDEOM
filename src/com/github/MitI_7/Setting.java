package com.github.MitI_7;


public class Setting {
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

        Setting setting = (Setting)obj;
        if (!this.editorName.equals(setting.editorName)) {return false;}
        if (this.useWallPaper != setting.useWallPaper) {return false;}
        if (!this.imagePath.equals(setting.imagePath)) {return false;}
        if (this.imageOpacity != setting.imageOpacity) {return false;}
        if (this.imageHorizonPositionNo != setting.imageHorizonPositionNo) {return false;}
        if (this.imageVerticalPositionNo != setting.imageVerticalPositionNo) {return false;}

        return true;
    }
}
