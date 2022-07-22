package com.sjm.utils;

public class ResultUtil {
    private String msg;
    private Integer status;
    private Object data;

    //成功无结果集
    private ResultUtil(String msg) {
        this.status = 200;
        this.msg = msg;
    }

    public static ResultUtil success(String msg) {
        return new ResultUtil(msg);
    }

    //    成功有结果集
    private ResultUtil(String msg, Object data) {
        this.msg = msg;
        this.data = data;
        this.status = 200;
    }

    public static ResultUtil success(String msg, Object data) {
        return new ResultUtil(msg, data);
    }

    //失败
    private ResultUtil(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ResultUtil fail(int status, String msg) {
        return new ResultUtil(status, msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
