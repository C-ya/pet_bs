package com.smilcool.server.core.pojo.po;

import lombok.Data;

import java.util.Date;

@Data
public class LostFound {
    private Integer id;

    private Integer userId;

    private Integer resourceId;

    private String lostFoundCategory;

    private String title;

    private String description;

    private String itemName;

    private Date time;

    private String address;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

    private String images;
    
    private String startdate;
    private String enddate;
}