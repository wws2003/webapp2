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
import org.hpg.auth.constant.AuthUrls;
import org.hpg.common.biz.annotation.MendelAction;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.sec.MendelActionSecurity;
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
     * @param urlPrivilegeConfigClass Class ofwhich static fields declare URL
     * and privileges setting
     * @return
     */
    public static Map<String, MendelActionSecurity> getUrlPrivilesMap(Class urlPrivilegeConfigClass) {
        List<Field> urlFields = Arrays.asList(urlPrivilegeConfigClass.getDeclaredFields());
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
                        field -> new MendelActionSecurity(
                                Arrays.asList(field.getAnnotation(MendelAction.class).privileges()),
                                field.getAnnotation(MendelAction.class).requiredAllPrivileges()
                        ),
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

    /**
     * Get URL of action in Auth module (starting with Auth module root URL)
     *
     * @param functionUrl
     * @return
     */
    public static String getUrlInAuthDomain(String functionUrl) {
        return AuthUrls.AUTH_ROOT_URL + functionUrl;
    }

    /**
     * Get URL of action for ADMIN actions (starting with ADMIN module root URL)
     *
     * @param functionUrl
     * @return
     */
    public static String getUrlInAdminDomain(String functionUrl) {
        return AuthUrls.AdminRole.PREFIX + functionUrl;
    }

    /**
     * Get URL of action for USER actions (starting with ADMIN module root URL)
     *
     * @param functionUrl
     * @return
     */
    public static String getUrlInUserDomain(String functionUrl) {
        return AuthUrls.UserRole.PREFIX + functionUrl;
    }
}
