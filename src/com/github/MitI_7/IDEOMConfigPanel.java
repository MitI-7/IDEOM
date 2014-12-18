package com.github.MitI_7;

import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;
    private JComboBox editorNameComboBox;
    private JCheckBox useWallPaperCheckBox;
    private TextFieldWithBrowseButton imagePath;
    private JSlider imageOpacitySlider;
    private JButton addEditorNameButton;
    private JButton deleteEditorNameButton;
    private JComboBox imageHorizonPositionComboBox;
    private JComboBox imageVerticalPositionComboBox;

    public IDEOMConfig.State state;

    public IDEOMConfigPanel(IDEOMConfig.State state) {
        this.state = state;
        Setting setting = state.editorSetting.get("Default");
        deleteEditorNameButton.setEnabled(false);
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
        editorNameComboBox.addActionListener(new SelectEditorName());
        addEditorNameButton.addActionListener(new AddEditorName());
        deleteEditorNameButton.addActionListener(new DeleteEditorName());
    }

    // editorNameComboBoxで選択された設定を設定する
    private class SelectEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            set_setting(state.editorSetting.get(editorName));

            if (editorName.equals("Default")) {
                deleteEditorNameButton.setEnabled(false);
            }
            else {
                deleteEditorNameButton.setEnabled(true);
            }
        }
    }

    private class AddEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            final String editorName = Messages.showInputDialog("Create New Editor Setting", "Input Dialog", null, "",
                    new InputValidator() {
                        // FIX:ダイアログが生成されたときはOKが有効になってる(押せはしない)けどどう直すの？
                         public boolean checkInput(String inputString) {
                             if (inputString == null || inputString.equals("")) { return false; }
                             if (inputString.length() >= 20) {return false;}
                             if (state.editorSetting.keySet().contains(inputString)) {return false;}
                             else {return true;}
                         }
                         public boolean canClose(String inputString) { return true; }
                     });

            if (editorName == null) {
                return;
            }
            editorNameComboBox.addItem(editorName);
            Setting s = new Setting();
            state.editorSetting.put(editorName, s);
            editorNameComboBox.setSelectedItem(editorName);
            set_setting(s);
        }
    }

    private class DeleteEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            if (0 == Messages.showYesNoDialog("Delete " + editorName, "Caution!", null)) {
                editorNameComboBox.removeItem(editorName);
                state.editorSetting.remove(editorName);
                editorNameComboBox.setSelectedIndex(0);
                set_setting(state.editorSetting.get("Default"));
            }
        }
    }

    public Setting get_setting() {
        Setting setting = new Setting();
        setting.editorName      = (String)this.editorNameComboBox.getSelectedItem();
        setting.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        setting.imagePath       = this.imagePath.getText();
        setting.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        setting.imageHorizonPositionNo = this.imageHorizonPositionComboBox.getSelectedIndex();
        setting.imageVerticalPositionNo = this.imageVerticalPositionComboBox.getSelectedIndex();

        return setting;
    }

    private void set_setting(Setting setting) {
        this.useWallPaperCheckBox.setSelected(setting.useWallPaper);
        this.imagePath.setText(setting.imagePath);
        this.imageOpacitySlider.setValue((int)(setting.imageOpacity * 100));
        this.imageHorizonPositionComboBox.setSelectedIndex(setting.imageHorizonPositionNo);
        this.imageVerticalPositionComboBox.setSelectedIndex(setting.imageVerticalPositionNo);
    }

    public JPanel get_panel() {
        return panel;
    }

}
