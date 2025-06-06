package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}