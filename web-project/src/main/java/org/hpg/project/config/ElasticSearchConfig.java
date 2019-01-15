/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.config;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.context.WebApplicationContext;

/**
 * Elatic search config
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("datasources_elasticsearch.properties")
})
@EnableElasticsearchRepositories(basePackages = "org.hpg.project.dao.repository.es")
public class ElasticSearchConfig {
    // Search template ?

    @Autowired
    private Environment environment;

    /**
     * Search operations instance (used for actions like createIndex,
     * putMapping)
     *
     * @return
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ElasticsearchOperations getElasticsearchOperations() {
        return new ElasticsearchTemplate(getElasticSearchClient());
    }

    // Client config ? (should be in the same file with search template ?)
    /**
     *
     * @return
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public Client getElasticSearchClient() {
        // TODO Implement
        return null;
//        RestClient lowLevelRestClient = RestClient
//                .builder(new HttpHost(
//                        environment.getProperty("elasticsearch.server", "localhost"),
//                        environment.getProperty("elasticsearch.port", Integer.class, 9200),
//                        environment.getProperty("elasticsearch.protocol", "http")))
//                .build();
//
//        return new RestHighLevelClient(lowLevelRestClient);
    }
}
