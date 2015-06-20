package com.graduation.model.dto;

import java.util.Date;
import java.util.List;

public class UserDto {
    private int id;
    /**
     * 用户登录名称
     */
    private String username;
    /**
     * 用户登录密码
     */
    private String password;

    private String confirmPwd;
    /**
     * 用户的中文名称
     */
    private String nickname;
    /**
     * 用户的邮件
     */
    private String email;
    /**
     * 用户的联系电话
     */
    private String phone;
    /**
     * 用户的状态：0表示停用，1表示启用
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createDate;

    private String company;

    private List<Integer> roleIds;

    private List<Integer> groupIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }
}
