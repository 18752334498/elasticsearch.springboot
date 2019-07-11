package com.yucong.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ESConfig {

    private String host = "127.0.0.1";
    private int port = 9200;
    private Integer connectNum = 10;
    private Integer connectPerRoute = 50;

    @Bean
    public HttpHost httpHost() {
        return new HttpHost(host, port, "http");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ESClientSpringFactory getFactory() {
        return ESClientSpringFactory.build(httpHost(), connectNum, connectPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient() {
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient() {
        return getFactory().getRhlClient();
    }

}
