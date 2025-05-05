package com.qm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.entity.Country;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国家Mapper接口
 */
@Mapper
public interface CountryMapper extends BaseMapper<Country> {
}