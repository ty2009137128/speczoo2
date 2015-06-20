package com.graduation.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.dao.IMydbDao;
import com.graduation.model.MydbElement;
import com.graduation.util.ResultSetHandleUtil;


public class MydbDao extends BaseDao<MydbElement> implements IMydbDao {

    @Override
    public void createTable(String createTableSql, String tableName) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("createTableSql", createTableSql);
        parameters.put("tableName", tableName);
        this.getSqlSessionTemplate().update(this.getClz().getName() + ".dropTable", parameters);
        this.getSqlSessionTemplate().update(this.getClz().getName() + ".createTable", parameters);

    }

    @Override
    public int insertBachByCollection(String tableName, ArrayList<String> sqls) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableName", tableName);
        parameters.put("sqls", sqls);
        return this.getSqlSessionTemplate().insert(this.getClz().getName() + ".insertBach", parameters);
    }

    @Override
    public int insertMydbElement(MydbElement mydb) {
    	mydb.setUpdateDate(new java.sql.Date(new java.util.Date().getTime()));
        return this.getSqlSessionTemplate().insert(this.getClz().getName() + ".insertMydb", mydb);
    }

    @Override
    public List<String> getTableNames(Integer uid, Integer tid) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("uid", uid);
        parameters.put("tid", tid);
        return this.getSqlSessionTemplate().selectList(this.getClz().getName() + ".getTableNames", parameters);
    }

    @Override
    public int deleteTableByNames(List<String> tableNames) {
        //可以不用map的，用map便于扩展参数
        //若是直接用List<String> tableNames作为参数，mybatis会为其生成一个map ，key为 list，
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableNames", tableNames);
        return this.getSqlSessionTemplate().update(this.getClz().getName() + ".deleteTableByNames", parameters);
    }

    @Override
    public MydbElement getByNames(String tableName) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableName", tableName);
        return this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".getMydbElement", parameters);
    }

    @Override
    public int deleteMydbElementByTableName(List<String> tableNames) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableNames", tableNames);
        return this.getSqlSessionTemplate().delete(this.getClz().getName() + ".deleteMydbElementByTableName", parameters);
    }

    @Override
    public int deleteMydbElementByTableName(String tableName) {
        List<String> tableNames = new ArrayList<String>();
        tableNames.add(tableName);
        return deleteMydbElementByTableName(tableNames);
    }

    @Override
    public Pager<Map<String, Object>> findTabelData(String tableName) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableName", tableName);
        Integer pageSize = SystemContext.getPageSize();
        Integer offSet = SystemContext.getPageOffset();
        Pager<Map<String, Object>> page = new Pager<Map<String, Object>>();
        page.setOffset(offSet);
        page.setSize(pageSize);
        Map<String, Object> resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".findTabelData", parameters, "RECORD_ID", new RowBounds(offSet, pageSize));

        List<Map<String, Object>> ls = ResultSetHandleUtil.map2List(resultMap);
        long total = (Long)this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".findTabelDataCount", parameters);
        page.setDatas(ls);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<Map<String, Object>> listTableData(String tableName) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tableName", tableName);
        Map<String, Object> resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".findTabelData", parameters, "RECORD_ID");

        List<Map<String, Object>> ls = ResultSetHandleUtil.map2List(resultMap);
        return ls;
    }

    @Override
    public List<MydbElement> getMydbElement(Integer uid) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("uid", uid);
        parameters.put("isPublic",1);
        return this.getSqlSessionTemplate().selectList(this.getClz().getName() + ".getMydbElementByUId", parameters);
    }

    @Override
    public List<String> getTableNames(Integer uid, String keyword) {
       Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("uid",uid);
        parameters.put("keyword",keyword);
        parameters.put("isPublic",1);
        return this.getSqlSessionTemplate().selectList(this.getClz().getName()+".getTableNameKeword",parameters);
    }

    @Override
    public List<String> getFieldNamesOfTable(String tableName) throws SQLException {
        Connection con = this.getSqlSessionTemplate().getConnection();
       // DatabaseMetaData databaseMetaData = con.getMetaData();
       // ResultSet rs = databaseMetaData.getTables(null,null,tableName,new String[]{"TABLE"});
        Statement state  = con.createStatement();
        ResultSet rs = state.executeQuery("select * from "+tableName+" where  1=2");
        return ResultSetHandleUtil.getFieldNamesByResultSet(rs);
    }

	@Override
	public MydbElement getMydbElementByUidAndTableName(Integer uid,
			String tableName) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("uid",uid);
		parameters.put("tableName",tableName);
		
		return this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".getMydbElementByUidAndTableName", parameters);
		

	}

	@Override
	public void updateTableStatus(MydbElement mydbElement) {
		if(mydbElement.getIsPublic() == 1) {
			mydbElement.setIsPublic(0);
		}else{
			mydbElement.setIsPublic(1);
		}
		
		this.getSqlSessionTemplate().update(this.getClz().getName() + ".updateTableStatus", mydbElement);
	}
	
	
    
}
