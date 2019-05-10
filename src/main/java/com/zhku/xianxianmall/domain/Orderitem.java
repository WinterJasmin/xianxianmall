package com.zhku.xianxianmall.domain;

import java.util.Date;

public class Orderitem {
    private Integer oid;

    private Integer uid;

    private Integer pid;

    private String pdescription;

    private Integer total;

    private Double account;

    private Integer ostate;

    private Date odate;

    private Double oprice;

    private String osize;

    private String ocolor;

    private String opic;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Integer getOstate() {
        return ostate;
    }

    public void setOstate(Integer ostate) {
        this.ostate = ostate;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public Double getOprice() {
        return oprice;
    }

    public void setOprice(Double oprice) {
        this.oprice = oprice;
    }

    public String getOsize() {
        return osize;
    }

    public void setOsize(String osize) {
        this.osize = osize == null ? null : osize.trim();
    }

    public String getOcolor() {
        return ocolor;
    }

    public void setOcolor(String ocolor) {
        this.ocolor = ocolor == null ? null : ocolor.trim();
    }

    public String getOpic() {
        return opic;
    }

    public void setOpic(String opic) {
        this.opic = opic == null ? null : opic.trim();
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    @Override
    public String toString() {
        return "Orderitem{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", pdescription='" + pdescription + '\'' +
                ", total=" + total +
                ", account=" + account +
                ", ostate=" + ostate +
                ", odate=" + odate +
                ", oprice=" + oprice +
                ", osize='" + osize + '\'' +
                ", ocolor='" + ocolor + '\'' +
                ", opic='" + opic + '\'' +
                '}';
    }
}