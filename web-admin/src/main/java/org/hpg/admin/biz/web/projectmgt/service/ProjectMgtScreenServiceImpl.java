/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.service;

import java.util.List;
import org.hpg.admin.biz.web.projectmgt.form.ProjectDetailForm;
import org.hpg.admin.biz.web.projectmgt.form.ProjectsIndexForm;
import org.hpg.admin.biz.web.projectmgt.form.UserSearchForm;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnUserTag;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.stereotype.Service;

/**
 * Project management screen service
 *
 * @author trungpt
 */
@Service
public class ProjectMgtScreenServiceImpl implements IProjectMgtScrnService {

    @Override
    public AjaxResult index(ProjectsIndexForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult searchForUsers(UserSearchForm form) throws MendelRuntimeException {
        // TODO Implement
        String text = form.getUserText();
        List<ScrnUserTag> resultTags = null;
        return AjaxResultBuilder.successInstance()
                .resultObject(resultTags)
                .build();
    }

    @Override
    public AjaxResult getProjectDetail(ProjectDetailForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
