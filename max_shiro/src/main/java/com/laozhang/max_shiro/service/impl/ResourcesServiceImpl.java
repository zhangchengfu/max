package com.laozhang.max_shiro.service.impl;

import com.github.pagehelper.PageInfo;
import com.laozhang.max_shiro.dao.ResourcesMapper;
import com.laozhang.max_shiro.entity.Resources;
import com.laozhang.max_shiro.service.ResourcesService;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public PageInfo<Resources> selectByPage(Resources resources, int start, int length) {

        return null;
    }

    @Override
    public List<Resources> queryAll() {
        return null;
    }

    @Override
    public List<Resources> loadUserResources(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<Resources> queryResourcesListWithSelected(Integer rid) {
        return null;
    }

    @Override
    public int save(Resources entity) {
        return resourcesMapper.insert(entity);
    }

    @Override
    public int delete(Object id) {
        return resourcesMapper.deleteByPrimaryKey((Integer) id);
    }

    @Override
    public int update(Resources entity) {
        return resourcesMapper.updateByPrimaryKey(entity);
    }

    @Override
    public Resources selectById(Object id) {
        return resourcesMapper.selectByPrimaryKey((Integer)id);
    }
}
