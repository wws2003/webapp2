/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.hpg.auth.biz.service.impl.SessionAuthenticationFailureHandlerImpl;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.sec.MendelActionSecurity;
import org.hpg.libcommon.Tuple;
import org.hpg.libcommon.Tuple2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.session.SessionRegistry;

/**
 * Wrapper for HttpSecurity config
 *
 * @author trungpt
 */
public class HttpSecurityRolePrivsConfigurer {

    /**
     * Reg instance
     */
    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry mAuthorizeReg = null;

    /**
     * HttpSecurity instance
     */
    private final HttpSecurity mHttpSecurity;

    /**
     * Configuring role
     */
    private MendelRole mRole = MendelRole.INVALID;

    private HttpSecurityRolePrivsConfigurer(HttpSecurity httpSecurity) {
        mHttpSecurity = httpSecurity;
    }

    /**
     * Configure session
     *
     * @param maximumSessions
     * @param sessionRegistry
     * @return
     * @throws java.lang.Exception
     */
    public HttpSecurityRolePrivsConfigurer sessionManagement(int maximumSessions, SessionRegistry sessionRegistry) throws Exception {
        mHttpSecurity.sessionManagement()
                .sessionAuthenticationFailureHandler(new SessionAuthenticationFailureHandlerImpl()) // FOR EXPERIMENT
                .maximumSessions(maximumSessions)
                .sessionRegistry(sessionRegistry)
                .maxSessionsPreventsLogin(true);
        return this;
    }

    public HttpSecurityRolePrivsConfigurer forUserRole() throws Exception {
        // TODO Implement
        mAuthorizeReg = mHttpSecurity.antMatcher("/user/**").authorizeRequests();
        mRole = MendelRole.USER;
        return this;
    }

    public HttpSecurityRolePrivsConfigurer forAdminRole() throws Exception {
        mAuthorizeReg = mHttpSecurity.authorizeRequests();
        mRole = MendelRole.ADMIN;
        return this;
    }

    public HttpSecurityRolePrivsConfigurer forUrlPrivileges(Map<String, MendelActionSecurity> urlPrivilegesMap) throws Exception {
        // TODO Implement
        mAuthorizeReg = this.getUrlInterceptRegistryForPrivilges(mAuthorizeReg, urlPrivilegesMap);
        // Finallize with role-dependent setting
        if (mRole == MendelRole.USER) {
            mAuthorizeReg.antMatchers("/user/**").hasRole(MendelRole.USER.getName());
        }
        if (mRole == MendelRole.ADMIN) {
            mAuthorizeReg.antMatchers("/admin/**").hasRole(MendelRole.ADMIN.getName());
        }
        return this;
    }

    /**
     * Get final ExpressionInterceptUrlRegistry
     *
     * @return
     */
    public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry buildInterceptUrlRegistry() {
        return mAuthorizeReg;
    }

    /**
     * Get URL interceptor registry privileges
     *
     * @param reg
     * @param urlPrivilegesMap
     * @return
     */
    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry getUrlInterceptRegistryForPrivilges(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg,
            Map<String, MendelActionSecurity> urlPrivilegesMap) {

        BiFunction<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry, Tuple2<String, MendelActionSecurity>, ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry> accessStrConfFunc = (rg, urlPrivTuple) -> {
            // Sample: hasAuthority('A1') and hasAuthority('A2')
            String privDelim = urlPrivTuple.getItem2().isAllPrivilegesRequired() ? " and " : " or ";
            String accessStr = urlPrivTuple.getItem2()
                    .getPrivileges()
                    .stream()
                    .map(MendelPrivilege::getCode)
                    .map(priv -> "hasAuthority('" + priv + "')")
                    .collect(Collectors.joining(privDelim));
            return reg.antMatchers(urlPrivTuple.getItem1()).access(accessStr);
        };

        // Stream API 'reduce' method does not guarantee to execute sequentially so temporary need to use for loop
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry ret = reg;
        for (Map.Entry<String, MendelActionSecurity> entry : urlPrivilegesMap.entrySet()) {
            ret = accessStrConfFunc.apply(ret, Tuple.newTuple(entry.getKey(), entry.getValue()));
        }
        return ret;
    }

    /**
     * Get new instance
     *
     * @param httpSecurity
     * @return
     */
    public static HttpSecurityRolePrivsConfigurer instance(HttpSecurity httpSecurity) {
        return new HttpSecurityRolePrivsConfigurer(httpSecurity);
    }
}
