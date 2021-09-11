package com.laozhang.shardingsphere.repository;

import com.laozhang.shardingsphere.entity.EncryptUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EncryptUserRepository extends BaseRepository<EncryptUser, Long> {

}
