package com.github.MitI_7;


import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;
    private JCheckBox useWallPaperCheckBox;
    private TextFieldWithBrowseButton imagePath;
    private JSlider imageOpacitySlider;
    private JComboBox imagePositionComboBox;
    private JComboBox EditorNameComboBox;

    public IDEOMConfig.State state;

    public IDEOMConfigPanel(IDEOMConfig.State state) {
        this.state = state;
        Setting setting = state.editorSetting.get("Text Editor");
        set_setting(setting);

        imagePath.addBrowseFolderListener(
                "Title", "Description", null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
    }

    public Setting get_setting() {
        Setting setting = new Setting();
        setting.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        setting.imagePath       = this.imagePath.getText();
        setting.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        setting.imagePositionNo = this.imagePositionComboBox.getSelectedIndex();

        return setting;
    }

    public void set_setting(Setting setting) {
        useWallPaperCheckBox.setSelected(setting.useWallPaper);
        imagePath.setText(setting.imagePath);
        imageOpacitySlider.setValue((int)(setting.imageOpacity * 100));
        imagePositionComboBox.setSelectedIndex(setting.imagePositionNo);
    }

    public JPanel get_panel() {
        return panel;
    }

}
