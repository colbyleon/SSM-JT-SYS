package com.jt.sys.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jt.common.util.DateJsonSerializer;
import com.jt.common.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {

    private static final long serialVersionUID = 7948348514951831421L;
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Integer valid = 1;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    // 自己转换时间的规则
    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getModifiedTime() {
        return modifiedTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", vaild='" + valid + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", createdUser='" + createdUser + '\'' +
                ", modifiedUser='" + modifiedUser + '\'' +
                '}';
    }
}
