package com.graduation.model;

public class AjaxObj {


    /**
     * 1.表示成功 0表示失败
     */
    private int result = 0;

    private String message = "";

    private Object obj;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


}
