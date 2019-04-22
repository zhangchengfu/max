package com.laozhang.max_mybatis_mapper.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
