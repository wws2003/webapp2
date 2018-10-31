/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.service;

import java.util.List;
import java.util.stream.Collectors;
import org.hpg.admin.biz.web.common.form.PageRequestForm;
import org.hpg.admin.biz.web.common.form.SimpleDeleteByIDForm;
import org.hpg.admin.biz.web.common.form.SimpleRequestByIDForm;
import org.hpg.admin.biz.web.projectmgt.form.ProjectAddUpdateForm;
import org.hpg.admin.biz.web.projectmgt.form.UserSearchForm;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnUserTag;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Project management screen service
 *
 * @author trungpt
 */
@Service
public class ProjectMgtScreenServiceImpl implements IProjectMgtScrnService {

    @Autowired
    private IUserService userService;

    @Override
    public AjaxResult index(PageRequestForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult getProjectDetail(SimpleRequestByIDForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult addUpdateProject(ProjectAddUpdateForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult deleteProjects(SimpleDeleteByIDForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult searchForUsers(UserSearchForm form) throws MendelRuntimeException {
        String text = form.getUserText();
        List<ScrnUserTag> resultTags = userService.findUsers(text)
                .stream()
                .map(mendelUser -> {
                    ScrnUserTag tag = new ScrnUserTag();
                    tag.setId(mendelUser.getId());
                    tag.setName(mendelUser.getName());
                    tag.setDispName(mendelUser.getDispName());
                    return tag;
                })
                .collect(Collectors.toList());
        return AjaxResultBuilder.successInstance()
                .resultObject(resultTags)
                .build();
    }
}
