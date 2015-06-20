package com.graduation.service;

import com.graduation.common.Pager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoquan on 2014/5/17.
 */
public interface ISqlQueryService {
    public Pager<Map<String,Object>> executeSql(String sql,List<String> error) throws SQLException;

	public List<Map<String, Object>> listTableData(String tableName);
}
