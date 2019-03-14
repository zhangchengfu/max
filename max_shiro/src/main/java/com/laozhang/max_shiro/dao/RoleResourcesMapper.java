package com.laozhang.max_shiro.dao;

import com.laozhang.max_shiro.entity.RoleResourcesCriteria;
import com.laozhang.max_shiro.entity.RoleResourcesKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleResourcesMapper {
    long countByExample(RoleResourcesCriteria example);

    int deleteByExample(RoleResourcesCriteria example);

    int deleteByPrimaryKey(RoleResourcesKey key);

    int insert(RoleResourcesKey record);

    int insertSelective(RoleResourcesKey record);

    List<RoleResourcesKey> selectByExample(RoleResourcesCriteria example);

    int updateByExampleSelective(@Param("record") RoleResourcesKey record, @Param("example") RoleResourcesCriteria example);

    int updateByExample(@Param("record") RoleResourcesKey record, @Param("example") RoleResourcesCriteria example);
}