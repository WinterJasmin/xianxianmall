package com.zhku.xianxianmall.domain;

import java.util.Date;

/**
 * @author: qwe
 * @date: 2019/4/21 22:14
 * @description:
 */
public class OrderTable {
    private Integer uid;

    private Integer rstate;

    private Date date;

    private Integer rid;

    private Integer oid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

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

    public Integer getRstate() {
        return rstate;
    }

    public void setRstate(Integer rstate) {
        this.rstate = rstate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uid=" + uid +
                ", rstate=" + rstate +
                ", date=" + date +
                ", rid=" + rid +
                ", oid=" + oid +
                '}';
    }
}
