package com.graduation.dao.impl;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.dao.ISpectrumDao;
import com.graduation.model.SpectrumElement;
import com.graduation.util.ResultSetHandleUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

public class SpectrumDao extends BaseDao<SpectrumElement>
  implements ISpectrumDao
{
  public void createTable(String createTableSql, String tableName)
  {
    HashMap parameters = new HashMap();
    parameters.put("createTableSql", createTableSql);
    parameters.put("tableName", tableName);
    getSqlSessionTemplate().update(getClz().getName() + ".dropTable", parameters);
    getSqlSessionTemplate().update(getClz().getName() + ".createTable", parameters);
  }

  public int insertBachByCollection(String tableName, ArrayList<String> sqls)
  {
    Map parameters = new HashMap();
    parameters.put("tableName", tableName);
    parameters.put("sqls", sqls);
    return getSqlSessionTemplate().insert(getClz().getName() + ".insertBach", parameters);
  }

  public int insertSpectrumElement(SpectrumElement spectrumElement)
  {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String date=df.format(new Date());
    spectrumElement.setUpdateTime(date);
    spectrumElement.setInsertTime(date);
    return getSqlSessionTemplate().insert(getClz().getName() + ".insertSpectrum", spectrumElement);
  }

  public List<String> getTableNames(Integer uid, Integer tid)
  {
    Map parameters = new HashMap();
    parameters.put("uid", uid);
    parameters.put("tid", tid);
    return getSqlSessionTemplate().selectList(getClz().getName() + ".getTableNames", parameters);
  }

  public int deleteTableByNames(List<String> tableNames)
  {
    Map parameters = new HashMap();
    parameters.put("tableNames", tableNames);
    return getSqlSessionTemplate().update(getClz().getName() + ".deleteTableByNames", parameters);
  }

  public SpectrumElement getByNames(String tableName)
  {
    Map parameters = new HashMap();
    parameters.put("tableName", tableName);
    return ((SpectrumElement)getSqlSessionTemplate().selectOne(getClz().getName() + ".getSpectrumElement", parameters));
  }

  public int deleteSpectrumElementByTableName(List<String> tableNames)
  {
    Map parameters = new HashMap();
    parameters.put("tableNames", tableNames);
    return getSqlSessionTemplate().delete(getClz().getName() + ".deleteSpectrumElementByTableName", parameters);
  }

  public int deleteSpectrumElementByTableName(String tableName)
  {
    List tableNames = new ArrayList();
    tableNames.add(tableName);
    return deleteSpectrumElementByTableName(tableNames);
  }

  public Pager<Map<String, Object>> findTabelData(String tableName)
  {
    Map parameters = new HashMap();
    parameters.put("tableName", tableName);
    Integer pageSize = SystemContext.getPageSize();
    Integer offSet = SystemContext.getPageOffset();
    Pager page = new Pager();
    page.setOffset(offSet.intValue());
    page.setSize(pageSize.intValue());
    Map resultMap = getSqlSessionTemplate().selectMap(getClz().getName() + ".findTabelData", parameters, "RECORD_ID", new RowBounds(offSet.intValue(), pageSize.intValue()));

    List ls = ResultSetHandleUtil.map2List(resultMap);
    long total = ((Long)getSqlSessionTemplate().selectOne(getClz().getName() + ".findTabelDataCount", parameters)).longValue();
    page.setDatas(ls);
    page.setTotal(total);
    return page;
  }

  public List<Map<String, Object>> listTableData(String tableName)
  {
    Map parameters = new HashMap();
    parameters.put("tableName", tableName);
    Map resultMap = getSqlSessionTemplate().selectMap(getClz().getName() + ".findTabelData", parameters, "RECORD_ID");

    List ls = ResultSetHandleUtil.map2List(resultMap);
    return ls;
  }

  public List<SpectrumElement> getSpectrumElement(Integer uid)
  {
    Map parameters = new HashMap();
    parameters.put("uid", uid);
    parameters.put("isPublic", Integer.valueOf(1));
    return getSqlSessionTemplate().selectList(getClz().getName() + ".getSpectrumElementByUId", parameters);
  }

  public List<String> getTableNames(Integer uid, String keyword)
  {
    Map parameters = new HashMap();
    parameters.put("uid", uid);
    parameters.put("keyword", keyword);
    parameters.put("isPublic", Integer.valueOf(1));
    return getSqlSessionTemplate().selectList(getClz().getName() + ".getTableNameKeword", parameters);
  }

  public List<String> getFieldNamesOfTable(String tableName) throws SQLException
  {
    Connection con = getSqlSessionTemplate().getConnection();

    Statement state = con.createStatement();
    ResultSet rs = state.executeQuery("select * from " + tableName + " where  1=2");
    return ResultSetHandleUtil.getFieldNamesByResultSet(rs);
  }

  public SpectrumElement getSpectrumElementByUidAndTableName(Integer uid, String tableName)
  {
    Map parameters = new HashMap();
    parameters.put("uid", uid);
    parameters.put("tableName", tableName);

    return ((SpectrumElement)getSqlSessionTemplate().selectOne(getClz().getName() + ".getSpectrumElementByUidAndTableName", parameters));
  }
}