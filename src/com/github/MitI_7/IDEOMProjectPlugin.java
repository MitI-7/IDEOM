package com.github.MitI_7;

import com.intellij.execution.Executor;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.execution.ui.RunContentWithExecutorListener;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.xdebugger.*;
import com.intellij.xdebugger.breakpoints.*;
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

        MessageBusConnection conn = project.getMessageBus().connect();
        conn.subscribe(XDebuggerManager.TOPIC, new XDebuggerManagerListener() {
            @Override
            public void processStarted(@NotNull XDebugProcess xDebugProcess) {

                xDebugProcess.getSession().addSessionListener(
                        new XDebugSessionListener() {
                            @Override
                            public void sessionPaused() {
                                SoundSetting soundSetting = state.soundSetting.get(SoundSetting.BREAKPOINT);
                                if (soundSetting == null || !soundSetting.useSound) {
                                    return;
                                }

                                XSourcePosition position = xDebugProcess.getSession().getCurrentPosition();
                                if (position == null) {
                                    return;
                                }

                                int line = position.getLine();
                                VirtualFile file = position.getFile();

                                final XBreakpoint<?>[] breakpoints = ReadAction.compute(() -> XDebuggerManager.getInstance(project).getBreakpointManager().getAllBreakpoints());

                                for (XBreakpoint<?> breakpoint : breakpoints) {
                                    if (breakpoint instanceof XLineBreakpoint) {
                                        if (((XLineBreakpoint<?>)breakpoint).getFileUrl().equals(file.getUrl()) && ((XLineBreakpoint<?>) breakpoint).getLine() == line) {
                                            if (breakpoint.isEnabled()) {
                                                SoundPlayer.play(soundSetting.soundPath, soundSetting.soundVolume);
                                                return;
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void sessionStopped() {

                            }
                        }
                );
            }

            @Override
            public void processStopped(@NotNull XDebugProcess debugProcess) {
            }
        });
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
