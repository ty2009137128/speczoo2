package com.graduation.model.dto;

/**
 * Created with IntelliJ IDEA.
 * User: wenfe_000
 * Date: 5/13/14
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupDto {
    private int id;
    private String name;
    private String dec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
