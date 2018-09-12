/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.hpg.auth.biz.service.impl.WrapperClass.In1.In2;
import org.hpg.auth.biz.service.impl.WrapperClass.In3;
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

    @Test
    public void testNewClazz() {
        try {
            WrapperClass wrapper = WrapperClass.class.newInstance();
            wrapper.printSth();

            In3 in3 = WrapperClass.In3.class.newInstance();
            in3.printSth();

            In2 in2 = org.hpg.auth.biz.service.impl.WrapperClass.In1.In2.class.newInstance();
            System.out.println(in2.getVal());
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

}
