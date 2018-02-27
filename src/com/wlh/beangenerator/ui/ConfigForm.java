/*
 * Copyright (c) 2017-present, wlh
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.wlh.beangenerator.ui;



import com.wlh.beangenerator.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by weilh on 2018/2/6.
 */
public class ConfigForm extends JFrame{
    private JTextArea beanTextArea;
    private JPanel root;
    private JCheckBox aBCheckBox;
    private JButton oKButton;
    private JTextField NamePositionTextField;
    private JTextField typeTextField;
    private JTextField describeTextField;
    private JTextField classNameText;
    private JCheckBox firstLetterToLowCheckBox;
    private JTextField lineSplitText;
    private JTextField blockSplitBlock;


    public ConfigForm() {
        super("ConfigForm");
        setContentPane(root);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void setOnClickListener(OnClickListener listener) {
        oKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (listener != null) {
                    Config config = new Config();
                    config.forceToAb = aBCheckBox.isSelected();
                    config.namePosition = getFromText(NamePositionTextField);
                    config.typePosition = getFromText(typeTextField);
                    config.describePosition = getFromText(describeTextField);
                    config.className = classNameText.getText();
                    config.firstToLow = firstLetterToLowCheckBox.isEnabled();
                    config.lineSplit = lineSplitText.getText();
                    config.blockSplit = blockSplitBlock.getText();
                    listener.onClick(beanTextArea.getText(), config);
                }
            }
        });
    }

    private int getFromText(JTextField textField) {
        return Integer.parseInt(textField.getText());
    }

    public interface OnClickListener {
        void onClick(String beanText, Config config);
    }
}
