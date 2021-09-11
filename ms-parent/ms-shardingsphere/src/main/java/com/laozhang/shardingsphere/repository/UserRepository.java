package com.laozhang.shardingsphere.repository;

import com.laozhang.shardingsphere.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRepository extends BaseRepository<User, Long> {

}
