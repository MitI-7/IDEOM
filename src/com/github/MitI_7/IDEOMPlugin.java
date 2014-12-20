package com.github.MitI_7;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;


public class IDEOMPlugin implements ApplicationComponent,Configurable {
    private IDEOMConfigPanel ideomConfigPanel;
    private IDEOMConfig ideomConfig;

    public IDEOMPlugin() {
        super();
    }

    public void initComponent() {
        ideomConfig = IDEOMConfig.getInstance();
    }

    public void disposeComponent() {
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
        if (ideomConfigPanel == null && ideomConfig != null && ideomConfig.state != null) {
            ideomConfigPanel = new IDEOMConfigPanel(ideomConfig.state);
        }
        return ideomConfigPanel.get_panel();
    }

    // OK or Apply button
    public void apply() {
        EditorSetting nowEditorSetting = ideomConfigPanel.get_editorSetting();
        ideomConfig.state.editorSetting.put(nowEditorSetting.editorName, nowEditorSetting);

        SoundSetting nowSoundSetting = ideomConfigPanel.get_soundSetting();
        ideomConfig.state.soundSetting.put(nowSoundSetting.eventName, nowSoundSetting);
    }

    // Cancel button
    public void reset() {
    }

    public boolean isModified() {
        // 設定パネルの状態と設定クラスの状態を比較(表示しているEditorNameのもののみ)
        EditorSetting nowEditorSetting = ideomConfigPanel.get_editorSetting();
        if (!nowEditorSetting.equals(ideomConfig.state.editorSetting.get(nowEditorSetting.editorName))) {
            return true;
        }

        SoundSetting nowSoundSetting = ideomConfigPanel.get_soundSetting();
        if (!nowSoundSetting.equals(ideomConfig.state.soundSetting.get(nowSoundSetting.eventName))) {
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

