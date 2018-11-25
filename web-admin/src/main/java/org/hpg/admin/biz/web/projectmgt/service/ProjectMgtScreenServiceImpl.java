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
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnProjectDetail;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnProjectRecord;
import org.hpg.admin.biz.web.projectmgt.scrnmodel.ScrnUserTag;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.biz.service.abstr.IProjectService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelProjectStatus;
import org.hpg.common.constant.MendelReferScope;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.libcommon.DateFormatConst;
import org.hpg.libcommon.DateUtil;
import org.hpg.libcommon.Tuple;
import org.hpg.libcommon.Tuple2;
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
        MendelProject project = projectService.findProjectById(form.getElementId())
                .orElseThrow(() -> new MendelRuntimeException("Could not find project by id " + form.getElementId()));

        List<MendelUser> users = projectService.findUsersAssignedToProject(project);

        ScrnProjectDetail projectDetail = createProjectDetail(project, users);

        return AjaxResultBuilder.successInstance()
                .resultObject(projectDetail)
                .build();
    }

    @Override
    public AjaxResult addUpdateProject(ProjectAddUpdateForm form) throws MendelRuntimeException {
        MendelProject project = parseProjectDtoFromForm(form);
        // 1. Create new / Update
        MendelProject savedProject = (form.getIdForm().getElementId() < 0) ? projectService.createProject(project) : projectService.updateProject(project);
        // 2. Assign users
        projectService.assignUsersToProject(savedProject, form.getUserIds());
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Project has been successfully saved")
                .build();
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
        project.setId(form.getIdForm().getElementId());
        project.setDisplayedName(form.getDisplayedName());
        project.setDescription(form.getDescription());
        project.setReferScope(MendelReferScope.getProjectReferScopeByCode(form.getReferScopeCode()));
        project.setStatus(MendelProjectStatus.getProjectStatusByCode(form.getStatusCode()));
        return project;
    }

    /**
     * Create instance of project with detailed info
     *
     * @param project
     * @param users
     * @return
     */
    private ScrnProjectDetail createProjectDetail(MendelProject project, List<MendelUser> users) {
        List<Tuple2<Long, String>> members = users.stream()
                .map(user -> Tuple.newTuple(user.getId(), user.getDispName()))
                .collect(Collectors.toList());

        ScrnProjectDetail projectDetail = new ScrnProjectDetail();
        projectDetail.setId(project.getId());
        projectDetail.setCode(project.getCode());
        projectDetail.setDisplayedName(project.getDisplayedName());
        projectDetail.setDescription(project.getDescription());
        projectDetail.setMembers(members);
        projectDetail.setReferScope(project.getReferScope().getCode());
        projectDetail.setStatus(project.getStatus().getCode());
        projectDetail.setcDateTimeStamp(DateUtil.dateTime2String(project.getcDate(), DateFormatConst.DATE_A_YYMMDD));
        projectDetail.setmDateTimeStamp(DateUtil.dateTime2String(project.getmDate(), DateFormatConst.DATE_A_YYMMDD));

        return projectDetail;
    }
}
