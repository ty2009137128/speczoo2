package com.graduation.model.dto;

import com.graduation.common.Pager;

import java.util.List;
import java.util.Set;

/**
 * Created by xiaoquan on 2014/5/23.
 */
public class Datadto {
    private Pager pager;
    private String tableName;
    private Object message;

    private Set<String> names;
    //0为错误,1为正确
    private int result = 1;
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Set<String> getNames() {
        return names;
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
