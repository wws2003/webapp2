/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for repositories in module web-common
 *
 * @author trungpt
 */
@Configuration
@EnableJpaRepositories("org.hpg.common.dao.repository")
public class CommonRepositoryConfig {
    // TODO Add methods for entityManagerFactory
}
