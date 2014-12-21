package com.github.MitI_7;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/*
Projectレベルの機能の実装
 */
// CompilationStatusListenerはpycharmだと使えない・・・
public class IDEOMProjectPlugin implements ProjectComponent {
    private Project project;
    private IDEOMConfig.State state;

    public IDEOMProjectPlugin(Project project) {
        this.project = project;
    }

    public void initComponent() {
        state = IDEOMConfig.getInstance().state;
    }

    public void disposeComponent() {
        state = null;
    }

    @NotNull
    public String getComponentName() {
        return "IDEOMProjectPlugin";
    }

    /*
    ProjectComponent
     */
    public void projectOpened() {
        SoundSetting soundSetting = this.state.soundSetting.get(SoundSetting.PROJECTOPEN);
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }

    public void projectClosed() {
        SoundSetting soundSetting = this.state.soundSetting.get(SoundSetting.PROJECTCLOSE);
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }



}
