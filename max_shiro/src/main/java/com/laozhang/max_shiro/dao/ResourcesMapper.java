package com.laozhang.max_shiro.dao;

import com.laozhang.max_shiro.entity.Resources;
import com.laozhang.max_shiro.entity.ResourcesCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourcesMapper {
    long countByExample(ResourcesCriteria example);

    int deleteByExample(ResourcesCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Resources record);

    int insertSelective(Resources record);

    List<Resources> selectByExample(ResourcesCriteria example);

    Resources selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Resources record, @Param("example") ResourcesCriteria example);

    int updateByExample(@Param("record") Resources record, @Param("example") ResourcesCriteria example);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);
}