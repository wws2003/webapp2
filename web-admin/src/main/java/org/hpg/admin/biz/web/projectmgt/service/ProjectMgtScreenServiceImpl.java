/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.hpg.admin.biz.web.common.form.PageRequestForm;
import org.hpg.admin.biz.web.common.form.SimpleDeleteByIDForm;
import org.hpg.admin.biz.web.common.form.SimpleRequestByIDForm;
import org.hpg.admin.biz.web.projectmgt.form.ProjectAddUpdateForm;
import org.hpg.admin.biz.web.projectmgt.form.UserSearchForm;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnProjectRecord;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnUserTag;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.biz.service.abstr.IProjectService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelProjectStatus;
import org.hpg.common.constant.MendelReferScope;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IPagingService<MendelProject> projectPagingService;

    @Override
    public AjaxResult index(PageRequestForm form) throws MendelRuntimeException {
        List<Sort.Order> orders = Arrays.asList(Sort.Order.desc("cDate"));
        Sort sort = Sort.by(orders);
        Pageable pageRequest = PageRequest.of(form.getPageNumber() - 1,
                form.getRecordCountPerPage(),
                sort);

        // Get data
        Page<ScrnProjectRecord> currentPage = projectPagingService.getPage(pageRequest)
                .map(prj -> new ScrnProjectRecord(prj));

        return AjaxResultBuilder.successInstance()
                .resultObject(currentPage)
                .build();
    }

    @Override
    public AjaxResult getProjectDetail(SimpleRequestByIDForm form) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult addUpdateProject(ProjectAddUpdateForm form) throws MendelRuntimeException {
        MendelProject project = parseProjectDtoFromForm(form);
        // 1. Create new

        // 2. Assign users
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AjaxResult deleteProjects(SimpleDeleteByIDForm form) throws MendelRuntimeException {
        projectService.deleteProjects(form.getElementIds());
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Project have been successfully deleted")
                .build();
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

    /**
     * Parse from add/update form the DTO instance
     *
     * @param form
     * @return
     */
    private MendelProject parseProjectDtoFromForm(ProjectAddUpdateForm form) {
        // TODO Implement properly based on annotations
        MendelProject project = new MendelProject();
        project.setCode(form.getCode());
        project.setId(form.getProjectId());
        project.setDisplayedName(form.getDisplayedName());
        project.setDescription(form.getDescription());
        project.setReferScope(MendelReferScope.getProjectReferScopeByCode(form.getReferScopeCode()));
        project.setStatus(MendelProjectStatus.getProjectStatusByCode(form.getStatusCode()));
        return project;
    }
}
