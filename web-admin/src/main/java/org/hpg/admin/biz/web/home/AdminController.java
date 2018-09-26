/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.home;

import java.util.HashMap;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.message.RecentLoginStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for admin pages
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_ROOT_URL)
public class AdminController {

    @Autowired
    private IUserSession userSession;

    @Autowired
    private IUserService userService;

    @Autowired
    private IScreenService screenService;

    /**
     * Home page for admin
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_HOME)
    public String home(Model model) {
        model.addAttribute("userName", userSession.getCurrentLoginInfo().getLoginUser().getName());
        return "admin/home";
    }

    /**
     * User management page
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_USER_MANAGEMENT)
    public ModelAndView userManagement() {
        // First attempt to use ModelAndView as result, without using any meaningful form
        // One page used for both normal view and error
        String userMgtPage = "admin/userManagement";
        // Execute for result
        return screenService.executeSyncForModelAndView((Long) 0L,
                MendelTransactionalLevel.DEFAULT_READONLY,
                fm -> new ModelAndView(userMgtPage),
                userMgtPage);
    }

    /**
     * This is to listen to client sending data
     *
     * @return
     * @throws java.lang.Exception
     */
    @MessageMapping("/loginCheck")
    @SendTo("/topic/loginCheck")
    public RecentLoginStatusMessage fff() throws Exception {
        // This does not get called after being triggered
        Thread.sleep(1000); // simulated delay
        RecentLoginStatusMessage msg = new RecentLoginStatusMessage();
        return msg;
    }

    /**
     * System management page
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_SYSTEM_MANAGEMENT)
    public ModelAndView systemManagement() {
        return new ModelAndView("admin/systemManagement", new HashMap());
    }
}
