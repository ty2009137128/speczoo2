package com.graduation.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.graduation.common.Pager;
import com.graduation.model.SpectroscopicElement;

public interface ISpectroscopicDao extends IBaseDao<SpectroscopicElement> {

    public List<Map<String, Object>> executeSql(String sql) throws SQLException;

   public Pager<Map<String, Object>> findTableDate(Map<String, Object> parameters);

}
