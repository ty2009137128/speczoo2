package com.graduation.service.impl;

import com.graduation.common.Pager;
import com.graduation.dao.IOmlementDao;
import com.graduation.model.OmlElement;
import com.graduation.service.IOmlElementService;

import java.util.List;
import java.util.Map;

public class OmlElementService implements IOmlElementService {

    private IOmlementDao omlElementDao;

    @Override
    public void add(OmlElement oml) {
        this.omlElementDao.insert(oml);

    }

    @Override
    public void delete(Integer id) {
        this.omlElementDao.deleteById(id);

    }

    @Override
    public void update(OmlElement oml) {
        this.omlElementDao.update(oml);

    }

    @Override
    public List<OmlElement> list() {
        return this.omlElementDao.list();
    }

    @Override
    public List<OmlElement> list(Map<String, Object> parameters) {
        return this.omlElementDao.list(parameters);
    }

    public void setOmlElementDao(IOmlementDao omlElementDao) {
        this.omlElementDao = omlElementDao;
    }

    @Override
    public Pager<OmlElement> find() {
        return this.omlElementDao.find();
    }

    @Override
    public Pager<OmlElement> find(Map<String, Object> map) {
        return this.omlElementDao.find(map);
    }


}
