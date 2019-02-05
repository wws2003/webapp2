/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * Test for logging spring security stuffs
 *
 * @author wws2003
 */
public class LogTest {

    public LogTest() {
    }

    @Test
    public void testDebug() {
        Log logger1 = LogFactory.getLog(getClass());
        Log logger2 = LogFactory.getLog(new DaoAuthenticationProvider().getClass());
        // Debug
        logger1.debug("Something to debug");
        logger2.debug("Something to debug from Spring Sec");
        // Info
        logger1.info("Some info");
        logger2.info("Some info from Spring Sec");
        // Error
        logger1.error("Some error");
        logger2.error("Some error from Spring Sec");
    }
}
