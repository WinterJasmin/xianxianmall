package com.zhku.xianxianmall.domain;

import java.util.Date;

public class Cart {
    private Integer cid;

    private Integer pid;

    private Integer uid;

    private Double cprice;

    private Integer total;

    private Double account;

    private Date cdate;

    private String csize;

    private String ccolor;

    private String cpic;

    private String pdescription;

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getCprice() {
        return cprice;
    }

    public void setCprice(Double cprice) {
        this.cprice = cprice;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getCsize() {
        return csize;
    }

    public void setCsize(String csize) {
        this.csize = csize == null ? null : csize.trim();
    }

    public String getCcolor() {
        return ccolor;
    }

    public void setCcolor(String ccolor) {
        this.ccolor = ccolor == null ? null : ccolor.trim();
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic == null ? null : cpic.trim();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", cprice=" + cprice +
                ", total=" + total +
                ", account=" + account +
                ", cdate=" + cdate +
                ", csize='" + csize + '\'' +
                ", ccolor='" + ccolor + '\'' +
                ", cpic='" + cpic + '\'' +
                ", pdescription='" + pdescription + '\'' +
                '}';
    }
}