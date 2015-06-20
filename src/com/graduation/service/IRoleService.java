package com.graduation.service;

import com.graduation.model.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    /**
     * 获取所有角色
     *
     * @param parameters
     * @return
     */
    public List<Role> list(Map<String, Object> parameters);

    public List<Role> list();

    public int add(Role role);

    public int update(Role role);

    public int delete(Role role);

    public Role getRoleById(Integer id);

    public int deleteRoleById(Integer id);
}
