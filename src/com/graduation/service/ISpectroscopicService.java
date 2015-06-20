package com.graduation.service;

import com.graduation.common.Pager;
import com.graduation.model.SpectroscopicElement;

import java.util.List;
import java.util.Map;

public interface ISpectroscopicService {

    public Pager<SpectroscopicElement> find();

    public Pager<SpectroscopicElement> find(Map<String, Object> parameters);

    public List<Map<String, Object>> executeSql(String sql, List<String> error);
    public Pager<Map<String,Object>> findTableData(Map<String,Object> parameters);
}
