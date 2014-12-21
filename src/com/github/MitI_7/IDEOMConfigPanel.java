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
    private JButton addEditorNameButton;
    private JButton deleteEditorNameButton;
    private JCheckBox useWallPaperCheckBox;
    private TextFieldWithBrowseButton imagePath;
    private JSlider imageOpacitySlider;
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
        EditorSetting editorSetting = this.state.editorSetting.get(EditorSetting.DEFALUT);
        set_editorSetting(editorSetting);
        set_editorOptionEnable(useWallPaperCheckBox.isSelected());
        deleteEditorNameButton.setEnabled(false);

        // 設定にあるeditorNameをコンボボックスにすべて追加
        for (String editorName : state.editorSetting.keySet()) {
            if (editorName.equals(EditorSetting.DEFALUT)) {continue;}
            editorNameComboBox.addItem(editorName);
        }

        useWallPaperCheckBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_editorOptionEnable(useWallPaperCheckBox.isSelected());
            }
        });

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
        SoundSetting soundSetting = this.state.soundSetting.get(SoundSetting.PROJECTOPEN);
        set_soundSetting(soundSetting);
        set_soundOptionEnable(useSoundCheckBox.isSelected());

        // 設定にあるeventをコンボボックスにすべて追加
        for (String eventName : SoundSetting.eventNameList) {
            eventNameComboBox.addItem(eventName);
        }

        useSoundCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set_soundOptionEnable(useSoundCheckBox.isSelected());
            }
        });

        soundPath.addBrowseFolderListener(
                "Select Image File", "", null,
                new FileChooserDescriptor(true, false, false, false, false, false)
        );
        eventNameComboBox.addActionListener(new SelectEventName());
        soundPlayButton.addActionListener(new SoundPlay());
    }

    // editorNameComboBoxで選択された設定を設定する
    private class SelectEditorName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String editorName = (String)editorNameComboBox.getSelectedItem();
            set_editorSetting(state.editorSetting.get(editorName));
            set_editorOptionEnable(state.editorSetting.get(editorName).useWallPaper);

            if (editorName.equals(EditorSetting.DEFALUT)) {
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
                set_editorSetting(state.editorSetting.get(EditorSetting.DEFALUT));
            }
        }
    }

    // eventNameComboBoxで選択された設定を設定する
    private class SelectEventName implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String eventName = (String)eventNameComboBox.getSelectedItem();
            set_soundSetting(state.soundSetting.get(eventName));
            set_soundOptionEnable(state.soundSetting.get(eventName).useSound);
        }
    }

    private class SoundPlay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SoundPlayer.play(soundPath.getText(), soundVolumeSlider.getValue() / 100.0f);

        }
    }

    private void set_editorOptionEnable(boolean b) {
        imagePath.setEditable(b);
        imageOpacitySlider.setEnabled(b);
        imageHorizonPositionComboBox.setEnabled(b);
        imageVerticalPositionComboBox.setEnabled(b);
    }

    private void set_soundOptionEnable(boolean b) {
        soundPath.setEnabled(b);
        soundPlayButton.setEnabled(b);
        soundVolumeSlider.setEnabled(b);
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
