/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web;

import org.hpg.admin.biz.web.form.UserAddUpdateForm;
import org.hpg.admin.constant.AdminUrls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for user management actions
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ROOT_URL)
public class UserMgtController {

    /**
     * Add/Update user action
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ADD_UPDATE)
    public String addUpdateUser(UserAddUpdateForm form) {
        // TODO Implement
        return "admin/userAddUpdate";
    }
}
