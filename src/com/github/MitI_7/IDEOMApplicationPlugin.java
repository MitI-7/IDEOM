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
import com.intellij.psi.PsiFile;
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

    @Override
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

    @Override
    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "IDEOMApplicationPlugin";
    }


    /*
    Configurable
     */
    @Override
    public String getDisplayName() {
        return "IDEOM";
    }

    @Override
    public JComponent createComponent() {
        if (state == null) {
            state = IDEOMConfig.getInstance().state;
        }
        if (ideomConfigPanel == null) {
            ideomConfigPanel = new IDEOMConfigPanel(this.state);
        }
        return ideomConfigPanel.get_panel();
    }

    @Override
    public void apply() {
        EditorSetting nowEditorSetting = ideomConfigPanel.get_editorSetting();
        state.editorSetting.put(nowEditorSetting.editorName, nowEditorSetting);

        SoundSetting nowSoundSetting = ideomConfigPanel.get_soundSetting();
        state.soundSetting.put(nowSoundSetting.eventName, nowSoundSetting);
    }

    @Override
    public void reset() {
    }

    @Override
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
    @Override
    public void disposeUIResources() {
        ideomConfigPanel = null;
    }

    @Override
    public String getHelpTopic() {
        return "plugins.IDEOMApplicationPlugin";
    }

    /*
     EditorFactoryListener
     */
    @Override
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

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }

    /*
    IconProvider
     */
    @Nullable
    @Override
    public Icon getIcon(@NotNull PsiElement element, int flags) {
        if (state == null) {
            state = IDEOMConfig.getInstance().state;
        }
        PsiFile containingFile = element.getContainingFile();
        if (containingFile == null) {
            return null;
        }

        String editorName = containingFile.getName();
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

