package com.laozhang.max_shiro.vo;

import java.util.List;

public class UserVO {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Integer enable;

    private List<RoleVO> roleVOS;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public List<RoleVO> getRoleVOS() {
        return roleVOS;
    }

    public void setRoleVOS(List<RoleVO> roleVOS) {
        this.roleVOS = roleVOS;
    }
}
