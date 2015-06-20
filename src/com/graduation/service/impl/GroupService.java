package com.graduation.service.impl;

import com.graduation.dao.IGroupDao;
import com.graduation.model.Group;
import com.graduation.service.IGroupService;

import java.util.List;
import java.util.Map;

public class GroupService implements IGroupService {
    private IGroupDao groupDao;

    @Override
    public int add(Group group) {
        return groupDao.insert(group);
    }

    @Override
    public int delete(Group group) {
        return 0;
    }

    @Override
    public List<Group> list(Map<String, Object> parameters) {
        return this.groupDao.list(parameters);
    }

    @Override
    public List<Group> list() {
        return this.list(null);
    }

    @Override
    public int update(Group group) {
        return this.groupDao.update(group);
    }

    public void setGroupDao(IGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public int deleteGroupById(Integer id) {
        return this.groupDao.deleteById(id);
    }

    @Override
    public Group getGroupById(Integer id) {
        return this.groupDao.getGroupById(id);
    }
}
