package com.laozhang.max_shiro.service.impl;

import com.laozhang.max_shiro.dao.UserRoleMapper;
import com.laozhang.max_shiro.entity.UserRole;
import com.laozhang.max_shiro.entity.UserRoleCriteria;
import com.laozhang.max_shiro.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addUserRole(UserRole userRole) {
        userRoleMapper.insert(userRole);
    }

    @Override
    public int save(UserRole entity) {
        return userRoleMapper.insert(entity);
    }

    @Override
    public int delete(Object id) {
        return 0;
    }

    @Override
    public int update(UserRole entity) {
        return userRoleMapper.updateByExample(entity, new UserRoleCriteria());
    }

    @Override
    public UserRole selectById(Object id) {
        return null;
    }
}
