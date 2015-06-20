package com.graduation.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.graduation.common.Pager;
import com.graduation.dao.IMydbDao;
import com.graduation.model.MydbElement;
import com.graduation.service.IMydbService;
import com.graduation.util.TableHandleUtil;

public class MydbService implements IMydbService {

    private IMydbDao mydbDao;

    @Override
    public void createTable(String createTableSql, String tableName) {
        this.mydbDao.createTable(createTableSql, tableName);
    }

    //该函数存在的问题表已创建，但是因数据插入不正确抛出异常，导致创建了表但没有在mydb_user建立引用
    @Override
    /*
     * 由于不能插入csv格式，且record_id
     * 
     * */
    public long insertBach(InputStream fileIn, String tableName, List<String> error) throws IOException {

        int insertRowNumber = 0;//插入表格总行数
        boolean hasRecordId = false;
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileIn));
            ArrayList<String> sqls = new ArrayList<String>();//存入要插入的字段值
            int fieldColumnNum = 0;//字段的数目
            String line = "";
            Long row = 0L;
            while (null != (line = fileReader.readLine())) {
                row++;
                System.out.println("".equals(line) ? "空" : line);
                if (1 == row) {
                    //判断该表是否有record_Id字段
                    String temLine = line.toUpperCase();
                    hasRecordId = TableHandleUtil.hasRecordId(temLine);
                    if(!hasRecordId) line = "RECORD_ID"+","+line;
                    String createTableSql = TableHandleUtil.getTableByString(tableName, line);
                    System.out.println("生成的创建表的SQL语句："+createTableSql);
                    this.createTable(createTableSql, tableName);
                    fieldColumnNum = TableHandleUtil.getColumnNumberByString(line);
                } else {
                    if(!hasRecordId) line = (row-1)+","+line;
                   // System.out.println("Line值："+line);
                    String insertValue = TableHandleUtil.getInsertValueByString(line, fieldColumnNum, row, error);
                   // System.out.println("生成的插入字段的值："+insertValue);
                    if (null != insertValue || !"".equals(insertValue)) {
                        sqls.add(insertValue);
                    }
                    if (sqls.size() >= 200) {
                        insertRowNumber = this.mydbDao.insertBachByCollection(tableName, sqls);
                        sqls.clear();
                    }
                }

            }
            insertRowNumber += this.mydbDao.insertBachByCollection(tableName, sqls);

        } catch (IOException e) {
            error.add("请检查您上传的数据格式，上传数据中不要有数据库关键字字段，例如:dec,asc等等");
            throw e;

        }
        return (long) insertRowNumber;
    }

    @Override
    public void insertMydb(MydbElement mydb) {
        //插入mydb时要确保 mydb_user中没记录该表
        MydbElement md = this.mydbDao.getByNames(mydb.getTableName());
        if (md != null) {
            this.mydbDao.deleteMydbElementByTableName(mydb.getTableName());
        }
        this.mydbDao.insertMydbElement(mydb);
    }


    @Override
    public MydbElement getMydbElementByTableName(String tableName) {
        MydbElement mydb = this.mydbDao.getByNames(tableName);
        return mydb;
    }

    @Override
    public int deleteTable(Integer uid, Integer tid) {
        List<String> tableNames = this.mydbDao.getTableNames(uid, tid);
        if (tableNames.size() == 0) {
            return 0;
        }
         this.mydbDao.deleteTableByNames(tableNames);
         return this.mydbDao.deleteMydbElementByTableName(tableNames);
    }

    public void setMydbDao(IMydbDao mydbDao) {
        this.mydbDao = mydbDao;
    }

    @Override
    public List<MydbElement> list() {
        return this.list(null);
    }

    @Override
    public List<MydbElement> list(Integer uid) {
        return this.mydbDao.getMydbElement(uid);
    }

    @Override
    public List<String> listTableNames(Integer uid, String keyword) {
        return this.mydbDao.getTableNames(uid,keyword);
    }

    @Override
    public Pager<Map<String, Object>> findTableData(String tableName) {
        return this.mydbDao.findTabelData(tableName);
    }

    @Override
    public List<Map<String, Object>> listTableData(String tableName) {
        return this.mydbDao.listTableData(tableName);
    }

    @Override
    public List<String> getFieldNamesOfTable(String tableName) throws SQLException {
        return this.mydbDao.getFieldNamesOfTable(tableName);
    }

	@Override
	public MydbElement getMydbElementByUidAndTableName(Integer uid,
			String tableName) {
		return this.mydbDao.getMydbElementByUidAndTableName(uid,tableName);
	}

	@Override
	public void updateTableStatus(MydbElement mydbElement) {
		 this.mydbDao.updateTableStatus(mydbElement);
		
	}

	@Override
	public int deleteSelectedTable(int id, String[] tableName) {

        List<String> tableNames = new ArrayList<String>();
        for(String str:tableName){
        	  tableNames.add(str);
        }
         this.mydbDao.deleteTableByNames(tableNames);
         return this.mydbDao.deleteMydbElementByTableName(tableNames);
    
		
	}
	
	
    
}
