package com.graduation.service.impl;

import com.graduation.common.Pager;
import com.graduation.dao.ISqlQueryDao;
import com.graduation.service.ISqlQueryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoquan on 2014/5/17.
 */
public class SqlQueryService implements ISqlQueryService {

    private ISqlQueryDao sqlQueryDao;
    @Override
    public Pager<Map<String, Object>> executeSql(String sql,List<String> error) throws SQLException {
        Pager<Map<String,Object>> pager = null;
        try {
            pager = this.sqlQueryDao.executeSql(sql);
        } catch (SQLException e) {
            error.add("SQLException,Please check your sql ......");
            
            throw e;
        }


        return pager;
    }



    public void setSqlQueryDao(ISqlQueryDao sqlQueryDao) {
        this.sqlQueryDao = sqlQueryDao;
    }



	
	@Override
	public List<Map<String, Object>> listTableData(String tableName) {
		// TODO Auto-generated method stub
		return this.sqlQueryDao.listTableData(tableName);
	}
}
