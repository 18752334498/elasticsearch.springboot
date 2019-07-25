package com.yucong;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.get.GetResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSerachApp.class)
public class ElasticSerachTest {

    /**
     * https://www.cnblogs.com/ginb/p/8716485.html
     */
    @Autowired
    private RestHighLevelClient restHighLevelClient;// 高级客户端将在内部创建低级客户端，用来执行基于提供的构建器的请求，并管理其生命周期

    @Test
    public void test1() throws Exception {

        GetRequest getRequest = new GetRequest("bk", "doc", "1");

        // 同步执行
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        // Get Response
        // 返回的GetResponse允许检索请求的文档及其元数据和最终存储的字段。
        String index = getResponse.getIndex();
        String type = getResponse.getType();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();// 检索文档(String形式)
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();// 检索文档(Map<String,Object>形式)
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();// 检索文档（byte[]形式）

            System.out.println(JSON.toJSONString(sourceAsMap, SerializerFeature.PrettyFormat));
        } else {
        }
    }

    @Test
    public void update() throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("intro", "winter is coming tonight will be long");
        UpdateRequest request = new UpdateRequest("bk", "doc", "EQuAImwBOS0HOAwWs2wg").doc(jsonMap);

        request.fetchSource(true); // 启用_source检索，默认为禁用

        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);

        // 当通过fetchSource方法在UpdateRequest中启用源检索时，响应会包含已更新文档：
        GetResult result = updateResponse.getGetResult();// 获取已更新的文档
        if (result.isExists()) {
            String sourceAsString = result.sourceAsString();// 获取已更新的文档源（String方式）
            Map<String, Object> sourceAsMap = result.sourceAsMap();// 获取已更新的文档源（Map方式）
            byte[] sourceAsBytes = result.source();// 获取已更新的文档源（byte[]方式）
            System.out.println(JSON.toJSONString(sourceAsMap, SerializerFeature.PrettyFormat));
        } else {
            // 处理不返回文档源的场景（默认就是这种情况）
        }
    }


}
