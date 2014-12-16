package com.github.MitI_7;

import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import javax.swing.border.Border;
import javax.imageio.ImageIO;
import java.io.File;
import org.jetbrains.annotations.NotNull;


public class EditorListener implements EditorFactoryListener {
    public IDEOMConfig.State state;

    public EditorListener(@NotNull IDEOMConfig.State state) {
        this.state = state;
    }

    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Setting setting = state.editorSetting.get("Text Editor");
        if (!setting.useWallPaper) {
            return;
        }

        Editor editor = event.getEditor();

        // 通常のedior以外は画像を出さない(Event Logとか)
        if (FileDocumentManager.getInstance().getFile(editor.getDocument()) == null) {
            return;
        }
        //Messages.showErrorDialog(FileDocumentManager.getInstance().getFile(editor.getDocument()).getName(), "Error setting background image.");

        try {
            File file = new File(setting.imagePath);
            Border wallPaper = new WallPaper(ImageIO.read(file), setting.imageOpacity, setting.imagePositionNo);
            editor.getContentComponent().setBorder(wallPaper);
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error setting background image.");
        }
    }

    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }
}
