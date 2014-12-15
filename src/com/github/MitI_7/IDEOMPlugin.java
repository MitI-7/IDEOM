package com.github.MitI_7;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;


public class IDEOMPlugin implements ApplicationComponent,Configurable{
    private IDEOMConfigPanel ideomConfigPanel;
    private IDEOMConfig ideomConfig;
    private EditorListener editorListener;

    public IDEOMPlugin() {
        super();
    }

    public void initComponent() {
        ideomConfig = IDEOMConfig.getInstance();

        // editorListenerの設定
        editorListener = new EditorListener(ideomConfig.state);
        EditorFactory.getInstance().addEditorFactoryListener(editorListener, new Disposable() {
            @Override
            public void dispose() {
                editorListener = null;
            }
        });

    }

    public void disposeComponent() {
        if (editorListener != null) {
            EditorFactory.getInstance().addEditorFactoryListener(editorListener, new Disposable() {
                @Override
                public void dispose() {
                    editorListener = null;
                }
            });
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
        ideomConfig.state = ideomConfigPanel.get_state();
        editorListener.state = ideomConfig.state;
    }

    // Cancel button
    public void reset() {
    }

    public boolean isModified() {
        // 設定パネルの状態と設定クラスの状態を比較
        if (ideomConfigPanel.get_state().equals(ideomConfig.state)) {
            return false;
        }
        return true;
    }

    // user closes the form
    public void disposeUIResources() {
        ideomConfigPanel = null;
    }

    public String getHelpTopic() {
        return "plugins.IDEOMPlugin";
    }
}

