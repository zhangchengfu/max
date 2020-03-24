package com.laozhang.max_springsecurity.service;

import com.laozhang.max_springsecurity.entity.SysRole;
import com.laozhang.max_springsecurity.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jitwxs
 * @date 2018/3/30 1:27
 */
@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    public SysRole selectByName(String name) {
        return roleMapper.selectByName(name);
    }
}
