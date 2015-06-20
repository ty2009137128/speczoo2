package com.graduation.dao;

import com.graduation.common.Pager;
import com.graduation.model.User;

import java.util.List;

public interface IUserDao extends IBaseDao<User> {
    /**
     * 根据用户名密码获取登录用户
     *
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password);

    public Pager<User> findPager();

    public int deleteUserRoleByUserId(int userId);

    public int deleteUserGroupByUserId(int userId);

    public User getUserById(int id);

    public int initPassword(int id);

    public User getUserByUserName(String username);

    public int createUser(User user, List<Integer> roleIds, List<Integer> groupIds);

    public List<Integer> getRoleIdsByUserId(int userId);

    public List<Integer> getGroupIdsByUserId(int userId);

    public int createUserRole(int userId, int roleId);

    public int createUserGroup(int userId, int groupId);

    public int updateUserInfo(User user);

    public int updatePasswordById(int id, String newPsw);

    public int updateUserAuthorityById(int userId, String authority);
}
