package com.laozhang.shardingsphere.repository;

import com.laozhang.shardingsphere.entity.HealthLevel;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HealthLevelRepository extends BaseRepository<HealthLevel, Long> {

}
