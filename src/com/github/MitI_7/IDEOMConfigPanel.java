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

    public IDEOMConfigPanel(IDEOMConfig ideomConfig) {
        useWallPaperCheckBox.setSelected(ideomConfig.state.useWallPaper);
        imagePath.setText(ideomConfig.state.imagePath);
        imageOpacitySlider.setValue((int)(ideomConfig.state.imageOpacity * 100));
        imagePositionComboBox.setSelectedIndex(ideomConfig.state.imagePositionNo);
        imagePath.addBrowseFolderListener(
                "Title", "Description", null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
    }

    public IDEOMConfig.State get_state() {
        IDEOMConfig.State state = new IDEOMConfig.State();
        state.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        state.imagePath       = this.imagePath.getText();
        state.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        state.imagePositionNo = this.imagePositionComboBox.getSelectedIndex();

        return state;
    }

    public JPanel get_panel() {
        return panel;
    }

}
