/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.service;

import org.hpg.admin.biz.web.projectmgt.form.ProjectDetailForm;
import org.hpg.admin.biz.web.projectmgt.form.ProjectsIndexForm;
import org.hpg.admin.biz.web.projectmgt.form.UserSearchForm;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Screen service for project management
 *
 * @author trungpt
 */
public interface IProjectMgtScrnService {

    /**
     * Index projects (including paging)
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public AjaxResult index(ProjectsIndexForm form) throws MendelRuntimeException;

    /**
     * Search for users to assign to project
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public AjaxResult searchForUsers(UserSearchForm form) throws MendelRuntimeException;

    /**
     * Get project detail info
     *
     * @param form
     * @return
     * @throws MendelRuntimeException
     */
    public AjaxResult getProjectDetail(ProjectDetailForm form) throws MendelRuntimeException;
}
