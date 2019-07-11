package com.yucong;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSerachApp.class)
public class ElasticSerachTest {

    @Autowired
    private RestClient restClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test1() {

    }

}
