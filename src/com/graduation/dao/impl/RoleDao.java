package com.graduation.dao.impl;

import com.graduation.dao.IRoleDao;
import com.graduation.model.Role;

import java.util.HashMap;
import java.util.Map;

public class RoleDao extends BaseDao<Role> implements IRoleDao {
    @Override
    public Role getRoleById(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".getRoleById", params);
    }
}
