package com.yucong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yucong.entity.User;
import com.yucong.service.UserService;

@RestController
@RequestMapping("test")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("add")
	public void add() {
		String aa = "{\"id\":1,\"name\":\"张三\",\"age\":20,\"description\":\"张三是个Java开发工程师\",\"createtm\":\"2018-4-25 11:07:42\"}";
		String bb = "{\"id\":2,\"name\":\"李四\",\"age\":24,\"description\":\"李四是个测试工程师\",\"createtm\":\"1980-2-15 19:01:32\"}";
		String cc = "{\"id\":3,\"name\":\"王五\",\"age\":25,\"description\":\"王五是个运维工程师\",\"createtm\":\"2016-8-21 06:11:32\"}";

		User user1 = JSON.parseObject(aa, User.class);
		User user2 = JSON.parseObject(bb, User.class);
		User user3 = JSON.parseObject(cc, User.class);

		boolean b = userService.insert(user1);
		boolean c = userService.insert(user2);
		boolean d = userService.insert(user3);

		System.out.println(b + ":" + c + ":" + d);
	}

}
