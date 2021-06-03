package com.smilcool.server.core.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SecondHandVO {
    private Integer id;

    private Integer userId;

    private Integer resourceId;

    private String secondHandCategory;

    private String title;

    private String name;

    private Integer price;

    private String address;

    private String description;

    private String images;
    
    private Integer state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 发布用户信息
     */
    private UserVO user;

    /**
     * 关联资源信息
     */
    private ResourceVO resource;
    private String startdate;
    private String enddate;
    
}