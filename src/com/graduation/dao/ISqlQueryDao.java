package com.graduation.dao;

import com.graduation.common.Pager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoquan on 2014/5/17.
 * Changed by tyang  on 2014-8-19
 */
public interface ISqlQueryDao extends IBaseDao {

    public Pager<Map<String, Object>> executeSql(String sql) throws SQLException ;

	public List<Map<String, Object>> listTableData(String tableName);
}
