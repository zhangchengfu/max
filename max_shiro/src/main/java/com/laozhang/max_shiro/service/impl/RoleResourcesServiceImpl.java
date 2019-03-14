package com.laozhang.max_shiro.service.impl;

import com.laozhang.max_shiro.dao.RoleResourcesMapper;
import com.laozhang.max_shiro.entity.RoleResourcesCriteria;
import com.laozhang.max_shiro.entity.RoleResourcesKey;
import com.laozhang.max_shiro.service.RoleResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleResourcesServiceImpl implements RoleResourcesService {

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    public void addRoleResources(RoleResourcesKey roleResources) {

    }

    @Override
    public int save(RoleResourcesKey entity) {
        return roleResourcesMapper.insert(entity);
    }

    @Override
    public int delete(Object id) {
        RoleResourcesKey roleResourcesKey = (RoleResourcesKey) id;
        return roleResourcesMapper.deleteByPrimaryKey(roleResourcesKey);
    }

    @Override
    public int update(RoleResourcesKey entity) {
        return roleResourcesMapper.updateByExample(entity,new RoleResourcesCriteria());
    }

    @Override
    public RoleResourcesKey selectById(Object id) {
        return null;
    }
}
