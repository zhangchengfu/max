package com.laozhang.maxmongo.dao.impl;

import com.laozhang.maxmongo.dao.UserDao;
import com.laozhang.maxmongo.entity.UserEntity;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(UserEntity user) {
        mongoTemplate.save(user);
    }

    @Override
    public UserEntity findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserEntity user = mongoTemplate.findOne(query, UserEntity.class);
        return user;
    }

    @Override
    public int updateUser(UserEntity user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        WriteResult result = mongoTemplate.updateFirst(query, update, UserEntity.class);
        if (result != null) {
            return result.getN();
        } else {
            return 0;
        }
    }

    @Override
    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserEntity.class);
    }
}
