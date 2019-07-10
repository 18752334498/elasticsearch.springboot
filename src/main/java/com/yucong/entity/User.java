package com.yucong.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private Date creatDate;

}
