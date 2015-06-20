package com.graduation.service;

import com.graduation.common.Pager;
import com.graduation.model.OmlElement;

import java.util.List;
import java.util.Map;

public interface IOmlElementService {

    public void add(OmlElement oml);

    public void delete(Integer id);

    public void update(OmlElement oml);

    public List<OmlElement> list();

    public List<OmlElement> list(Map<String, Object> parameters);

    public Pager<OmlElement> find();

    public Pager<OmlElement> find(Map<String, Object> parameters);

}
