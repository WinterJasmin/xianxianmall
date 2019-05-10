package com.zhku.xianxianmall.domain;

import java.util.Date;

/**
 * @author: qwe
 * @date: 2019/4/30 15:43
 * @description:
 */
public class Marketing {
    private int pid;

    private  String pname;

    private int date;

    private int count;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Marketing{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", date=" + date +
                ", count=" + count +
                '}';
    }
}
