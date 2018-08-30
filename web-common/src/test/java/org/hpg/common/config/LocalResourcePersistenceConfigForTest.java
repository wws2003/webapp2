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
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JTA config for test
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:datasource_jpa_test.properties")
})
@EnableJpaRepositories(
        // TODO Add packages from other modules apart from web-common
        basePackages = {"org.hpg.common.dao.repository"},
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaPlatformTransactionManager"
)
@EnableTransactionManagement
public class LocalResourcePersistenceConfigForTest {

    @Autowired
    private Environment environment;

    // Entity manager factory
    @Bean(name = "jpaEntityManagerFactory")
    public AbstractEntityManagerFactoryBean getEntityManagerFactoryBean() throws Exception {

        return EntityManagerFactoryBuilder.instanceForDataSource(getDataSource())
                .packagesToScan(Arrays.asList("org.hpg.common.model.entity"))
                .persistenceUnitName("pu1-fortest")
                .jpaVendorAdapter(new HibernateJpaVendorAdapter())
                .propertiesMap(getPropertiesMap(Arrays.asList(
                        "hibernate.transaction.jta.platform",
                        "hibernate.dialect",
                        "hibernate.show_sql",
                        "hibernate.format_sql"
                )))
                .build();
    }

    // Transaction manager
    @Bean(name = "jpaPlatformTransactionManager")
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * Create XA-data source name
     *
     * @return
     */
    @Bean
    public DataSource getDataSource() {
        // For test, do not use JNDI (since no container required), but direct datasource bound to DBMS
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        return dataSource;
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
            // Data source
            emf.setDataSource(mDataSource);
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
