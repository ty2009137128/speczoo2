package com.graduation.dao.impl;

import com.graduation.common.Pager;
import com.graduation.common.SystemContext;
import com.graduation.dao.ISpectroscopicDao;
import com.graduation.model.SpectroscopicElement;
import com.graduation.util.ResultSetHandleUtil;
import com.graduation.util.TableHandleUtil;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class SpectroscopicDao extends BaseDao<SpectroscopicElement> implements ISpectroscopicDao {


    public List<Map<String, Object>> executeSql(String sql) throws SQLException {

        //对Sql完成分页的包装
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        sql=sql+" "+"limit "+" "+pageOffset+", "+pageSize;
        Connection con = this.getSqlSessionTemplate().getConnection();
        Statement statement = con.createStatement();
        //判断是否是修改语句
        ResultSet rs = null;
        boolean isSqlUpdate = TableHandleUtil.isSqlUpdate(sql);
        if (isSqlUpdate){
              statement.executeUpdate(sql);
        }else {
             rs = statement.executeQuery(sql);
        }
        List<Map<String, Object>> lmps = null;/*new ArrayList<Map<String,Object>>()*/
        if(rs != null){
             lmps = ResultSetHandleUtil.ResultSetHandle(rs);
        }
        return lmps;

    }

    @Override
    public Pager<Map<String, Object>> findTableDate(Map<String, Object> parameters) {

        Pager<Map<String,Object>> pager = new Pager<Map<String, Object>>();
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        pager.setSize(pageSize);
        pager.setOffset(pageOffset);
        long total = 0L;
        Map<String, Object> resultMap = null;
        if (parameters.get("or_and").equals("0"))
        {
            if("fithas".equals(parameters.get("tableName"))){
                resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".selectListOr", parameters,"recordId" ,new RowBounds(pageOffset, pageSize));
                total = (Long)this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".countOr", parameters);
            }else{
                resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".findTabelDataOr", parameters,"RECORD_ID" ,new RowBounds(pageOffset, pageSize));
                total = (Long)this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".findTableDataCountOr", parameters);
            }
        }
        else {
            if("fithas".equals(parameters.get("tableName"))){
                resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".selectList", parameters, "recordId",new RowBounds(pageOffset, pageSize));
                total = (Long) this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".count", parameters);
            }else{
                resultMap = this.getSqlSessionTemplate().selectMap(this.getClz().getName() + ".findTabelData", parameters,"RECORD_ID" ,new RowBounds(pageOffset, pageSize));
                total = (Long)this.getSqlSessionTemplate().selectOne(this.getClz().getName() + ".findTableDataCount", parameters);
            }
        }
        List<Map<String, Object>> ls = ResultSetHandleUtil.map2List(resultMap);
        pager.setTotal(total);
        pager.setDatas(ls);
        return pager;
    }
}
