/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        PasswordEncoder encoderImpl = new BCryptPasswordEncoder(11);
        String pass = "system1234";
        System.err.println("For " + pass + ", -----------Encoded password = " + encoderImpl.encode(pass));
        pass = "pass1";
        System.err.println("For " + pass + ", -----------Encoded password = " + encoderImpl.encode(pass));
    }

}
