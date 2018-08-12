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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Configuration class for repositories in module web-common (currently aimed to
 * use for JTA datasource)
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:datasource_jta.properties")
})
public class CommonJTAPersistenceConfig {

    @Autowired
    private Environment environment;

    // Entity manager factory
    @Bean(name = CommonQualifierConstant.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() throws Exception {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        // TODO Implement properly

        // Datasource
        emf.setJtaDataSource(getDataSource());

        // What about packages in other modules ?
        emf.setPackagesToScan("org.hpg.common.model.entity");
        emf.setPersistenceUnitName("pu-common");

        // Vendor adapter
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // JPA and Hibernate properties
        List<String> jpaPropertyNames = Arrays.asList(
                "hibernate.transaction.jta.platform",
                "hibernate.hbm2ddl.auto",
                "hibernate.dialect",
                "hibernate.show_sql",
                "hibernate.format_sql"
        );
        Map<String, Object> jpaPropertiesMap = getPropertiesMap(jpaPropertyNames);

        emf.setJpaPropertyMap(jpaPropertiesMap);

        // Finallize
        emf.afterPropertiesSet();

        return emf;
    }

    // Transaction manager
    @Bean(name = CommonQualifierConstant.TRANSACTION_MANAGER)
    public PlatformTransactionManager getJtaTransactionManager() throws Exception {
        // Default constructor, i.e. delegate to the server the job of managing transaction ?
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        // Finallize
        jtaTransactionManager.afterPropertiesSet();
        return jtaTransactionManager;
    }

    /**
     * Create XA-data source name
     *
     * @return
     */
    @Bean
    public DataSource getDataSource() {
        return createJndiDataSource(environment.getProperty("spring.datasource.datasource-name"));
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

    /**
     * Create data source by JNDI name
     *
     * @param jndiName
     * @return
     */
    private DataSource createJndiDataSource(String jndiName) {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource(jndiName);
    }
}
