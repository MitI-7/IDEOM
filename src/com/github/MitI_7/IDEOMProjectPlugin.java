package com.github.MitI_7;

import com.intellij.execution.Executor;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.execution.ui.RunContentWithExecutorListener;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @Override
    public void projectOpened() {
        SoundSetting soundSetting = this.state.soundSetting.get(SoundSetting.PROJECTOPEN);
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }

        // run/debug
        project.getMessageBus().connect().subscribe(RunContentManager.TOPIC, new RunContentWithExecutorListener() {
            @Override
            public void contentSelected(@Nullable RunContentDescriptor runContentDescriptor, @NotNull Executor executor) {
                if (executor.getActionName().equals("Run")) {
                    SoundSetting soundSetting = state.soundSetting.get(SoundSetting.RUN);
                    if (soundSetting.useSound) {
                        SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
                    }
                }
                else if (executor.getActionName().equals("Debug")) {
                    SoundSetting soundSetting = state.soundSetting.get(SoundSetting.DEBUG);
                    if (soundSetting.useSound) {
                        SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
                    }
                }
            }

            @Override
            public void contentRemoved(@Nullable RunContentDescriptor runContentDescriptor, @NotNull Executor executor) {
            }
        });
    }

    @Override
    public void projectClosed() {
        SoundSetting soundSetting = this.state.soundSetting.get(SoundSetting.PROJECTCLOSE);
        if (soundSetting.useSound) {
            SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
        }
    }

}
