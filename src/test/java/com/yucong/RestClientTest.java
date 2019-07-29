package com.yucong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableMap;
import com.yucong.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSerachApp.class)
public class RestClientTest {

    @Autowired
    private RestClient restClient;

    @Test
    public void get() throws Exception {
        Request request = new Request("POST", "/user/doc/_search");

        ImmutableMap<String, Object> of = 
            ImmutableMap.of("query",
                ImmutableMap.of("term",
                        ImmutableMap.of("age", 58)));

        System.out.println(JSON.toJSONString(of, SerializerFeature.PrettyFormat));

        HttpEntity entity = new NStringEntity(JSON.toJSONString(of), ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);

        String string = EntityUtils.toString(response.getEntity());

        JSONObject object = JSONObject.parseObject(string);
        System.out.println(JSON.toJSONString(object.getJSONObject("hits"), SerializerFeature.PrettyFormat));

    }

    @Test
    public void put() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<User> list = new ArrayList<>();
        list.add(new User(1l, "赵敏", "女", 25, "排球，足球，游泳", "江苏省", "淮安市", sdf.parse("2017-01-01"), null));
        list.add(new User(2l, "钱钟书", "男", 58, "足球，书法", "江苏省", "淮安市", sdf.parse("2018-02-31"), null));
        list.add(new User(3l, "孙满堂", "男", 78, "跑步，篮球，羽毛球", "江苏省", "南京市", sdf.parse("2019-01-01"), null));
        list.add(new User(4l, "李白", "男", 34, "书法，篮球", "江苏省", "南京市", sdf.parse("2019-01-01"), null));
        list.add(new User(5l, "周武王", "男", 58, "跑步", "山东省", "济南市", sdf.parse("2019-02-01"), null));
        list.add(new User(6l, "吴文周", "男", 50, "排球，游泳，足球", "山东省", "济南市", sdf.parse("2019-02-01"), null));
        list.add(new User(7l, "郑爽", "女", 28, "乒乓球，高尔夫，台球", "山东省", "青岛市", sdf.parse("2019-11-21"), null));
        list.add(new User(8l, "王老汉", "男", 28, "台球，足球", "山东省", "青岛市", sdf.parse("2019-11-25"), null));

        for (User user : list) {
            String jsonStr = JSONObject.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss");
            HttpEntity entity = new NStringEntity(jsonStr, ContentType.APPLICATION_JSON);
            Request request = new Request("PUT", "/user/doc/" + user.getId());
            request.setEntity(entity);
            restClient.performRequest(request);
        }
    }
    
    @Test
    public void refresh() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User(1l, "赵敏", "女", 25, "排球，足球，游泳", "江苏省", "淮安市", sdf.parse("2017-01-01"), null);
        
        String jsonStr = JSONObject.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss");
        HttpEntity entity = new NStringEntity(jsonStr, ContentType.APPLICATION_JSON);
        Request request = new Request("PUT", "/user/doc/" + user.getId());
        request.setEntity(entity);
        restClient.performRequest(request);
        
    }
    
    @Test
    public void getbyId(long id) throws Exception {
        Request request = new Request("POST", "/user/doc/_search");

        ImmutableMap<String, Object> of = 
            ImmutableMap.of("query",
                ImmutableMap.of("term",
                        ImmutableMap.of("id", id)));
        HttpEntity entity = new NStringEntity(JSON.toJSONString(of), ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);

        String string = EntityUtils.toString(response.getEntity());

        JSONObject object = JSONObject.parseObject(string);
        System.out.println(JSON.toJSONString(object.getJSONObject("hits"), SerializerFeature.PrettyFormat));
        
    }

    
    
//    PUT user
//    {
//      "mappings" : {
//          "doc" : {
//            "properties" : {
//              "age" : {
//                "type" : "integer"
//              },
//              "birth" : {
//                "type" : "date",
//                "format": "yyyy-MM-dd HH:mm:ss"
//              },
//              "city" : {
//                "type" : "keyword"
//              },
//              "hobby" : {
//                "type" : "text",
//                "analyzer" : "ik_max_word"
//              },
//              "id" : {
//                "type" : "long"
//              },
//              "intro" : {
//                "type" : "text",
//                "analyzer" : "ik_max_word"
//              },
//              "name" : {
//                "type" : "keyword"
//              },
//              "province" : {
//                "type" : "text",
//                "analyzer": "ik_max_word",
//                "search_analyzer": "ik_max_word",
//                "fielddata" : true,
//                "fields": {"raw": {"type": "keyword"}}
//              },
//              "sex" : {
//                "type" : "keyword"
//              }
//            }
//          }
//        }
//    }

}
