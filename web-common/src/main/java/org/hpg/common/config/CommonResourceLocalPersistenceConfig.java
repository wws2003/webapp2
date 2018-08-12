/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration class for repositories in module web-common (used as
 * alternative for persistence.xml, but this class is not suitable for JTA
 * datasource but Resource-local). Currently unused
 *
 * @author trungpt
 */
@PropertySources({
    @PropertySource("classpath:datasource_postgres.properties")
})
public class CommonResourceLocalPersistenceConfig {
    // TODO Add methods for entityManagerFactory

    @Autowired
    private Environment environment;

    // DataSource bean
    @Bean
    public DataSource getDataSourceBean() throws Exception {
        // TODO Implement properly
        // QUESTION: Where is the name of Glassfish-set datasource ? Looks like ignored ?
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(environment.getProperty("spring.datasource.url"));//URL includes server address, port name, database name
        ds.setUser(environment.getProperty("spring.datasource.username"));
        ds.setPassword(environment.getProperty("spring.datasource.password"));
        ds.setTcpKeepAlive(true);
        return ds;
    }

    // Entity manager factory
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() throws Exception {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        // TODO Implement properly

        // Datasource
        emf.setDataSource(getDataSourceBean());

        // What about packages in other modules ?
        emf.setPackagesToScan("org.hpg.common.model.entity");
        emf.setPersistenceUnitName("pu-common");

        // Vendor adapter
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // JPA and Hibernate properties
        List<String> jpaPropertyNames = Arrays.asList(
                "spring.jpa.hibernate.ddl-auto",
                "spring.jpa.properties.hibernate.dialect",
                "spring.jpa.show-sql"
        );
        Map<String, Object> jpaPropertiesMap = getPropertiesMap(jpaPropertyNames);

        emf.setJpaPropertyMap(jpaPropertiesMap);

        // Finallize
        emf.afterPropertiesSet();

        return emf;
    }

    // Transaction manager
    @Bean
    public PlatformTransactionManager getPlatformTransactionManagerBean() throws Exception {
        // TODO Implement
        return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
    }

    /**
     * Get from property file the mapping from property name to property value
     *
     * @param propertyNames
     * @return
     */
    private Map<String, Object> getPropertiesMap(List<String> propertyNames) {
        return propertyNames.stream()
                .filter(propertyName -> environment.getProperty(propertyName) != null)
                .collect(Collectors.toMap(propertyName -> propertyName,
                        propertyName -> environment.getProperty(propertyName))
                );
    }
}
