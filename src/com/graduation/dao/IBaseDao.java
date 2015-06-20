package com.graduation.dao;

import com.graduation.common.Pager;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
    /**
     * 插入对象
     *
     * @param t
     */
    public int insert(T t);

    /**
     * 删除
     *
     * @param t
     */
    public int delete(T t);

    public int deleteById(Integer id);

    /**
     * 更新对象
     *
     * @param t
     */
    public int update(T t);

    /**
     * @return
     */
    public List<T> list();

    public List<T> list(Map<String, Object> parameters);

    public Pager<T> find();

    public Pager<T> find(Map<String, Object> parameters);

    public T selectOne(String statement);

    public T selectOne(String statement, Object parameters);

    public Map selectMap(String statement, Object parameters, String keymap);

	List<Map<String, Object>> listTableData(String tableName);


}
