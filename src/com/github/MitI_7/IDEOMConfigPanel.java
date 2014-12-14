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

    public IDEOMConfigPanel(IDEOMConfig ideomConfig) {
        useWallPaperCheckBox.setSelected(ideomConfig.useWallPaper);
        imagePathTextField.setText(ideomConfig.imagePath);
        imageOpacitySlider.setValue((int)(ideomConfig.imageOpacity * 100));
        imagePositionComboBox.setSelectedIndex(ideomConfig.imagePositionNo);
    }

    public JPanel get_panel() {
        return panel;
    }

}
