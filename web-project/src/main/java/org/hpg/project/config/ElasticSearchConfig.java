/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
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
    @PropertySource("classpath:datasources_elasticsearch.properties")
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
     * @throws java.net.UnknownHostException
     */
    @Bean(name = {"elasticsearchTemplate"})
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ElasticsearchOperations getElasticsearchOperations() throws UnknownHostException {
        return new ElasticsearchTemplate(getElasticSearchClient());
    }

    // Client config ? (should be in the same file with search template ?)
    /**
     *
     * @return @throws java.net.UnknownHostException
     */
    @Bean(destroyMethod = "close")
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public TransportClient getElasticSearchClient() throws UnknownHostException {
        // Try to use RestHightLevel client. But it is not an implementation of Client class ?

        // But it looks RestHighLevelClient is not quite compatible with Spring Data ?
//        return new RestHighLevelClient(RestClient.builder(
//                new HttpHost(environment.getProperty("elasticsearch.server", "localhost"),
//                        environment.getProperty("elasticsearch.port", Integer.class, 9200),
//                        environment.getProperty("elasticsearch.protocol", "http")))
//        );
        // Any better with TransportClient directly
        Settings settings = Settings.builder()
                .put("cluster.name", environment.getProperty("elasticsearch.cluster", "elasticsearch-clt1"))
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(
                        InetAddress.getByName(environment.getProperty("elasticsearch.server", "localhost")),
                        environment.getProperty("elasticsearch.port", Integer.class, 9300))
                );

        return client;
    }
}
