package com.graduation.model;


import java.util.Date;
import java.util.List;

public class User {
    private int id;
    /**
     * 用户登录名称
     */
    private String username;
    /**
     * 用户登录密码
     */
    private String password;
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

    private List<Integer> roleIds;

    private List<Integer> groupIds;

    /**
     * 创建时间
     */
    private Date createDate;

    private String company;

    private String authority;

    public User(int id, String username, String password, String nickname,
                String email, String phone, int status) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public User() {
    }

    public User(String username, String password, String nickname,
                String email, String phone, int status) {
        super();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public static String checkUserName(String username) {
        String errorMsg = null;

        if (username == null) {
            errorMsg = "User name is empty.";
        } else if (username.length() < 1) {
            errorMsg = "The length of user name should not less than 1";
        }

        return errorMsg;
    }

    public static String checkPassword(String password) {
        String errorMsg = null;

        if (password == null) {
            errorMsg = "Password is empty";
        } else if (password.length() < 3) {
            errorMsg = "The length of password should not be less than 3";
        }

        return errorMsg;
    }

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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
