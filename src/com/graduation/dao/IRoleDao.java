package com.graduation.dao;

import com.graduation.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
    public Role getRoleById(Integer id);
}
