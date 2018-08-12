/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for Spring Data JPA
 *
 * @author wws2003
 */
@Configuration
@EnableJpaRepositories(
        // TODO Add packages from other modules apart from web-common
        basePackages = {"org.hpg.common.dao.repository"},
        entityManagerFactoryRef = "jtaEntityManagerFactory",
        transactionManagerRef = "jtaPlatformTransactionManager"
)
public class RepositoryConfig {

}
