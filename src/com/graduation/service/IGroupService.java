package com.graduation.service;

import com.graduation.model.Group;

import java.util.List;
import java.util.Map;

public interface IGroupService {

    /**
     * 获取所有角色
     *
     * @param parameters
     * @return
     */
    public List<Group> list(Map<String, Object> parameters);

    public List<Group> list();

    public int add(Group group);

    public int update(Group group);

    public int delete(Group group);

    public int deleteGroupById(Integer id);

    public Group getGroupById(Integer id);
}
