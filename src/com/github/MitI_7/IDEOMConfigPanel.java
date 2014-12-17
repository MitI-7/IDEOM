package com.github.MitI_7;

import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;
    private JCheckBox useWallPaperCheckBox;
    private TextFieldWithBrowseButton imagePath;
    private JSlider imageOpacitySlider;
    private JComboBox imagePositionComboBox;
    private JComboBox editorNameComboBox;

    public IDEOMConfig.State state;

    public IDEOMConfigPanel(IDEOMConfig.State state) {
        this.state = state;
        Setting setting = state.editorSetting.get("Default");
        set_setting(setting);

        // 設定にあるeditorNameをすべて追加
        for (String editorName : state.editorSetting.keySet()) {
            if (editorName.equals("Default")) {continue;}
            editorNameComboBox.addItem(editorName);
        }

        imagePath.addBrowseFolderListener(
                "Select Image File", "", null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
        editorNameComboBox.addActionListener(new Act());
    }

    // editorNameComboBoxで選択された設定を設定する
    class Act implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            set_setting(state.editorSetting.get(editorName));
        }
    }

    public Setting get_setting() {
        Setting setting = new Setting();
        setting.editorName      = (String)this.editorNameComboBox.getSelectedItem();
        setting.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        setting.imagePath       = this.imagePath.getText();
        setting.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        setting.imagePositionNo = this.imagePositionComboBox.getSelectedIndex();

        return setting;
    }

    private void set_setting(Setting setting) {
        this.useWallPaperCheckBox.setSelected(setting.useWallPaper);
        this.imagePath.setText(setting.imagePath);
        this.imageOpacitySlider.setValue((int)(setting.imageOpacity * 100));
        this.imagePositionComboBox.setSelectedIndex(setting.imagePositionNo);
    }

    public JPanel get_panel() {
        return panel;
    }

}
