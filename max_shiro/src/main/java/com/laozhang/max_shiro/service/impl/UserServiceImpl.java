package com.laozhang.max_shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.laozhang.max_shiro.dao.UserMapper;
import com.laozhang.max_shiro.dao.UserRoleMapper;
import com.laozhang.max_shiro.entity.User;
import com.laozhang.max_shiro.entity.UserCriteria;
import com.laozhang.max_shiro.entity.UserRoleCriteria;
import com.laozhang.max_shiro.service.UserService;
import com.laozhang.max_shiro.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public PageInfo<User> selectByPage(User user, int start, int length) {
        int page = start/length+1;
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();
        if (!StringUtils.isEmpty(user.getUsername())) {
            criteria.andUsernameLike("%" + user.getUsername() + "%");
        }
        PageHelper.startPage(page, length);
        List<User> users = userMapper.selectByExample(userCriteria);
        return new PageInfo<User>(users);
    }

    @Override
    public User selectByUsername(String username) {
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();
        criteria.andUsernameEqualTo(username);
        User user = null;
        List<User> users = userMapper.selectByExample(userCriteria);
        if (null != users && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }

    @Override
    public void delUser(Integer userid) {
        userMapper.deleteByPrimaryKey(userid);
        UserRoleCriteria userRoleCriteria = new UserRoleCriteria();
        UserRoleCriteria.Criteria criteria = userRoleCriteria.createCriteria();
        criteria.andUseridEqualTo(userid);
        // 删除用户角色
        userRoleMapper.deleteByExample(userRoleCriteria);
    }

    @Override
    public UserVO getUserVO(User user) {
        return userMapper.getUserVO(user);
    }

    @Override
    public int save(User entity) {
        return userMapper.insert(entity);
    }

    @Override
    public int delete(Object id) {
        return userMapper.deleteByPrimaryKey((Integer) id);
    }

    @Override
    public int update(User entity) {
        return userMapper.updateByPrimaryKey(entity);
    }

    @Override
    public User selectById(Object id) {
        return userMapper.selectByPrimaryKey((Integer) id);
    }
}
