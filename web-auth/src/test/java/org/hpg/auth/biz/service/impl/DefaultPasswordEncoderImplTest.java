/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.junit.Test;

/**
 *
 * @author wws2003
 */
public class DefaultPasswordEncoderImplTest {

    public DefaultPasswordEncoderImplTest() {
    }

    @Test
    public void testSomeMethod() {
        // Just a sample to show encoded password
        DefaultPasswordEncoderImpl encoderImpl = new DefaultPasswordEncoderImpl();
        String encodedPassword = encoderImpl.encode("pass001");
        System.err.println("-----------Encoded password = " + encodedPassword);
    }

}
