package com.github.MitI_7;

import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;
    // editor
    private JComboBox editorNameComboBox;
    private JCheckBox useWallPaperCheckBox;
    private TextFieldWithBrowseButton imagePath;
    private JSlider imageOpacitySlider;
    private JButton addEditorNameButton;
    private JButton deleteEditorNameButton;
    private JComboBox imageHorizonPositionComboBox;
    private JComboBox imageVerticalPositionComboBox;

    // sound
    private JComboBox eventNameComboBox;
    private JCheckBox useSoundCheckBox;
    private TextFieldWithBrowseButton soundPath;
    private JButton soundPlayButton;
    private JSlider soundVolumeSlider;

    public IDEOMConfig.State state;

    public IDEOMConfigPanel(IDEOMConfig.State state) {
        this.state = state;

        /*
        editor
         */
        EditorSetting editorSetting = state.editorSetting.get("Default");
        set_editorSetting(editorSetting);
        deleteEditorNameButton.setEnabled(false);

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


        /*
        sound
         */
        SoundSetting soundSetting = this.state.soundSetting.get("Compile Success");
        set_soundSetting(soundSetting);
        soundPath.addBrowseFolderListener(
                "Select Image File", "", null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
        eventNameComboBox.addActionListener(new SelectEventName());
        soundPlayButton.addActionListener(new SoundPlay());

        // 設定にあるeventをすべて追加
        for (String eventName : SoundSetting.eventNameList) {
            eventNameComboBox.addItem(eventName);
        }
    }

    // editorNameComboBoxで選択された設定を設定する
    private class SelectEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            set_editorSetting(state.editorSetting.get(editorName));

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
            final String editorName = Messages.showInputDialog(
                    "Create New Editor Setting", "Input Dialog", null, "",
                    new InputValidator() {
                        // FIX:ダイアログが生成されたときはOKが有効になってる(押せはしない)けどどう直すの？
                         public boolean checkInput(String inputString) {
                             if (inputString == null || inputString.equals("")) { return false; }
                             if (inputString.length() >= 20) {return false;}
                             if (state.editorSetting.keySet().contains(inputString)) {return false;}
                             else {return true;}
                         }
                         public boolean canClose(String inputString) { return true; }
                     }
            );

            if (editorName == null) {
                return;
            }
            editorNameComboBox.addItem(editorName);
            EditorSetting s = new EditorSetting();
            state.editorSetting.put(editorName, s);
            editorNameComboBox.setSelectedItem(editorName);
            set_editorSetting(s);
        }
    }

    private class DeleteEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            if (0 == Messages.showYesNoDialog("Delete " + editorName, "Caution!", null)) {
                editorNameComboBox.removeItem(editorName);
                state.editorSetting.remove(editorName);
                editorNameComboBox.setSelectedIndex(0);
                set_editorSetting(state.editorSetting.get("Default"));
            }
        }
    }

    // eventNameComboBoxで選択された設定を設定する
    private class SelectEventName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String eventName = (String)eventNameComboBox.getSelectedItem();
            set_soundSetting(state.soundSetting.get(eventName));
        }
    }

    private class SoundPlay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SoundPlayer.play(soundPath.getText(), soundVolumeSlider.getValue() / 100.0f);

        }
    }

    public EditorSetting get_editorSetting() {
        EditorSetting editorSetting = new EditorSetting();
        editorSetting.editorName      = (String)this.editorNameComboBox.getSelectedItem();
        editorSetting.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        editorSetting.imagePath       = this.imagePath.getText();
        editorSetting.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        editorSetting.imageHorizonPositionNo = this.imageHorizonPositionComboBox.getSelectedIndex();
        editorSetting.imageVerticalPositionNo = this.imageVerticalPositionComboBox.getSelectedIndex();

        return editorSetting;
    }

    public SoundSetting get_soundSetting() {
        SoundSetting soundSetting = new SoundSetting();
        soundSetting.eventName = (String)this.eventNameComboBox.getSelectedItem();
        soundSetting.useSound = this.useSoundCheckBox.isSelected();
        soundSetting.soundPath = this.soundPath.getText();
        soundSetting.soundVolume = this.soundVolumeSlider.getValue() / 100.0f;

        return soundSetting;
    }

    private void set_editorSetting(EditorSetting editorSetting) {
        this.useWallPaperCheckBox.setSelected(editorSetting.useWallPaper);
        this.imagePath.setText(editorSetting.imagePath);
        this.imageOpacitySlider.setValue((int)(editorSetting.imageOpacity * 100));
        this.imageHorizonPositionComboBox.setSelectedIndex(editorSetting.imageHorizonPositionNo);
        this.imageVerticalPositionComboBox.setSelectedIndex(editorSetting.imageVerticalPositionNo);
    }

    public void set_soundSetting(@NotNull SoundSetting soundSetting) {
        this.useSoundCheckBox.setSelected(soundSetting.useSound);
        this.soundPath.setText(soundSetting.soundPath);
        this.soundVolumeSlider.setValue((int)(soundSetting.soundVolume * 100));
    }

    public JPanel get_panel() {
        return panel;
    }

}
