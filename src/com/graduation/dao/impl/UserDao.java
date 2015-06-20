package com.graduation.dao.impl;

import com.graduation.common.Pager;
import com.graduation.dao.IUserDao;
import com.graduation.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao extends BaseDao<User> implements IUserDao {

    @Override
    public User login(String username, String password) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", password);
        return this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".selectOneUser", parameters);
    }

    @Override
    public Pager<User> findPager() {
        Pager<User> pager = new Pager<User>();
        //Map<String,Object> paramters = new HashMap<String,Object>();
        ///parameters.put(.....);
        this.initMyPager(pager, null);
        return pager;
    }

    @Override
    public int deleteUserRoleByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSessionTemplate().delete(this.getClz().getName() + ".deleteUserRoleByUserId", params);
    }

    @Override
    public int deleteUserGroupByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSessionTemplate().delete(this.getClz().getName() + ".deleteUserGroupByUserId", params);
    }

    @Override
    public User getUserById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.selectOne(this.getClz().getName() + ".getUserById", params);
    }

    @Override
    public int initPassword(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getSqlSessionTemplate().update(this.getClz().getName() + ".initPassword", params);
    }

    @Override
    public User getUserByUserName(String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        List<User> users = this.getSqlSessionTemplate().selectList(this.getClz().getName() + ".getUserByUserName", params);
        return (users != null && users.size() > 0) ? users.get(0) : null;
    }

    @Override
    public int createUser(User user, List<Integer> roleIds, List<Integer> groupIds) {
        // Operate in a transaction.
        // The design of Connection belongs to Singleton pattern.
        Connection connection = this.getSqlSessionTemplate().getConnection();

        try {
            connection.setAutoCommit(false);
            this.insert(user);

            int userId = user.getId();
            if (roleIds != null) {
                for (int i = 0; i < roleIds.size(); i++) {
                    this.createUserRole(userId, roleIds.get(i));
                }
            }

            if (groupIds != null) {
                for (int i = 0; i < groupIds.size(); i++) {
                    this.createUserGroup(userId, groupIds.get(i));
                }
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.out.println("[Warning] Rollback failed.");
                }
            }
        }

        return user.getId();
    }

    @Override
    public int createUserRole(int userId, int roleId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        return this.getSqlSessionTemplate().insert(this.getClz().getName() + ".createUserRole", params);
    }

    @Override
    public int createUserGroup(int userId, int groupId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("groupId", groupId);
        return this.getSqlSessionTemplate().insert(this.getClz().getName() + ".createUserGroup", params);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSessionTemplate().selectList(this.getClz().getName() + ".getRoleIdsByUserId", params);
    }

    @Override
    public List<Integer> getGroupIdsByUserId(int userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return this.getSqlSessionTemplate().selectList(this.getClz().getName() + ".getGroupIdsByUserId", params);
    }

    @Override
    public int updateUserInfo(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", user.getId());
        params.put("username", user.getUsername());
        params.put("nickname", user.getNickname());
        params.put("phone", user.getPhone());
        params.put("email", user.getEmail());
        params.put("company", user.getCompany());
        return this.getSqlSessionTemplate().update(this.getClz().getName() + ".updateUserInfo", params);
    }

    @Override
    public int updatePasswordById(int id, String newPsw) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("password", newPsw);
        return this.getSqlSessionTemplate().update(this.getClz().getName() + ".updatePasswordById", params);
    }

    @Override
    public int updateUserAuthorityById(int userId, String authority) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", userId);
        params.put("authority", authority);
        return this.getSqlSessionTemplate().update(this.getClz().getName() + ".updateUserAuthorityById", params);
    }
}
