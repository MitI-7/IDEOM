package com.github.MitI_7;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;


public class IDEOMPlugin implements ApplicationComponent,Configurable {
    private IDEOMConfigPanel ideomConfigPanel;
    private IDEOMConfig ideomConfig;
    private EditorListener editorListener;

    public IDEOMPlugin() {
        super();
    }

    public void initComponent() {
        Application app = ApplicationManager.getApplication();
        ideomConfig = app.getComponent(IDEOMConfig.class);

        // editorListenerの設定
        editorListener = new EditorListener(ideomConfig.imagePath);
        if (ideomConfig.useWallPaper) {
            editorListener.imageOpacity = ideomConfig.imageOpacity;
            EditorFactory.getInstance().addEditorFactoryListener(editorListener);
        }
    }

    public void disposeComponent() {
        if (editorListener != null) {
            EditorFactory.getInstance().removeEditorFactoryListener(editorListener);
        }
    }

    @NotNull
    public String getComponentName() {
        return "IDEOMPlugin";
    }


    /*
    Configurable
     */
    public String getDisplayName() {
        return "IDEOM";
    }

    public JComponent createComponent() {
        if (ideomConfigPanel == null && ideomConfig != null) {
            ideomConfigPanel = new IDEOMConfigPanel(ideomConfig);
        }
        return ideomConfigPanel.get_panel();
    }

    // OK or Apply button
    public void apply() {
        ideomConfig.useWallPaper = ideomConfigPanel.useWallPaperCheckBox.isSelected();
        ideomConfig.imagePath    = ideomConfigPanel.imagePathTextField.getText();
        ideomConfig.imageOpacity = ideomConfigPanel.imageOpacitySlider.getValue() / 100.0f;

        editorListener.imagePath = ideomConfig.imagePath;
        editorListener.imageOpacity = ideomConfig.imageOpacity;

        // TODO: 再起動なしで反映できるようにする
        if (0 == Messages.showYesNoDialog("IDE must be restarted for changes to take effect.Would you like to shutdown IDEA?", "Warning!", null)) {
            ApplicationManager.getApplication().exit();
        }
    }

    // Cancel button
    public void reset() {
    }

    public boolean isModified() {
        if (!ideomConfigPanel.imagePathTextField.getText().equals(ideomConfig.imagePath) ||
            ideomConfigPanel.useWallPaperCheckBox.isSelected() != ideomConfig.useWallPaper ||
            ideomConfigPanel.imageOpacitySlider.getValue() != ideomConfig.imageOpacity){
            return true;
        }
        return false;
    }

    // user closes the form
    public void disposeUIResources() {
        ideomConfigPanel = null;
    }

    public String getHelpTopic() {
        return "plugins.IDEOMPlugin";
    }

}
