/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hpg.auth.config.UrlPrivilegeConfig;
import org.hpg.common.biz.annotation.MendelAction;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Utilities for Auth module
 *
 * @author trungpt
 */
public class AuthUtil {

    /**
     * Parse UrlPrivilegeConfig for mapping from URL string to privileges
     *
     * @return
     */
    public static Map<String, List<MendelPrivilege>> getUrlPrivilesMap() {
        List<Field> urlFields = Arrays.asList(UrlPrivilegeConfig.class.getDeclaredFields());
        return urlFields.stream()
                .collect(Collectors.toMap(
                        field -> {
                            try {
                                // Get static field value
                                return (String) field.get(null);
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(AuthUtil.class.getName()).log(Level.SEVERE, null, ex);
                                return "unknown";
                            }
                        },
                        field -> Arrays.asList(field.getAnnotation(MendelAction.class).privileges()),
                        (s1, s2) -> s1
                ));
    }

    /**
     * Get Spring-sec form authorities from app-domain role and privileges
     *
     * @param role
     * @param privileges
     * @return
     */
    public static List<GrantedAuthority> getGrantedAuthoritiesFromRoleAndPrivileges(MendelRole role, List<MendelPrivilege> privileges) {
        List<GrantedAuthority> authorities = privileges
                .stream()
                .distinct()
                .map(priv -> new SimpleGrantedAuthority(priv.getCode()))
                .collect(Collectors.toList());

        // Manually add the default role - a trick for Spring-sec ?
        authorities.add(new SimpleGrantedAuthority(("ROLE_" + role.getName())));

        return authorities;
    }
}
