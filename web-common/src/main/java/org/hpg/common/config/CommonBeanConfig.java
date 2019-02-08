/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import java.util.HashMap;
import java.util.Map;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.biz.service.abstr.IPageService;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.biz.service.abstr.IProjectService;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.biz.service.impl.PageServiceImpl;
import org.hpg.common.biz.service.impl.PagingServiceImpl;
import org.hpg.common.biz.service.impl.PrivilegeServiceImpl;
import org.hpg.common.biz.service.impl.ProjectServiceImpl;
import org.hpg.common.biz.service.impl.SampleUserSessionImpl;
import org.hpg.common.biz.service.impl.ScreenServiceImpl;
import org.hpg.common.biz.service.impl.SimpleLoggerImpl;
import org.hpg.common.biz.service.impl.StdFormValidatorImpl;
import org.hpg.common.biz.service.impl.UserServiceImpl;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.mapper.impl.ProjectEntityDtoMapperImpl;
import org.hpg.common.dao.mapper.impl.UserEntityDtoMapperImpl;
import org.hpg.common.dao.repository.IPageRepository;
import org.hpg.common.dao.repository.IProjectRepository;
import org.hpg.common.dao.repository.IProjectUserRepository;
import org.hpg.common.dao.repository.IUserPrivRepository;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.framework.transaction.CurrentReadOnlyTransactionalExecutorImpl;
import org.hpg.common.framework.transaction.CurrentTransactionalExecutorImpl;
import org.hpg.common.framework.transaction.DefaultReadOnlyTransactionalExecutorImpl;
import org.hpg.common.framework.transaction.DefaultTransactionalExecutorImpl;
import org.hpg.common.framework.transaction.ITransactionExecutor;
import org.hpg.common.framework.transaction.NewReadOnlyTransactionalExecutorImpl;
import org.hpg.common.framework.transaction.NewTransactionalExecutorImpl;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.PageEntity;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration (mostly in term of producer) for beans provided by common
 * module
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:application.properties")
})
public class CommonBeanConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IEntityDtoMapper<UserEntity, MendelUser> getUserEntityDtoMapper() {
        return new UserEntityDtoMapperImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IUserService getUserService(IUserRepository userRepository, IUserPrivRepository userPrivRepository, IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper) {
        return new UserServiceImpl(userRepository, userPrivRepository, entityDtoMapper);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IEntityDtoMapper<ProjectEntity, MendelProject> getProjectEtityDtoMapper() {
        return new ProjectEntityDtoMapperImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IProjectService getProjectService(IProjectRepository projectRepository,
            IProjectUserRepository projectUserRepository,
            IEntityDtoMapper<ProjectEntity, MendelProject> projectEntityDtoMapper,
            IEntityDtoMapper<UserEntity, MendelUser> userEntityDtoMapper) {
        return new ProjectServiceImpl(projectRepository, projectUserRepository, projectEntityDtoMapper, userEntityDtoMapper);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IPageService getPageService(IPageRepository pageRepository, IEntityDtoMapper<PageEntity, MendelPage> pageEntityDtoMapper) {
        return new PageServiceImpl(pageRepository, pageEntityDtoMapper);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public IUserSession getSampleUserSession(IUserService userService) {
        // For the case module web-auth not deployed
        return new SampleUserSessionImpl(userService);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IPrivilegeService getPrivilegeService() {
        // TODO Implement properly
        return new PrivilegeServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IFormValidator getFormValidator() {
        return new StdFormValidatorImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ILogger getLogger() {
        int maxTraceLevel = environment.getProperty("logger.max-trace-level", Integer.class);
        return new SimpleLoggerImpl(maxTraceLevel);
    }

    @Bean
    @DependsOn()
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public Map<Integer, ITransactionExecutor> getTransactionExecutorMap() {
        Map<Integer, ITransactionExecutor> transactionExecutorMap = new HashMap();
        transactionExecutorMap.put(MendelTransactionalLevel.DEFAULT.getCode(), getDefaultTransactionalExecutor());
        transactionExecutorMap.put(MendelTransactionalLevel.DEFAULT_READONLY.getCode(), getDefaultReadOnlyTransactionalExecutor());
        transactionExecutorMap.put(MendelTransactionalLevel.NEW.getCode(), getNewTransactionalExecutor());
        transactionExecutorMap.put(MendelTransactionalLevel.NEW_READONLY.getCode(), getNewTransactionalExecutor());
        transactionExecutorMap.put(MendelTransactionalLevel.CURRENT.getCode(), getCurrentTransactionalExecutor());
        transactionExecutorMap.put(MendelTransactionalLevel.CURRENT_READONLY.getCode(), getCurrentReadOnlyTransactionalExecutor());
        return transactionExecutorMap;
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IScreenService getScreenService(IFormValidator formValidator,
            ILogger logger,
            Map<Integer, ITransactionExecutor> transactionExecutorMap) {
        return new ScreenServiceImpl(formValidator, logger, transactionExecutorMap);
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.DEFAULT)
    public ITransactionExecutor getDefaultTransactionalExecutor() {
        return new DefaultTransactionalExecutorImpl();
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.DEFAULT_READONLY)
    public ITransactionExecutor getDefaultReadOnlyTransactionalExecutor() {
        return new DefaultReadOnlyTransactionalExecutorImpl();
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.NEW)
    public ITransactionExecutor getNewTransactionalExecutor() {
        return new NewTransactionalExecutorImpl();
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.NEW_READONLY)
    public ITransactionExecutor getNewReadOnlyTransactionalExecutor() {
        return new NewReadOnlyTransactionalExecutorImpl();
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.CURRENT)
    public ITransactionExecutor getCurrentTransactionalExecutor() {
        return new CurrentTransactionalExecutorImpl();
    }

    @Bean
    @Qualifier(CommonQualifierConstant.TransactionExecutor.CURRENT_READONLY)
    public ITransactionExecutor getCurrentReadOnlyTransactionalExecutor() {
        return new CurrentReadOnlyTransactionalExecutorImpl();
    }

    @Bean
    public IPagingService<MendelUser> getUserPagingService(IUserRepository userRepository, IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper) {
        return new PagingServiceImpl(entityDtoMapper, userRepository);
    }

    @Bean
    public IPagingService<MendelProject> getProjectPagingService(IProjectRepository projectRepository, IEntityDtoMapper<ProjectEntity, MendelProject> entityDtoMapper) {
        return new PagingServiceImpl(entityDtoMapper, projectRepository);
    }
}
