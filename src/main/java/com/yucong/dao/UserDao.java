package com.yucong.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yucong.entity.User;

public interface UserDao extends ElasticsearchRepository<User, Long> {

}
