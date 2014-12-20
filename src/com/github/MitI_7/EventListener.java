package com.github.MitI_7;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class EventListener implements ProjectComponent, CompilationStatusListener{
    private Project project;
    private IDEOMConfig ideomConfig;

    public EventListener(Project project) {
        this.project = project;
    }

    public void initComponent() {
        // eventListenerの設定
        CompilerManager.getInstance(this.project).addCompilationStatusListener(this);

        ideomConfig = IDEOMConfig.getInstance();
    }

    public void disposeComponent() {
        CompilerManager.getInstance(this.project).removeCompilationStatusListener(this);
    }

    @NotNull
    public String getComponentName() {
        return "EventListener";
    }

    public void projectOpened() {
        SoundSetting soundSetting = this.ideomConfig.state.soundSetting.get("Project Open");
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }

    public void projectClosed() {
        SoundSetting soundSetting = this.ideomConfig.state.soundSetting.get("Project Close");
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }

    public void compilationFinished(boolean aborted, int errors, int warnings, final CompileContext compileContext) {

        SoundSetting soundSetting;
        if (errors > 0) {
            soundSetting = this.ideomConfig.state.soundSetting.get("Compile Error");
        }
        else if (warnings > 0) {
            soundSetting = this.ideomConfig.state.soundSetting.get("Compile Warning");
        }
        else {
            soundSetting = this.ideomConfig.state.soundSetting.get("Compile Success");
        }

        if (soundSetting != null && soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }

    public void fileGenerated(String s, String st ) {
        Messages.showErrorDialog("gen", "Error setting background image.");
    }
}
