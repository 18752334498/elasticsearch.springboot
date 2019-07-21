package com.yucong;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.yucong.dao.ItemRepository;
import com.yucong.entity.Item;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSerachApp.class)
public class ElasticSerachTest {

    @Autowired
    private RestClient restClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ElasticsearchTemplate esTemplete;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createIndex() {
        boolean createIndex = esTemplete.createIndex(Item.class);
        System.out.println(createIndex);
    }

    @Test
    public void deleteIndex() {
        boolean deleteIndex = esTemplete.deleteIndex(Item.class);
        System.out.println(deleteIndex);
    }

    @Test
    public void save() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        itemRepository.saveAll(list);
    }

    @Test
    public void testMatchQuery() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米"));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("total = " + total);
        for (Item item : items) {
            System.out.println(item);
        }
    }

}
