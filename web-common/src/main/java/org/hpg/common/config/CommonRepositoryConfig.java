/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for repositories in module web-common
 *
 * @author trungpt
 */
@Configuration
@EnableJpaRepositories(basePackages = {"org.hpg.common.dao.repository"})
public class CommonRepositoryConfig {
    // TODO Add methods for entityManagerFactory

    // DataSource bean
    @Bean
    public DataSource getDataSourceBean() {
        // TODO Implement properly
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setDatabaseName("");
        ds.setApplicationName("");
        return ds;
    }

    // Entity manager factory
    public EntityManagerFactory getEntityManagerFactory() {
        // TODO Implement
        return null;
    }

    // Transaction manager
}
