/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.config;

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
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
@EnableTransactionManagement
public class JTAPersistenceConfig {

    @Autowired
    private Environment environment;

    // Entity manager factory
    @Bean(name = "jtaEntityManagerFactory")
    public AbstractEntityManagerFactoryBean getEntityManagerFactoryBean() throws Exception {

        return EntityManagerFactoryBuilder.instanceForDataSource(getDataSource())
                .packagesToScan(Arrays.asList("org.hpg.common.model.entity"))
                .persistenceUnitName("pu1")
                .jpaVendorAdapter(new HibernateJpaVendorAdapter())
                .propertiesMap(getPropertiesMap(Arrays.asList(
                        "hibernate.transaction.jta.platform",
                        "hibernate.dialect",
                        "hibernate.show_sql",
                        "hibernate.current_session_context_class",
                        "hibernate.format_sql"
                )))
                .build();
    }

    // Transaction manager
    @Bean(name = "jtaPlatformTransactionManager")
    public PlatformTransactionManager getJtaTransactionManager() throws Exception {
        // Default constructor, i.e. delegate to the server the job of managing transaction ?
        return new JtaTransactionManager();
    }

    /**
     * TODO Create XA-data source name
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

    private static class EntityManagerFactoryBuilder {

        /**
         * Data source
         */
        private DataSource mDataSource = null;

        /**
         * Persistence unit name
         */
        private String mPersistenceUnitName = null;

        /**
         * Packages to scan for entities
         */
        private List<String> mPackagesToScan = null;

        /**
         * Vendor adapter, such as Hibernate
         */
        private JpaVendorAdapter mJpaVendorAdapter = null;

        /**
         * Properties map
         */
        private Map<String, Object> mPropertiesMap = null;

        private EntityManagerFactoryBuilder(DataSource dataSource) {
            mDataSource = dataSource;
        }

        public static EntityManagerFactoryBuilder instanceForDataSource(DataSource dataSource) {
            return new EntityManagerFactoryBuilder(dataSource);
        }

        public EntityManagerFactoryBuilder persistenceUnitName(String persistenceUnitName) {
            mPersistenceUnitName = persistenceUnitName;
            return this;
        }

        public EntityManagerFactoryBuilder packagesToScan(List<String> packagesToScan) {
            mPackagesToScan = packagesToScan;
            return this;
        }

        public EntityManagerFactoryBuilder jpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter) {
            mJpaVendorAdapter = jpaVendorAdapter;
            return this;
        }

        public EntityManagerFactoryBuilder propertiesMap(Map<String, Object> propertiesMap) {
            mPropertiesMap = propertiesMap;
            return this;
        }

        public AbstractEntityManagerFactoryBean build() {
            // TODO Implement
            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            // emf.setPersistenceProviderClass(org.hibernate.ejb.HibernatePersistence.class);
            // Data source
            emf.setJtaDataSource(mDataSource);
            // Persistence unit
            emf.setPackagesToScan(mPackagesToScan.toArray(new String[mPackagesToScan.size()]));
            emf.setPersistenceUnitName(mPersistenceUnitName);
            // Vendor adapter
            emf.setJpaVendorAdapter(mJpaVendorAdapter);
            // Properties
            emf.setJpaPropertyMap(mPropertiesMap);
            // Finallize
            emf.afterPropertiesSet();
            return emf;
        }
    }
}
