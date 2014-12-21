package com.github.MitI_7;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.File;

// CompilationStatusListenerはpycharmだと使えない・・・
public class EventListener implements ProjectComponent, EditorFactoryListener {
    private Project project;
    private IDEOMConfig.State state;

    public EventListener(Project project) {
        this.project = project;
    }

    public void initComponent() {
        state = IDEOMConfig.getInstance().state;

        // editorListenerの設定
        EditorFactory.getInstance().addEditorFactoryListener(this, new Disposable() {
            @Override
            public void dispose() {
            }
        });
    }


    public void disposeComponent() {
        //CompilerManager.getInstance(this.project).removeCompilationStatusListener(this);
    }

    @NotNull
    public String getComponentName() {
        return "EventListener";
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

    /*
     EditorFactoryListener
     */
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        VirtualFile v = FileDocumentManager.getInstance().getFile(editor.getDocument());
        EditorSetting editorSetting = state.editorSetting.get(EditorSetting.DEFALUT);

        // editor名が取得でき，その設定があるなら取得する
        if (v != null) {
            String editorName = v.getName();

            // 設定にあるeditorNameからマッチするものを探す
            for (String editorNameInSetting : state.editorSetting.keySet()) {
                Boolean isMatched = false;
                try {
                    isMatched = editorName.matches(editorNameInSetting);
                }
                catch (Exception e) {
                    Messages.showErrorDialog(e.toString(), "Error setting background image.");
                }
                if (isMatched) {
                    editorSetting = state.editorSetting.get(editorNameInSetting);
                    break;
                }
            }
            //Messages.showErrorDialog(editorName, "Error setting background image.");
        }

        if (!editorSetting.useWallPaper || editorSetting.imagePath.equals("")) {return;}

        try {
            File file = new File(editorSetting.imagePath);
            Border wallPaper = new WallPaper(ImageIO.read(file), editorSetting);
            editor.getContentComponent().setBorder(wallPaper);
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error setting background image.");
        }
    }

    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }

}