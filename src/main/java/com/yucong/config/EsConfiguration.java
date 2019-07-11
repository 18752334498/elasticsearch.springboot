package com.yucong.config;

import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

// @Configuration
public class EsConfiguration {
    
    private Logger logger=LoggerFactory.getLogger(EsConfiguration.class);
    
    // @Resource
    // private ZkClient zkClient;

    private static String hosts = "192.168.85.101"; // 集群地址，多个用,隔开
    private static int port = 9200; // 使用的端口号
    private static String schema = "http"; // 使用的协议
    private static ArrayList<HttpHost> hostList = null;

    private static int connectTimeOut = 1000; // 连接超时时间
    private static int socketTimeOut = 30000; // 连接超时时间
    private static int connectionRequestTimeOut = 500; // 获取连接的超时时间

    private static int maxConnectNum = 5; // 最大连接数
    private static int maxConnectPerRoute = 5; // 最大路由连接数

    private RestClientBuilder builder;

    @Bean(name="esRestClient")
    public RestClient restClient() {
        hostList = new ArrayList<>();
        // hosts=zkClient.findData("/royasoft/core/es/default/address");
        logger.info("连接es，zk地址:/royasoft/core/es/default/address，值:{}",hosts);
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            hostList.add(new HttpHost(host, port, schema));
        }
        
        builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        // RestHighLevelClient client = new RestHighLevelClient(builder);
        return builder.build();
    }

    // 异步httpclient的连接延时配置
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(new RequestConfigCallback() {

            @Override
            public Builder customizeRequestConfig(Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
    }

    // 异步httpclient的连接数配置
    public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {

            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
    }

}
