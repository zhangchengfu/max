package com.laozhang.shardingsphere.repository;

import com.laozhang.shardingsphere.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HealthRecordRepository extends BaseRepository<HealthRecord, Long> {

}
