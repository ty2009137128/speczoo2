package com.graduation.service;

import com.graduation.common.Pager;
import com.graduation.model.MydbElement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IMydbService {


    /**
     * 从文件流中读取数据，完成表的创建
     *
     * @param fileIn
     * @param tableName 要创建的表名
     * @param error     文件流中数据的错误信息
     * @return
     * @throws IOException
     */
    public long insertBach(InputStream fileIn, String tableName,
                           List<String> error) throws IOException;


    /**
     * 通过创建表格的sql语句，和表名创建表
     *
     * @param createTableSql
     * @param tableName
     */
    public void createTable(String createTableSql, String tableName);


    /**
     * 添加mydb信息到 mydb_user中
     *
     * @param mydb
     */
    public void insertMydb(MydbElement mydb);

    /**
     * 获取tableName对应的MydbElement对象
     *
     * @param tableName
     * @return
     */
    public MydbElement getMydbElementByTableName(String tableName);

    public int deleteTable(Integer uid, Integer tid);

    public List<MydbElement> list();

    public List<MydbElement> list(Integer uid);

    /**
     * 通过用户id，搜索表名含有keyword的表
     * select tableName from mydb_user where u_id = #{uid} and tableName like '%keyword%'
     * @param uid
     * @param keyword
     * @return
     */
    public List<String> listTableNames(Integer uid,String keyword);

    public Pager<Map<String, Object>> findTableData(String tableName);

    public List<Map<String, Object>> listTableData(String tableName);

    /**
     * 获取指定表的字段名字（表元数据）
     * @param tabelName
     * @return
     */
    public List<String> getFieldNamesOfTable(String tabelName) throws SQLException;
    
    public MydbElement getMydbElementByUidAndTableName(Integer uid,String tableName);
    
    /**
     * 修改表是私有和共有的状态
     * @param mydbElement
     */
    public void updateTableStatus(MydbElement mydbElement);


	public int deleteSelectedTable(int id, String[] tableName);

}
