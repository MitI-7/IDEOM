package com.github.MitI_7;


import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;

    public JCheckBox useWallPaperCheckBox;
    public JTextField imagePathTextField;
    public JSlider imageOpacitySlider;
    public JComboBox imagePositionComboBox;
    private JComboBox comboBox1;

    public IDEOMConfigPanel(IDEOMConfig ideomConfig) {
        useWallPaperCheckBox.setSelected(ideomConfig.state.useWallPaper);
        imagePathTextField.setText(ideomConfig.state.imagePath);
        imageOpacitySlider.setValue((int)(ideomConfig.state.imageOpacity * 100));
        imagePositionComboBox.setSelectedIndex(ideomConfig.state.imagePositionNo);
    }

    public IDEOMConfig.State get_state() {
        IDEOMConfig.State state = new IDEOMConfig.State();
        state.useWallPaper    = this.useWallPaperCheckBox.isSelected();
        state.imagePath       = this.imagePathTextField.getText();
        state.imageOpacity    = this.imageOpacitySlider.getValue() / 100.0f;
        state.imagePositionNo = this.imagePositionComboBox.getSelectedIndex();

        return state;
    }

    public JPanel get_panel() {
        return panel;
    }

}
