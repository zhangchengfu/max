package com.laozhang.max_shiro.entity;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private Integer enable;

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
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
}