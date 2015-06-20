package com.graduation.dao;

import com.graduation.model.Group;

public interface IGroupDao extends IBaseDao<Group> {
    public Group getGroupById(Integer id);
}
