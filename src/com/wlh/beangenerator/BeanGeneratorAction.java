/*
 * Copyright (c) 2017-present, wlh
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.wlh.beangenerator;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.wlh.beangenerator.ui.ConfigForm;

/**
 *
 * Created by weilh on 2018/2/6.
 */
public class BeanGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        IdeView ideView = anActionEvent.getRequiredData(LangDataKeys.IDE_VIEW);
        PsiDirectory psiDirectory = ideView.getOrChooseDirectory();
        Project project = anActionEvent.getProject();

        ConfigForm form = new ConfigForm();
        form.setOnClickListener(new ConfigForm.OnClickListener() {
            @Override
            public void onClick(String beanText, Config config) {
                new BeanClassGenerator(project, psiDirectory).generate(beanText, config);
            }
        });
        form.setVisible(true);
    }
}
