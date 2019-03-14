package com.laozhang.max_shiro.service;

import com.laozhang.max_shiro.entity.RoleResourcesKey;

public interface RoleResourcesService extends IService<RoleResourcesKey> {
    void addRoleResources(RoleResourcesKey roleResources);
}
