package com.yucong.entity;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "userindex", type = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 7481481492891891599L;
	/** 编号 */
	private Long id;

	/** 姓名 */
	private String name;

	/** 年龄 */
	private Integer age;

	/** 描述 */
	private String description;

	/** 创建时间 */
	private String createtm;

}
