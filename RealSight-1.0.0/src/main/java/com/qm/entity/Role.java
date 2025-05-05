package com.qm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色实体类
 */
@Data
@TableName("roles")
public class Role {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String name;
}