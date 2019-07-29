package com.yucong.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String name;

    private String sex;

    private Integer age;

    private String hobby;

    private String province;

    private String city;

    private Date birth;

    private String intro;

}
