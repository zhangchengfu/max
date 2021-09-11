package com.laozhang.shardingsphere.repository;

import com.laozhang.shardingsphere.entity.HealthTask;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HealthTaskRepository extends BaseRepository<HealthTask, Long> {

}
