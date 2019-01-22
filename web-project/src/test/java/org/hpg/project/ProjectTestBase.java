/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Base test class for web-project module
 *
 * @author wws2003
 */
@ContextConfiguration(classes = ProjectTestBase.class)
@ComponentScan(basePackages = "org.hpg")
@PropertySources({
    @PropertySource("classpath:datasources_elasticsearch_fortest.properties")
    ,
    @PropertySource("classpath:datasources_elasticsearch.properties")
})
@WebAppConfiguration(value = "classpath:")
public class ProjectTestBase {

    /**
     * Constructor for debug
     */
    public ProjectTestBase() {
        System.out.println("Try to debug ProjectTestBase for properties classes");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
