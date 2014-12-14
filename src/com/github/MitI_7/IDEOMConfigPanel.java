package com.github.MitI_7;

import javax.swing.*;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.util.StringTokenizer;
import java.io.File;


public class IDEOMConfigPanel extends JComponent{
    private JPanel panel;

    public JCheckBox useWallPaperCheckBox;
    public JTextField imagePathTextField;
    public JSlider imageOpacitySlider;

    public IDEOMConfigPanel() {
    }

    public IDEOMConfigPanel(IDEOMConfig ideomConfig) {
        useWallPaperCheckBox.setSelected(ideomConfig.useWallPaper);
        imagePathTextField.setText(ideomConfig.imagePath);
        imageOpacitySlider.setValue((int)ideomConfig.imageOpacity);
    }

    public JPanel get_panel() {
        return panel;
    }

}
