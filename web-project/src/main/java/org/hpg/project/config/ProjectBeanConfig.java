/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.config;

import org.hpg.common.biz.service.abstr.IDocumentService;
import org.hpg.project.biz.service.abstr.IDocumentSearchService;
import org.hpg.project.biz.service.impl.DocumentSearchServiceElasticSearchImpl;
import org.hpg.project.dao.repository.es.IEsDocumentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * Bean config for project module
 *
 * @author trungpt
 */
@Configuration
public class ProjectBeanConfig {

//    @Bean(destroyMethod = "shutdown")
//    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
//    public ExecutorService getExecutorService() {
//        return Executors.newWorkStealingPool();
//    }
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IDocumentSearchService getDocumentSearchService(IEsDocumentRepository documentRepository, IDocumentService documentService) {
        return new DocumentSearchServiceElasticSearchImpl(documentRepository, documentService);
    }
}
