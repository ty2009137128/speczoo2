package com.graduation.service.impl;

import com.graduation.dao.IRoleDao;
import com.graduation.model.Role;
import com.graduation.service.IRoleService;

import java.util.List;
import java.util.Map;

public class RoleService implements IRoleService {

    private IRoleDao roleDao;

    @Override
    public int add(Role role) {
        return this.roleDao.insert(role);
    }

    @Override
    public int delete(Role role) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Role> list(Map<String, Object> parameters) {
        return this.roleDao.list(parameters);
    }

    @Override
    public List<Role> list() {
        return this.list(null);
    }

    @Override
    public int update(Role role) {
        return this.roleDao.update(role);
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleById(Integer id) {
        return this.roleDao.getRoleById(id);
    }

    @Override
    public int deleteRoleById(Integer id) {
        return this.roleDao.deleteById(id);
    }
}
