package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 国家实体类
 */
@Data
@TableName("countries")
public class Country {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String code;
    
    private String name;
    
    private String flagEmoji;
    
    private String flagImage;
}