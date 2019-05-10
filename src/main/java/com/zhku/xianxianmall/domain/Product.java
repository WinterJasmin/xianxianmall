package com.zhku.xianxianmall.domain;

import java.util.Date;

public class Product {
    private Integer pid;

    private String pname;

    private String pdescription;

    private Double pprice;

    private Integer pstate;

    private Integer pclass;

    private Date pdate;

    private String size;

    private String color;

    private String picaddress;

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription == null ? null : pdescription.trim();
    }

    public Double getPprice() {
        return pprice;
    }

    public void setPprice(Double pprice) {
        this.pprice = pprice;
    }

    public Integer getPstate() {
        return pstate;
    }

    public void setPstate(Integer pstate) {
        this.pstate = pstate;
    }

    public Integer getPclass() {
        return pclass;
    }

    public void setPclass(Integer pclass) {
        this.pclass = pclass;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getPicaddress() {
        return picaddress;
    }

    public void setPicaddress(String picaddress) {
        this.picaddress = picaddress == null ? null : picaddress.trim();
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pdescription='" + pdescription + '\'' +
                ", pprice=" + pprice +
                ", pstate=" + pstate +
                ", pclass=" + pclass +
                ", pdate=" + pdate +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", picaddress='" + picaddress + '\'' +
                '}';
    }

    public Product() {
    }

    public Product(Integer pid, String pname, String pdescription, Double pprice, Integer pstate, Integer pclass, Date pdate, String size, String color, String picaddress) {
        this.pid = pid;
        this.pname = pname;
        this.pdescription = pdescription;
        this.pprice = pprice;
        this.pstate = pstate;
        this.pclass = pclass;
        this.pdate = pdate;
        this.size = size;
        this.color = color;
        this.picaddress = picaddress;
    }
}