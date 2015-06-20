package com.graduation.service;

import com.graduation.common.Pager;
import com.graduation.model.User;

import java.util.List;

public interface IUserService {
    public User login(String username, String password);

    public Pager<User> findPager();

    public int deleteUserById(int id);

    public User getUserById(int id);

    public int update(User user);

    public int createUser(User user);

    public int initPassword(int id);

    public User getUserByUserName(String username);

    public int createUser(User user, List<Integer> roleIds, List<Integer> groupIds);

    public int deleteUserRoleByUserId(Integer id);

    public int deleteUserGroupByUserId(Integer id);

    public int updateUserInfo(User user);

    public int updatePasswordById(int id, String newPsw);

    public int updateUserAuthorityById(int userId, String authority);
}
