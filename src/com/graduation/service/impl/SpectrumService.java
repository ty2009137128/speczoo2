package com.graduation.service.impl;

import com.graduation.common.Pager;
import com.graduation.dao.ISpectrumDao;
import com.graduation.model.SpectrumElement;
import com.graduation.service.ISpectrumService;
import com.graduation.util.TableHandleUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpectrumService
  implements ISpectrumService
{
  private ISpectrumDao spectrumDao;

  public void createTable(String createTableSql, String tableName)
  {
    this.spectrumDao.createTable(createTableSql, tableName);
  }

  public long insertBach(InputStream fileIn, String tableName, List<String> error)
    throws IOException
  {
    int insertRowNumber = 0;
    boolean hasRecordId = false;
    try {
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileIn));
      ArrayList sqls = new ArrayList();
      int fieldColumnNum = 0;
      String line = "";
      Long row = Long.valueOf(0L);
      while ((line = fileReader.readLine()) != null) {
        row = Long.valueOf(row.longValue() + 1L);
        System.out.println(("".equals(line)) ? "空" : line);
        if (1L == row.longValue())
        {
          String temLine = line.toUpperCase();
          hasRecordId = TableHandleUtil.hasRecordId(temLine);
          if (!(hasRecordId)) line = "RECORD_ID," + line;
          String createTableSql = TableHandleUtil.getTableByString(tableName, line);
          System.out.println("生成的创建表的SQL语句：" + createTableSql);
          createTable(createTableSql, tableName);
          fieldColumnNum = TableHandleUtil.getColumnNumberByString(line);
        } else {
          if (!(hasRecordId)) line = (row.longValue() - 1L) + "," + line;

          String insertValue = TableHandleUtil.getInsertValueByString(line, fieldColumnNum, row, error);

          if ((insertValue != null) || (!("".equals(insertValue)))) {
            sqls.add(insertValue);
          }
          if (sqls.size() >= 200) {
            insertRowNumber = this.spectrumDao.insertBachByCollection(tableName, sqls);
            sqls.clear();
          }
        }
      }

      insertRowNumber += this.spectrumDao.insertBachByCollection(tableName, sqls);
    }
    catch (IOException e) {
      error.add("请检查您上传的数据格式，上传数据中不要有数据库关键字字段，例如:dec,asc等等");
      throw e;
    }

    return insertRowNumber;
  }

  public void insertSpectrum(SpectrumElement spectrum)
  {
    System.out.println("insertSpectrum这里会显示吗？");

    this.spectrumDao.insertSpectrumElement(spectrum);
  }

  public SpectrumElement getSpectrumElementByTableName(String tableName)
  {
    SpectrumElement spectrum = this.spectrumDao.getByNames(tableName);
    return spectrum;
  }

  public int deleteTable(Integer uid, Integer tid)
  {
    List tableNames = this.spectrumDao.getTableNames(uid, tid);
    if (tableNames.size() == 0) {
      return 0;
    }
    this.spectrumDao.deleteTableByNames(tableNames);
    return this.spectrumDao.deleteSpectrumElementByTableName(tableNames);
  }

  public List<SpectrumElement> list()
  {
    return list(null);
  }

  public List<SpectrumElement> list(Integer uid)
  {
    return this.spectrumDao.getSpectrumElement(uid);
  }

  public List<String> listTableNames(Integer uid, String keyword)
  {
    return this.spectrumDao.getTableNames(uid, keyword);
  }

  public Pager<Map<String, Object>> findTableData(String tableName)
  {
    return this.spectrumDao.findTabelData(tableName);
  }

  public List<Map<String, Object>> listTableData(String tableName)
  {
    return this.spectrumDao.listTableData(tableName);
  }

  public List<String> getFieldNamesOfTable(String tableName) throws SQLException
  {
    return this.spectrumDao.getFieldNamesOfTable(tableName);
  }

  public SpectrumElement getSpectrumElementByUidAndTableName(Integer uid, String tableName)
  {
    return this.spectrumDao.getSpectrumElementByUidAndTableName(uid, tableName);
  }

  public int deleteSelectedTable(int id, String[] tableName)
  {
    List tableNames = new ArrayList();
    for (String str : tableName) {
      tableNames.add(str);
    }
    this.spectrumDao.deleteTableByNames(tableNames);
    return this.spectrumDao.deleteSpectrumElementByTableName(tableNames);
  }

  public void setSpectrumDao(ISpectrumDao spectrumDao)
  {
    this.spectrumDao = spectrumDao;
  }
}