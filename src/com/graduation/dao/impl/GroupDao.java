package com.graduation.dao.impl;

import com.graduation.dao.IGroupDao;
import com.graduation.model.Group;

import java.util.HashMap;
import java.util.Map;

public class GroupDao extends BaseDao<Group> implements IGroupDao {

    @Override
    public Group getGroupById(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".getGroupById", params);
    }
}
