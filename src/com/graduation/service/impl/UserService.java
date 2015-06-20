package com.graduation.service.impl;

import com.graduation.common.Pager;
import com.graduation.dao.IUserDao;
import com.graduation.model.User;
import com.graduation.service.IUserService;

import java.util.List;

public class UserService implements IUserService {

    private IUserDao userDao;

    @Override
    public User login(String username, String password) {
        return this.userDao.login(username, password);
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Pager<User> findPager() {
        return this.userDao.findPager();
    }

    @Override
    public int deleteUserById(int id) {
        this.userDao.deleteUserRoleByUserId(id);
        this.userDao.deleteUserGroupByUserId(id);
        return this.userDao.deleteById(id);
    }

    @Override
    public int update(User user) {
        int userId = user.getId();

        //Delete the original role and group info.
        int cntUserRole = this.deleteUserRoleByUserId(userId);
        int cntUserGroup = this.deleteUserGroupByUserId(userId);

        //Insert the new role and group info.
        if (user.getRoleIds() != null) {
            for (int i = 0; i < user.getRoleIds().size(); i++) {
                this.userDao.createUserRole(userId, user.getRoleIds().get(i));
            }
        }

        if (user.getGroupIds() != null) {
            for (int i = 0; i < user.getGroupIds().size(); i++) {
                this.userDao.createUserGroup(userId, user.getGroupIds().get(i));
            }
        }

        return this.userDao.update(user);
    }

    @Override
    public User getUserById(int id) {
        User user = this.userDao.getUserById(id);
        List<Integer> roleIds = this.userDao.getRoleIdsByUserId(id);
        List<Integer> groupIds = this.userDao.getGroupIdsByUserId(id);
        user.setRoleIds(roleIds);
        user.setGroupIds(groupIds);
        return user;
    }

    @Override
    public int createUser(User user) {
        return this.userDao.insert(user);
    }

    @Override
    public int initPassword(int id) {
        return userDao.initPassword(id);
    }

    @Override
    public User getUserByUserName(String username) {
        return this.userDao.getUserByUserName(username);
    }

    @Override
    public int createUser(User user, List<Integer> roleIds, List<Integer> groupIds) {
        User exist = getUserByUserName(user.getUsername());

        int userId = 0;
        if (exist == null) {
            userId = this.userDao.createUser(user, roleIds, groupIds);
        }

        return userId;
    }

    @Override
    public int deleteUserRoleByUserId(Integer id) {
        return this.userDao.deleteUserRoleByUserId(id);
    }

    @Override
    public int deleteUserGroupByUserId(Integer id) {
        return this.userDao.deleteUserGroupByUserId(id);
    }

    @Override
    public int updateUserInfo(User user) {
        return this.userDao.updateUserInfo(user);
    }

    @Override
    public int updatePasswordById(int id, String newPsw) {
        return this.userDao.updatePasswordById(id, newPsw);
    }

    @Override
    public int updateUserAuthorityById(int userId, String authority) {
        return this.userDao.updateUserAuthorityById(userId, authority);
    }
}

