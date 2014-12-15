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
    public boolean useWallPaper; // 背景画像を表示するかどうか
    public String imagePath;     // 背景画像の絶対パス
    public float imageOpacity;   // 画像の透明度
    public int imagePositionNo;  // 画像の表示位置

    public EditorListener(@NotNull String imagePath) {
        this.imagePath = imagePath;
    }

    public void editorCreated(@NotNull EditorFactoryEvent event) {
        if (!useWallPaper) {
            return;
        }

        Editor editor = event.getEditor();

        // 通常のedior以外は画像を出さない(Event Logとか)
        if (FileDocumentManager.getInstance().getFile(editor.getDocument()) == null) {
            return;
        }
        Messages.showErrorDialog(FileDocumentManager.getInstance().getFile(editor.getDocument()).getName(), "Error setting background image.");

        try {
            File file = new File(imagePath);
            Border wallPaper = new WallPaper(ImageIO.read(file), imageOpacity, imagePositionNo);
            editor.getContentComponent().setBorder(wallPaper);
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error setting background image.");
        }
    }

    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }
}
