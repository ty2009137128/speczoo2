package com.graduation.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.dao.IBaseDao;


/**
 * BaseDao封装不是很好，执行哪个statement不应该在这里写死，应该在UserDao层写
 *
 * @param <T>
 * @author xiaoquan
 */
public class BaseDao<T> implements IBaseDao<T> {

    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<?> clz;

    public Class<?> getClz() {
        if (clz == null) {
            //获取泛型的Class对象
            clz = ((Class<?>)
                    (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }

    @Override
    public int insert(T t) {
        return this.sqlSessionTemplate.insert(this.getClz().getName() + ".insert", t);
    }

    @Override
    public int delete(T t) {
        return this.sqlSessionTemplate.delete(this.getClz().getName() + ".delete", t);
    }

    @Override
    public int deleteById(Integer id) {
        return this.sqlSessionTemplate.delete(this.getClz().getName() + ".deleteById", id);
    }

    @Override
    public int update(T t) {
        return this.sqlSessionTemplate.update(this.getClz().getName() + ".update", t);
    }

    @Override
    public List<T> list() {
        return list(null);
    }

    @Override
    public List<T> list(Map<String, Object> parameters) {
        List<T> ts = this.sqlSessionTemplate.selectList(this.getClz().getName() + ".selectList", parameters);
        return ts;
    }


    @Override
    public Pager<T> find() {
        //return find(new HashMap<String,Object>());
        return find(null);
    }


    @Override
    public Pager<T> find(Map<String, Object> parameters) {

        Pager<T> pager = new Pager<T>();
        this.initPager(pager, parameters);//初始化分页信息

        return pager;
    }

    @Override
    public T selectOne(String statement) {
        return this.selectOne(statement, null);
    }

    @Override
    public T selectOne(String statement, Object parameters) {
        return this.sqlSessionTemplate.selectOne(statement, parameters);
    }

    @Override
    public Map selectMap(String statement, Object parameters, String keymap) {
        return this.sqlSessionTemplate.selectMap(statement, parameters, keymap);
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private void initPager(Pager<T> pager, Map<String, Object> parameters) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        pager.setSize(pageSize);
        pager.setOffset(pageOffset);
        List<T> datas = null;
        long total = 0L;
        if (parameters.get("or_and").equals("0"))
        {
            datas = this.sqlSessionTemplate.selectList(this.getClz().getName() + ".selectListOr", parameters, new RowBounds(pageOffset, pageSize));
            total = (Long)this.sqlSessionTemplate.selectOne(this.getClz().getName() + ".countOr", parameters);
        }
        else
        {
            datas = this.sqlSessionTemplate.selectList(this.getClz().getName() + ".selectList", parameters, new RowBounds(pageOffset, pageSize));
            total = (Long)this.sqlSessionTemplate.selectOne(this.getClz().getName() + ".count", parameters);
        }
        pager.setTotal(total);
        pager.setDatas(datas);
    }

    //分页用此函数
    public void initMyPager(Pager<T> pager, Map<String, Object> parameters) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        pager.setSize(pageSize);
        pager.setOffset(pageOffset);
        List<T> datas = null;
        datas = this.sqlSessionTemplate.selectList(this.getClz().getName() + ".selectList", parameters, new RowBounds(pageOffset, pageSize));
        long total = (Long)this.sqlSessionTemplate.selectOne(this.getClz().getName() + ".count", parameters);
        pager.setTotal(total);
        pager.setDatas(datas);
    }

	public List<Map<String, Object>> listTableData(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
