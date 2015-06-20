package com.graduation.model;

import java.sql.Date;

/**
 * 存储哪个用户创建了哪个表，以及该表的行数
 *
 * @author xiaoquan
 */
public class MydbElement {
    private Integer id;
    private String tableName;//表名
    private Long rowNum = 0L; //表的行数
    private Integer uid;//用户id

    //1.表示共享，0表示私有
    private int isPublic = 0;//该表是否为公用的
    private Date updateDate;//上传日期
    private String username;//上传者用户名
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
