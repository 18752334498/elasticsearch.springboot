package com.yucong.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yucong.entity.Item;

public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

}
