package com.graduation.service.impl;

import com.graduation.common.Pager;
import com.graduation.dao.ISpectroscopicDao;
import com.graduation.model.SpectroscopicElement;
import com.graduation.service.ISpectroscopicService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SpectroscopicService implements ISpectroscopicService {

    private ISpectroscopicDao spectroscopicDao;

    @Override
    public Pager<SpectroscopicElement> find() {
        return this.spectroscopicDao.find();
    }

    @Override
    public Pager<SpectroscopicElement> find(Map<String, Object> parameters) {
        return this.spectroscopicDao.find(parameters);
    }

    public void setSpectroscopicDao(ISpectroscopicDao spectroscopicDao) {
        this.spectroscopicDao = spectroscopicDao;
    }

    @Override
    public List<Map<String, Object>> executeSql(String sql, List<String> error) {
        try {
            return this.spectroscopicDao.executeSql(sql);
        } catch (SQLException e) {
            error.add("sql执行异常，请检查sql语句是否正确.....");
        }
        return null;
    }

    @Override
    public Pager<Map<String, Object>> findTableData(Map<String, Object> parameters) {
        return this.spectroscopicDao.findTableDate(parameters);
    }
}
