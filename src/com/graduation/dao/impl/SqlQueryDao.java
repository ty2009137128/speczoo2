package com.graduation.dao.impl;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.dao.ISqlQueryDao;
import com.graduation.util.ResultSetHandleUtil;
import com.graduation.util.TableHandleUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoquan on 2014/5/17.
 */
public class SqlQueryDao extends BaseDao implements ISqlQueryDao {


    public Pager<Map<String, Object>> executeSql(String sql) throws SQLException {
        Pager<Map<String,Object>> pager = new Pager<Map<String, Object>>();
        //查询总记录数
        long total = 0;

        Connection con = this.getSqlSessionTemplate().getConnection();
        Statement statement = con.createStatement();
        //判断是否是修改语句
        ResultSet rs = null;
        boolean isSqlUpdate = TableHandleUtil.isSqlUpdate(sql);
        System.out.println("SqlQueryDaosql=="+sql);
        if (isSqlUpdate){
            //如果要求支持sql的更新，取消下面注释
           // statement.executeUpdate(sql);
        	
        	System.out.println("isSqlUpdate");
        }else {
        	System.out.println("到这里了么");
            String countSql = TableHandleUtil.getSqlQueryCount(sql);
            System.out.println("到这里了么-------"+countSql);
            ResultSet rsCount = statement.executeQuery(countSql);
            
            total = ResultSetHandleUtil.getCountByResultSet(rsCount);
            System.out.println("total"+total);
            statement.clearBatch();
            rs = statement.executeQuery(sql);
            System.out.println("rs"+rs);
        }
        List<Map<String, Object>> lmps = null;/*new ArrayList<Map<String,Object>>()*/
        if(rs != null){
            lmps = ResultSetHandleUtil.ResultSetHandle(rs);
        }

        pager.setDatas(lmps);
      //  pager.setOffset(0);
        pager.setTotal(total);
        return pager;

    }

	@Override
	public List<Map<String, Object>> listTableData(String tableName) {
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableName", tableName);
        Map<String, Object> resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".findTabelData", parameters, "RECORD_ID");

        List<Map<String, Object>> ls = ResultSetHandleUtil.map2List(resultMap);
        return ls;
	}
    
    

}
