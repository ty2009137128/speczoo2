package com.graduation.dao;

import com.graduation.common.Pager;
import com.graduation.model.SpectrumElement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract interface ISpectrumDao extends IBaseDao<SpectrumElement>
{
  public abstract void createTable(String paramString1, String paramString2);

  public abstract int deleteTableByNames(List<String> paramList);

  public abstract SpectrumElement getByNames(String paramString);

  public abstract int deleteSpectrumElementByTableName(List<String> paramList);

  public abstract int deleteSpectrumElementByTableName(String paramString);

  public abstract List<String> getTableNames(Integer paramInteger1, Integer paramInteger2);

  public abstract List<String> getTableNames(Integer paramInteger, String paramString);

  public abstract int insertBachByCollection(String paramString, ArrayList<String> paramArrayList);

  public abstract int insertSpectrumElement(SpectrumElement paramSpectrumElement);

  public abstract Pager<Map<String, Object>> findTabelData(String paramString);

  public abstract List<Map<String, Object>> listTableData(String paramString);

  public abstract List<SpectrumElement> getSpectrumElement(Integer paramInteger);

  public abstract List<String> getFieldNamesOfTable(String paramString)
    throws SQLException;

  public abstract SpectrumElement getSpectrumElementByUidAndTableName(Integer paramInteger, String paramString);
}