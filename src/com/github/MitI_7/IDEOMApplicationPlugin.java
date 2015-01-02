package com.github.MitI_7;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.options.Configurable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;

/*
Applicationレベルの機能の実装
 */
public class IDEOMApplicationPlugin extends IconProvider implements ApplicationComponent,Configurable, EditorFactoryListener {
    private IDEOMConfigPanel ideomConfigPanel;
    private IDEOMConfig.State state;

    public IDEOMApplicationPlugin() {
        super();
    }

    public void initComponent() {
        if (state == null) {
            state = IDEOMConfig.getInstance().state;
        }

        // editorListenerの設定
        EditorFactory.getInstance().addEditorFactoryListener(this, new Disposable() {
            @Override
            public void dispose() {
            }
        });
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "IDEOMApplicationPlugin";
    }


    /*
    Configurable
     */
    public String getDisplayName() {
        return "IDEOM";
    }

    public JComponent createComponent() {
        if (ideomConfigPanel == null && state != null) {
            ideomConfigPanel = new IDEOMConfigPanel(this.state);
        }
        return ideomConfigPanel.get_panel();
    }

    // OK or Apply button
    public void apply() {
        EditorSetting nowEditorSetting = ideomConfigPanel.get_editorSetting();
        state.editorSetting.put(nowEditorSetting.editorName, nowEditorSetting);

        SoundSetting nowSoundSetting = ideomConfigPanel.get_soundSetting();
        state.soundSetting.put(nowSoundSetting.eventName, nowSoundSetting);
    }

    // Cancel button
    public void reset() {
    }

    public boolean isModified() {
        // 設定パネルの状態と設定クラスの状態を比較(表示しているEditorNameのもののみ)
        EditorSetting nowEditorSetting = ideomConfigPanel.get_editorSetting();
        if (!nowEditorSetting.equals(state.editorSetting.get(nowEditorSetting.editorName))) {
            return true;
        }

        SoundSetting nowSoundSetting = ideomConfigPanel.get_soundSetting();
        if (!nowSoundSetting.equals(state.soundSetting.get(nowSoundSetting.eventName))) {
            return true;
        }

        return false;
    }

    // user closes the form
    public void disposeUIResources() {
        ideomConfigPanel = null;
    }

    public String getHelpTopic() {
        return "plugins.IDEOMApplicationPlugin";
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
                    Messages.showErrorDialog(e.toString(), "Error Setting Background Image.");
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
            Messages.showErrorDialog(e.toString(), "Error Setting Background Image.");
        }
    }

    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }

    /*
    IconProvider
     */
    @Nullable
    public Icon getIcon(@NotNull PsiElement element, int flags) {
        if (state == null) {
            state = IDEOMConfig.getInstance().state;
        }
        String editorName = element.getContainingFile().getName();
        EditorSetting editorSetting = state.editorSetting.get(EditorSetting.DEFALUT);

        // 設定にあるeditorNameからマッチするものを探す
        for (String editorNameInSetting : state.editorSetting.keySet()) {
            Boolean isMatched = false;
            try {
                isMatched = editorName.matches(editorNameInSetting);
            }
            catch (Exception e) {
                Messages.showErrorDialog(e.toString(), "Error Setting Icon Image.");
            }
            if (isMatched) {
                editorSetting = state.editorSetting.get(editorNameInSetting);
                break;
            }
        }

        if (!editorSetting.useIcon || editorSetting.iconImagePath.equals("")) {return null;}

        try {
            Image image = Toolkit.getDefaultToolkit().getImage(editorSetting.iconImagePath);
            return new ImageIcon(image);
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error Setting Icon Image.");
        }

        return null;
    }
}

