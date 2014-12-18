package com.github.MitI_7;

import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import javax.swing.border.Border;
import javax.imageio.ImageIO;
import java.io.File;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;


public class EditorListener implements EditorFactoryListener {
    public IDEOMConfig.State state;

    public EditorListener(@NotNull IDEOMConfig.State state) {
        this.state = state;
    }

    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        VirtualFile v = FileDocumentManager.getInstance().getFile(editor.getDocument());
        Setting setting = state.editorSetting.get("Default");

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
                    setting = state.editorSetting.get(editorNameInSetting);
                    break;
                }
            }
            //Messages.showErrorDialog(editorName, "Error setting background image.");
        }

        if (!setting.useWallPaper || setting.imagePath.equals("")) {return;}

        try {
            File file = new File(setting.imagePath);
            Border wallPaper = new WallPaper(ImageIO.read(file), setting);
            editor.getContentComponent().setBorder(wallPaper);
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error setting background image.");
        }
    }

    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }
}
