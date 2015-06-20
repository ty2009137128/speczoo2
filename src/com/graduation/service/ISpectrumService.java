package com.graduation.service;

import com.graduation.common.Pager;
import com.graduation.model.SpectrumElement;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract interface ISpectrumService
{
  public abstract long insertBach(InputStream paramInputStream, String paramString, List<String> paramList)
    throws IOException;

  public abstract void createTable(String paramString1, String paramString2);

  public abstract void insertSpectrum(SpectrumElement paramSpectrumElement);

  public abstract SpectrumElement getSpectrumElementByTableName(String paramString);

  public abstract int deleteTable(Integer paramInteger1, Integer paramInteger2);

  public abstract List<SpectrumElement> list();

  public abstract List<SpectrumElement> list(Integer paramInteger);

  public abstract List<String> listTableNames(Integer paramInteger, String paramString);

  public abstract Pager<Map<String, Object>> findTableData(String paramString);

  public abstract List<Map<String, Object>> listTableData(String paramString);

  public abstract List<String> getFieldNamesOfTable(String paramString)
    throws SQLException;

  public abstract SpectrumElement getSpectrumElementByUidAndTableName(Integer paramInteger, String paramString);

  public abstract int deleteSelectedTable(int paramInt, String[] paramArrayOfString);
}