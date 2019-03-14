package com.laozhang.max_shiro.vo;

import java.util.List;

public class RoleVO {
    private Integer id;

    private String roledesc;

    private List<ResourcesVO> resourcesVOS;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    public List<ResourcesVO> getResourcesVOS() {
        return resourcesVOS;
    }

    public void setResourcesVOS(List<ResourcesVO> resourcesVOS) {
        this.resourcesVOS = resourcesVOS;
    }
}
