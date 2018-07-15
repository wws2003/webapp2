/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Password encoder implementation
 *
 * @author wws2003
 */
public class DefaultPasswordEncoderImpl extends BCryptPasswordEncoder implements PasswordEncoder {
    // Currently just using BCryptPasswordEncoder
}
