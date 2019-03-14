package com.laozhang.max_shiro.service.impl;

import com.github.pagehelper.PageInfo;
import com.laozhang.max_shiro.dao.RoleMapper;
import com.laozhang.max_shiro.entity.Role;
import com.laozhang.max_shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryRoleListWithSelected(Integer uid) {
        return null;
    }

    @Override
    public PageInfo<Role> selectByPage(Role role, int start, int length) {
        return null;
    }

    @Override
    public void delRole(Integer roleid) {

    }

    @Override
    public int save(Role entity) {
        return roleMapper.insert(entity);
    }

    @Override
    public int delete(Object id) {
        return roleMapper.deleteByPrimaryKey((Integer) id);
    }

    @Override
    public int update(Role entity) {
        return roleMapper.updateByPrimaryKey(entity);
    }

    @Override
    public Role selectById(Object id) {
        return roleMapper.selectByPrimaryKey((Integer) id);
    }
}
