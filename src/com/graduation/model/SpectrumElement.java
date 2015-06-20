package com.graduation.model;

import java.sql.Date;

public class SpectrumElement
{
  private Integer id;
  private String userName;
  private String fitsName;
  private String templateName;
  private Double redShift;
  private String note;
  private String insertTime;
  private String updateTime;

  public void setId(Integer id)
  {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public void setFitsName(String fitsName) {
    this.fitsName = fitsName;
  }

  public String getFitsName() {
    return this.fitsName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateName() {
    return this.templateName;
  }

  public void setRedShift(double redShift) {
    this.redShift = Double.valueOf(redShift);
  }

  public Double getRedShift() {
    return this.redShift;
  }

  public void setInsertTime(String insertTime) {
    this.insertTime = insertTime;
  }

  public String getInsertTime() {
    return this.insertTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateTime() {
    return this.updateTime;
  }

public void setNote(String note) {
	this.note = note;
}

public String getNote() {
	return note;
}
}