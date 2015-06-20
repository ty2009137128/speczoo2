package com.graduation.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.graduation.common.Pager;
import com.graduation.model.MydbElement;

public interface IMydbDao extends IBaseDao<MydbElement> {

    public void createTable(String createTableSql, String tableName);

    /**
     * 删除名字在 tablenames中的表格
     *
     * @param tableNames
     * @return
     */
    public int deleteTableByNames(List<String> tableNames);

    /**
     * 通过表格名获取mydbelement对象
     *
     * @param tableName
     * @return
     */
    public MydbElement getByNames(String tableName);

    /**
     * 删除mydb_user中名字在tableName集合中的对象
     *
     * @param tableName
     * @return
     */
    public int deleteMydbElementByTableName(List<String> tableName);

    public int deleteMydbElementByTableName(String tableName);

    /**
     * 根据用户id 或者 表格 id 获取获取该用户创建的所有表格名 mapper中采用 <when><chose>
     * uid ==null and tid != null 查出 table 的id为tid的表信息
     * uid ！= null 表示查出该用户创建的所有表信息
     *
     * @param uid
     * @return
     */
    public List<String> getTableNames(Integer uid, Integer tid);

    /**
     * 模糊搜索改用户创建的表名
     * select * from mydb_user where u_id = #{uid} and tableName like '%keyword%'
     * @param uid
     * @param keyword
     * @return
     */
    public List<String> getTableNames(Integer uid,String keyword);

    public int insertBachByCollection(String tableName, ArrayList<String> sqls);

    public int insertMydbElement(MydbElement mydb);

    /**
     * 获取分页查找的数据
     *
     * @param tableName
     * @return
     */
    public Pager<Map<String, Object>> findTabelData(String tableName);

    /*
     * 获取非分页的数据，即所有表格数据
     */
    public List<Map<String, Object>> listTableData(String tableName);


    public List<MydbElement> getMydbElement(Integer uid);

    /**
     * 获取表字段的名字
     * @param tableName
     * @return
     */
    public List<String> getFieldNamesOfTable(String tableName) throws SQLException;
    
    public MydbElement getMydbElementByUidAndTableName(Integer uid,String tableName);
    
    public void updateTableStatus(MydbElement mydbElement);
}
